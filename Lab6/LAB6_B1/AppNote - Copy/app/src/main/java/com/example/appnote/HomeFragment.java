package com.example.appnote;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.appnote.database.DeleteDatabase;
import com.example.appnote.database.LabelDatabase;
import com.example.appnote.database.NotesDatabase;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment  extends Fragment implements NoteHanding
{
    public static final int REQUEST_CODE_ADD_NOTE=10;
    public static final int REQUEST_CODE_UPDATE=2;
    public static final int REQUEST_CODE_DISPLAY_NOTE=3;

    long timeDeleted=0;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Intent data;

    //FILTER LABEL
    Spinner spinnerLabel;
    List<LabelItem> listLabel;
    ArrayList<String> listLabelName=new ArrayList<>();

    int checkSwitch=2;
    ImageView changeView;


    private int notePosition=-1;
    private TextView texttitle,inputSearch;
    private RecyclerView noteRecyclerView;
    List<NoteManager> noteList;

    private List<NoteManager> listsearch=new ArrayList<>();
    private NoteAdapter noteAdapter;
    NotesDatabase database;
    FirestoreRecyclerAdapter<NoteManager, NoteAdapter.NoteViewHolder> noteadapterfire;

    //delete
    List<NoteManager> listDeleted = new ArrayList<>();
    List<NoteManager> temp=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.homefragment,container,false);
        ImageView addNoteMain=view.findViewById(R.id.addNoteMain);



        addNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult( new Intent(getContext(),CreateNote.class), REQUEST_CODE_ADD_NOTE);
            }
        });

        //search
        inputSearch=view.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                noteAdapter.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(noteList.size()!=0)
                {
                    noteAdapter.searchNote(editable.toString());
                }
            }
        });
        //FILTER SORT BY LABEL NAME
        listLabel= LabelDatabase.getInstance(getActivity()).labelDao().getListLabel();
        listLabelName.add("Chọn nhãn dán............");
        for(int i=0;i<listLabel.size();i++)
        {
            listLabelName.add(listLabel.get(i).getLabelName());
        }
        listLabelName.add("Lọc tất cả nhãn");
        inputSearch.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Lọc thông báo theo nhãn");
                builder.setIcon(R.drawable.filter);

                View mview=getLayoutInflater().inflate(R.layout.layoutspinnerlabel,null);
                final Spinner spinner=(Spinner)mview.findViewById(R.id.spinnerLabel);
                ArrayAdapter adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listLabelName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                builder.setView(mview);
                builder.setPositiveButton("Lọc", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        if(!spinner.getSelectedItem().toString().equals("Chọn nhãn dán............"))
                        {
                            filterLabel(spinner.getSelectedItem().toString());
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                AlertDialog ad=builder.create();
                ad.show();
                return  false;
            }
        });

        //SWITCH
        changeView=view.findViewById(R.id.changeView);
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checkSwitch++;
                if(checkSwitch%2==0)
                {
                    noteRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                }
                else
                {
                    noteRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                }
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNote").orderBy("id",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<NoteManager> allnote=new FirestoreRecyclerOptions.Builder<NoteManager>().setQuery(query,NoteManager.class).build();
        noteadapterfire=new FirestoreRecyclerAdapter<NoteManager, NoteAdapter.NoteViewHolder>(allnote) {
            @Override
            protected void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position, @NonNull NoteManager model) {
//                holder.textTitle.setText(model.getTitle());
//                holder.textSub.setText(model.getSubTitle());
            }

            @NonNull
            @Override
            public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new NoteAdapter.NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered,parent,false));
            }
        };


        noteRecyclerView=view.findViewById(R.id.noteRecyclerView);
        noteRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
        database=NotesDatabase.getNotesDatabase(getActivity());

        //List Note
        noteList=new ArrayList<>();
        noteAdapter=new NoteAdapter(noteList,this);
        noteRecyclerView.setAdapter(noteAdapter);
        getNote(REQUEST_CODE_DISPLAY_NOTE,false);
        return view;
    }

    @Override
    public void onNoteClick(NoteManager note, int position) {
        notePosition=position;
        Intent intent=new Intent(getActivity(),CreateNote.class);
        intent.putExtra("ViewOrUpdate",true);
        intent.putExtra("note", note);
        startActivityForResult(intent,REQUEST_CODE_UPDATE);
    }

    public void filterLabel(String labelNeedFilter)
    {
        List<NoteManager> listAfterFilterbyLabel=new ArrayList<>();
        for(int i=0;i<noteList.size();i++)
        {
            if(noteList.get(i).getLabel().contains(labelNeedFilter))
            {
                listAfterFilterbyLabel.add(noteList.get(i));
            }
        }
        if(labelNeedFilter.equals("Lọc tất cả nhãn"))
        {
            noteAdapter=new NoteAdapter(noteList,this);
        }
        else
            noteAdapter=new NoteAdapter(listAfterFilterbyLabel,this);
        noteRecyclerView.setAdapter(noteAdapter);
    }
    @Override
    public void reloadData() {
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteLongClick(NoteManager note, int position) {
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        imageUris.add(Uri.parse(noteList.get(position).getImagePath()));

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_SUBJECT,noteList.get(position).getTitle());
        intent.putExtra(Intent.EXTRA_TEXT,noteList.get(position).getNoteMain());
//        intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(noteList.get(position).getImagePath()));
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        intent.setData(Uri.parse(noteList.get(position).getImagePath()));
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.setType("plain/text");
        intent.setType("application/octet-stream");
        intent.setType("image/png");
        intent.setType("image/jpg");
        intent.setType("image/*");
        intent.setType("image/jpeg");
        intent.setType("video/mp4");
        if(intent.resolveActivity(getActivity().getPackageManager())!=null)
        {
            startActivity(Intent.createChooser(intent,"Choose App"));
        }
        else
        {
            Toast.makeText(getActivity(),"No app",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNoteClickTrash(NoteManagerTrash note, int position) {

    }

    @Override
    public void onNoteLongClickTrash(NoteManagerTrash note, int position) {

    }

    private void getNote(final int requestCode,final boolean noteDeleted)
    {
        @SuppressLint("StaticFieldLeak")
        class getNoteTask extends AsyncTask<Void,Void, List<NoteManager>>
        {
            @Override
            protected List<NoteManager> doInBackground(Void... voids) {
                return NotesDatabase
                        .getNotesDatabase(getActivity())
                        .daonote().getAllNote();
            }
            @Override
            protected void onPostExecute(List<NoteManager> note)
            {
                super.onPostExecute(note);
                if(requestCode==REQUEST_CODE_DISPLAY_NOTE)
                {
                    noteList.addAll(note);
                    noteAdapter.notifyDataSetChanged();
                }
                //thêm
                else if(requestCode==REQUEST_CODE_ADD_NOTE)
                {
                    noteList.add(0,note.get(0));
                    noteAdapter.notifyItemInserted(0);
                    noteRecyclerView.smoothScrollToPosition(0);
                }
                //xóa, cập nhật
                else if(requestCode==REQUEST_CODE_UPDATE)
                {
                    //TRUYỀN LIST CAC NOTE BỊ XÓA
                    sendListNoteDeleted(notePosition);
                    noteList.remove(notePosition);
//                    DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNote").document(String.valueOf(noteList.get(notePosition).getId()));
//                    documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
                    if(noteDeleted)
                    {
                        noteAdapter.notifyItemRemoved(notePosition);
                    }
                    else
                    {
                        noteList.add(notePosition,note.get(notePosition));
                        noteAdapter.notifyItemChanged(notePosition);
                    }
                }
            }
        }
        new getNoteTask().execute();
//        listsearch=noteList;

    }
    public void sendListNoteDeleted(int notePosition)
    {
//        int id=noteList.get(notePosition).getId();
        String title=noteList.get(notePosition).getTitle();
        String dateTime =noteList.get(notePosition).getDateTime();
        String subTitle =noteList.get(notePosition).getSubTitle();
        String noteMain=noteList.get(notePosition).getNoteMain();
        String color = noteList.get(notePosition).getColor();
        String imagePath =noteList.get(notePosition).getImagePath();
        String videoPath =noteList.get(notePosition).getVideoPath();
        int pinned=noteList.get(notePosition).getPinned();
        String eventDate =noteList.get(notePosition).getEventDate() ;
        String eventTime =noteList.get(notePosition).getEventTime();
        String messNoti =noteList.get(notePosition).getMessNoti();
        String password =noteList.get(notePosition).getPassword();
        int alarm =noteList.get(notePosition).getAlarm();
        String label=noteList.get(notePosition).getLabel();
        int lock=noteList.get(notePosition).getLock();
        timeDeleted=System.currentTimeMillis();

        NoteManagerTrash noteManagerTrash=new NoteManagerTrash(timeDeleted,title,dateTime,subTitle,noteMain,color,imagePath,videoPath,pinned,eventDate,eventTime,messNoti,password,alarm,label,lock);
        DeleteDatabase.getInstance(getActivity()).deleteDaoRoom().insertNoteDelete(noteManagerTrash);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD_NOTE && resultCode==RESULT_OK)
        {
            getNote(REQUEST_CODE_ADD_NOTE,false);
        }
        else if(requestCode==REQUEST_CODE_UPDATE && resultCode==RESULT_OK)
        {
            if(data!=null)
            {
                getNote(REQUEST_CODE_UPDATE,data.getBooleanExtra("noteDeleted",false));
            }
        }
    }

}

