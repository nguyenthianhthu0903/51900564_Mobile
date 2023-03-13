package com.example.appnote.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appnote.LabelItem;
import com.example.appnote.dao.LabelDaoRoom;

@Database(entities = {LabelItem.class},version = 1)
public abstract class LabelDatabase extends RoomDatabase
{
    private static final String DATABASE_NAME="label.db";
    private static LabelDatabase instance;

    public static synchronized LabelDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),LabelDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract LabelDaoRoom labelDao();
}
