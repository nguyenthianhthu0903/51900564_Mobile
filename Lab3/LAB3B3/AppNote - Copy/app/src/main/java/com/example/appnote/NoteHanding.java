package com.example.appnote;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.example.appnote.NoteManager;
public interface NoteHanding {
    void onNoteClick(NoteManager note,int position);
    void reloadData();
    void onNoteLongClick(NoteManager note,int position);
    void onNoteClickTrash(NoteManagerTrash note,int position);
    void onNoteLongClickTrash(NoteManagerTrash note,int position);
}
