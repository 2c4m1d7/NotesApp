package com.example.notesapp.dataStorage.local;

import android.provider.BaseColumns;

public final class SQLNote {


    public static class Notes implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_DATE = "date";
    }

    public static final String SQL_CREATE_NOTES =
            "CREATE TABLE " + Notes.TABLE_NAME + " (" +
                    Notes._ID + " INTEGER PRIMARY KEY," +
                    Notes.COLUMN_NAME_TEXT + " TEXT," +
                    Notes.COLUMN_NAME_DATE + " TEXT)";
    public static final String SQL_DELETE_NOTES =
            "DROP TABLE IF EXISTS " + Notes.TABLE_NAME;

}
