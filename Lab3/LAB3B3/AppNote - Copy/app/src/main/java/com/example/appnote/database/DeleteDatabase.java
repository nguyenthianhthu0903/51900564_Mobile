package com.example.appnote.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appnote.LabelItem;
import com.example.appnote.NoteManagerTrash;
import com.example.appnote.dao.DeleteDaoRoom;
import com.example.appnote.dao.LabelDaoRoom;

@Database(entities = {NoteManagerTrash.class},version = 1)
public abstract class DeleteDatabase extends RoomDatabase
{
    private static final String DATABASE_NAME="delete.db";
    private static DeleteDatabase instance;

    public static synchronized DeleteDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),DeleteDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract DeleteDaoRoom deleteDaoRoom();
}
