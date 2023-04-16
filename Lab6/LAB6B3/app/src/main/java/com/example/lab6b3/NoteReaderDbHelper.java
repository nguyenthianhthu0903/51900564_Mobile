package com.example.lab6b3;

import static com.example.lab6b3.Note.SQL_CREATE_NOTE;
import static com.example.lab6b3.Note.SQL_DELETE_NOTE;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteReaderDbHelper {
    public class StudentReaderDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Notes.db";

        public StudentReaderDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_NOTE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_NOTE);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
        public long insertNote(Note note)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(Note.TableNote._ID,note.id);
            values.put(Note.TableNote.COLUMN_NAME,note.name);
        }
    }

    

}
