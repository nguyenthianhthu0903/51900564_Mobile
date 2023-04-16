package com.example.appnote.database;

import android.content.Context;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.appnote.NoteManager;
import com.example.appnote.dao.NoteDaoRoom;
@Database(entities = NoteManager.class,version = 1,exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase
{
    private static NotesDatabase notesDatabase;
    public static synchronized NotesDatabase getNotesDatabase(Context context)
    {
        if(notesDatabase==null)
            notesDatabase= Room.databaseBuilder(context,NotesDatabase.class,"notesdb").build();
        return notesDatabase;
    }
    public abstract NoteDaoRoom daonote();
}
