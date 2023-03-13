package com.example.appnote;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import com.example.appnote.database.DeleteDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.zip.Inflater;

public class adapter2 extends RecyclerView.Adapter<adapter2.NoteViewHolder>
{
    Context context;

    public static final int type_grid=1;
    public static final int type_list=2;
    public static final int type_staggered=3;
    private GridLayoutManager mLayoutManager;


    //SEARCH
    private Timer timer;
    private List<NoteManagerTrash> noteNoteSearch;
    Runnable runnable;

    //UPDATE
    private List<NoteManagerTrash> notes;
    private NoteHanding noteHanding;

    //PASSWORD
    EditText inputPass,confirmPass,changepass1,changepass2,passold;

    private NoteHanding onReload;
    private ArrayList<NoteManagerTrash> itemModels;

    public adapter2(List<NoteManagerTrash> notes, NoteHanding noteHanding )
    {
        this.notes = notes;
        this.noteHanding=noteHanding;
        noteNoteSearch=notes;
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
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position)
    {
        holder.setNote(notes.get(position));

        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteHanding.onNoteClickTrash(notes.get(position),position);
            }
        });
        holder.layoutNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                noteHanding.onNoteLongClickTrash(notes.get(position),position);
                return false;
            }
        });

        //xóa sau 1 ngày
        if(System.currentTimeMillis()-notes.get(position).getTimeDeleted()>86400)
            DeleteDatabase.getInstance(context).deleteDaoRoom().deleleNoteTrash(notes.get(position));
    }


    @Override
    public int getItemCount()
    {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textTitle,textSub,textDate,label;
        private LinearLayout layoutNote;
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
        void setNote(NoteManagerTrash note)
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
}
