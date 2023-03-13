package com.example.appnote;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.appnote.database.DeleteDatabase;
import com.example.appnote.database.LabelDatabase;
import com.example.appnote.database.NotesDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrashFragment  extends Fragment implements NoteHanding
{
    private RecyclerView noteRecyclerView;
    List<NoteManager> noteList;
    List<NoteManagerTrash> listNoteManagerTrashes=new ArrayList<>();
    private adapter2 noteAdapter;
    private int notePosition=-1;
    public static final int REQUEST_CODE_ADD_NOTE=1;
    public static final int REQUEST_CODE_UPDATE=2;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.trashfragment,container,false);

        //LOAD DANH SÁCH KHI VỪA MỞ
        listNoteManagerTrashes=DeleteDatabase.getInstance(getActivity()).deleteDaoRoom().getListNoteDelete();
        noteRecyclerView=view.findViewById(R.id.noteRecyclerView);
        noteRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );

        //LẤY DANH SÁCH NOTE BỊ XÓA
        listNoteManagerTrashes=DeleteDatabase.getInstance(getActivity()).deleteDaoRoom().getListNoteDelete();
        noteAdapter=new adapter2(listNoteManagerTrashes,this);
        noteRecyclerView.setAdapter(noteAdapter);

        return view;
    }

    @Override
    public void onNoteClick(NoteManager note, int position)
    {
    }

    @Override
    public void reloadData() {
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteLongClick(NoteManager note, int position) {
    }

    @Override
    public void onNoteClickTrash(NoteManagerTrash note, int position)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa hoặc khôi phục ghi chú");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                Toast.makeText(getActivity(),"Xoá thành công", Toast.LENGTH_SHORT).show();
                deleteAlway(position);
            }
        });
        builder.setNegativeButton("Khôi phục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                restore(position);
            }
        });
        AlertDialog ad=builder.create();
        ad.show();
    }

    @Override
    public void onNoteLongClickTrash(NoteManagerTrash note, int position)
    {
        Toast.makeText(getActivity(), String.valueOf(listNoteManagerTrashes.get(position).getTimeDeleted()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD_NOTE && resultCode==RESULT_OK)
        {
        }
    }
    public void restore(int i)
    {
        NoteManager noteRes=new NoteManager();

        noteRes.setTitle(listNoteManagerTrashes.get(i).getTitle());
        noteRes.setDateTime(listNoteManagerTrashes.get(i).getDateTime());
        noteRes.setSubTitle(listNoteManagerTrashes.get(i).getSubTitle());
        noteRes.setNoteMain(listNoteManagerTrashes.get(i).getNoteMain());
        noteRes.setColor(listNoteManagerTrashes.get(i).getColor());
        noteRes.setImagePath(listNoteManagerTrashes.get(i).getImagePath());
        noteRes.setVideoPath(listNoteManagerTrashes.get(i).getVideoPath());
        noteRes.setPinned(listNoteManagerTrashes.get(i).getPinned());
        noteRes.setEventDate(listNoteManagerTrashes.get(i).getEventDate()) ;
        noteRes.setEventTime(listNoteManagerTrashes.get(i).getEventTime());
        noteRes.setMessNoti(listNoteManagerTrashes.get(i).getMessNoti());
        noteRes.setPassword(listNoteManagerTrashes.get(i).getPassword());
        noteRes.setAlarm(listNoteManagerTrashes.get(i).getAlarm());
        noteRes.setLabel(listNoteManagerTrashes.get(i).getLabel());
        noteRes.setLock(listNoteManagerTrashes.get(i).getLock());

        Toast.makeText(getActivity(),"Khôi phục thành công", Toast.LENGTH_SHORT).show();
        @SuppressLint("StaticFieldLeak")
        class saveNoteTask extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                NotesDatabase.getNotesDatabase(getActivity()).daonote().insertNode(noteRes);
                return null;
            }
            @Override
            protected void onPostExecute(Void avoid) {
                super.onPostExecute(avoid);
            }
        }
        deleteAlway(i);
        new saveNoteTask().execute();
    }
    public void deleteAlway(int position)
    {
        DeleteDatabase.getInstance(getActivity()).deleteDaoRoom().deleleNoteTrash(listNoteManagerTrashes.get(position));
        listNoteManagerTrashes=DeleteDatabase.getInstance(getActivity()).deleteDaoRoom().getListNoteDelete();
        noteAdapter=new adapter2(listNoteManagerTrashes,this);
        noteRecyclerView.setAdapter(noteAdapter);
    }

}

