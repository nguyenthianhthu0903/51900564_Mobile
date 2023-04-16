package com.example.appnote;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Handler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.LayoutInflaterCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>
{
    public static final int type_grid=1;
    public static final int type_list=2;
    public static final int type_staggered=3;
    private GridLayoutManager mLayoutManager;

    //SEARCH
    Timer timer;
    List<NoteManager> noteNoteSearch;
    Runnable runnable;

    //UPDATE
    List<NoteManager> notes;
    NoteHanding noteHanding;
    List<NoteManager>huy;

    //PASSWORD
    EditText inputPass,confirmPass,changepass1,changepass2,passold;

    NoteHanding onReload;
    ArrayList<NoteManager> itemModels;

    Context context;

    public NoteAdapter(List<NoteManager> notes, NoteHanding noteHanding )
    {
        this.notes = notes;
        this.noteHanding=noteHanding;
        noteNoteSearch=notes;
    }

    public NoteAdapter(Context context,List<NoteManager> user) {
        this.context=context;
        this.huy=user;
    }


    @Override
    public int getItemViewType(int position)
    {
       return position;
    }

    @NonNull
    @Override
    //trả về view hiển thị
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
//        context = parent.getContext();
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position)
    {
        holder.setNote(notes.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notes.get(position).getLock()==0||notes.get(position).getPassword()=="")
                    noteHanding.onNoteClick(notes.get(position),position);
                //Nhập mật khẩu
                if(notes.get(position).getLock()==1)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Nhập mật khẩu");
                    builder.setIcon(R.drawable.key);

                    confirmPass=new EditText(view.getContext());
                    builder.setView(confirmPass);

                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which)
                        {
                            if(notes.get(position).getPassword().equals(confirmPass.getText().toString()))
                            {
                                noteHanding.onNoteClick(notes.get(position),position);
                                Toast.makeText(view.getContext(),"Mật khẩu hợp lệ",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(view.getContext(),"Mật khẩu sai",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    AlertDialog ad=builder.create();
                    ad.show();
                }

            }
        });

        holder.layoutNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                noteHanding.onNoteLongClick(notes.get(position),position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        TextView textTitle,textSub,textDate,label;
        LinearLayout layoutNote;
        RoundedImageView imgViewNote,imgPin;
        //PIN
//        ImageView imgPin;
        ImageView imgMenu;

        public NoteViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textTitle=itemView.findViewById(R.id.textTitleV);
            textSub=itemView.findViewById(R.id.textSubV);
            textDate=itemView.findViewById(R.id.textDateV);
            layoutNote=itemView.findViewById(R.id.layoutNote);
            imgViewNote=itemView.findViewById(R.id.imgViewNote);
            imgPin=itemView.findViewById(R.id.icPin);
            label=itemView.findViewById(R.id.labelV);
        }
        void setNote(NoteManager note)
        {
            //set title
            textTitle.setText(note.getTitle());
            //set date
            textDate.setText(note.getDateTime());
            //set subtitle
            if(note.getSubTitle().trim().isEmpty())
            {
                textSub.setVisibility(View.GONE);
            }
            else textSub.setText(note.getSubTitle());

            //set lại màu
            layoutNote.setBackgroundColor(Integer.parseInt(note.getColor()));

            //set PICTURE
            if(note.getImagePath()!=null)
            {
                imgViewNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imgViewNote.setVisibility(View.VISIBLE);
            }

            //set pin
            if(note.getPinned()==1)
            {
                imgPin.setImageResource(R.drawable.ic_pinred);
                imgPin.setVisibility(View.VISIBLE);
            }
            else
            {
                imgPin.setImageResource(0);
                imgPin.setVisibility(View.GONE);
            }

            //label
            if(note.getLabel().isEmpty()||note.getLabel().equals("N"))
            {
                label.setVisibility(View.GONE);
            }
            else
            {
                label.setVisibility(View.VISIBLE);
                label.setText(note.getLabel());
            }

        }
    }
    //Popup menu


    //Search
    public void searchNote(String inputSearch)
    {
        timer=new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if(inputSearch.trim().isEmpty())
                {
                    notes=noteNoteSearch;
                }
                else
                {
                    ArrayList<NoteManager> flag=new ArrayList<>();
                    for(NoteManager noteManager:noteNoteSearch)
                    {
                        if(noteManager.getTitle().toUpperCase().contains(inputSearch.toUpperCase())
                                || noteManager.getSubTitle().toUpperCase().contains(inputSearch.toUpperCase())
                                || noteManager.getNoteMain().toUpperCase().contains(inputSearch.toUpperCase()))
                        {
                            flag.add(noteManager);
                        }
                    }
                    notes=flag;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }

        },500);
    }
    public void cancel()
    {
        if(timer!=null)
            timer.cancel();
    }
}
