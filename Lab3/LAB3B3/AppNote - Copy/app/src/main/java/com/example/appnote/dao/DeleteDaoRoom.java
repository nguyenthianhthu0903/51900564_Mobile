package com.example.appnote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appnote.LabelItem;
import com.example.appnote.NoteManager;
import com.example.appnote.NoteManagerTrash;

import java.util.List;

@Dao
public interface DeleteDaoRoom
{
    @Insert
    void insertNoteDelete(NoteManagerTrash deleteItem);

    @Query("select * from NoteManagerTrash")
    List<NoteManagerTrash> getListNoteDelete();

    @Delete
    void deleleNoteTrash(NoteManagerTrash noteManagerTrash);
}
