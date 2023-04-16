package com.example.lab6b3;

import android.provider.BaseColumns;

public class Note
{
    public static final String SQL_CREATE_NOTE =
            "CREATE TABLE " + TableNote.TABLE_NAME + " (" +
                    TableNote._ID + " INTEGER PRIMARY KEY," +
                    TableNote.COLUMN_NAME + " TEXT," +
                    TableNote.COLUMN_PLACE + " TEXT," +
                    TableNote.COLUMN_DATE + " TEXT)"+
                    TableNote.COLUMN_TIME + " TEXT)";

    public static final String SQL_DELETE_NOTE =
            "DROP TABLE IF EXISTS " + TableNote.TABLE_NAME;

    public static class TableNote implements BaseColumns {

        public static final String TABLE_NAME = "Note";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";

    }
}
