package com.example.appnote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.appnote.NoteManager;
import java.util.List;

@Dao
public interface NoteDaoRoom
{
    @Query("SELECT * FROM TABLENOTE order by id desc")
    List<NoteManager>getAllNote();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNode(NoteManager note);

    @Delete void deleteNote(NoteManager note);

//    @Query("update tablenote set pinned=:pin where id=:id")
//    void pin(int id,boolean pin);
}
