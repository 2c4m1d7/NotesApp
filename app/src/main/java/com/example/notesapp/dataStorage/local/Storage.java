package com.example.notesapp.dataStorage.local;


import static com.example.notesapp.dataStorage.local.SQLNote.SQL_CREATE_NOTES;
import static com.example.notesapp.dataStorage.local.SQLNote.SQL_DELETE_NOTES;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import com.example.notesapp.noteController.Note;
import com.example.notesapp.noteController.TextNote;


public class Storage extends SQLiteOpenHelper implements IStorage {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes.db";


    private SQLiteDatabase db;


    public Storage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NOTES);
        onCreate(db);
    }

    @Override
    public TextNote saveText(CharSequence text) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            db = this.getWritableDatabase();


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

            int id = getFreeID();
            String date = formatter.format(LocalDateTime.now());

            ContentValues values = new ContentValues();
            values.put(SQLNote.Notes._ID, id);
            values.put(SQLNote.Notes.COLUMN_NAME_TEXT, text.toString());
            values.put(SQLNote.Notes.COLUMN_NAME_DATE, date);


            try {
                if (db.insert(SQLNote.Notes.TABLE_NAME, null, values) != -1)
                    return new TextNote(id, text.toString(), date);
            } finally {
                db.close();
            }
        }

        return null;
    }

    private int getFreeID() {
        String[] colums = {SQLNote.Notes._ID};

        Cursor cursor = db.query(SQLNote.Notes.TABLE_NAME, colums, null, null, null, null, null);

        int freeId = 0;

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(SQLNote.Notes._ID));
            if (id > freeId)
                break;
            freeId++;
        }
        cursor.close();

        return freeId;
    }

    @Override
    public boolean restoreTextNote(TextNote textNote) {


        db = this.getWritableDatabase();


        int id = getFreeID();
        String date = textNote.getDate();
        String text = textNote.getText();

        ContentValues values = new ContentValues();
        values.put(SQLNote.Notes._ID, id);
        values.put(SQLNote.Notes.COLUMN_NAME_TEXT, text);
        values.put(SQLNote.Notes.COLUMN_NAME_DATE, date);


        try {
            return db.insert(SQLNote.Notes.TABLE_NAME, null, values) != -1;
        } finally {
            db.close();

        }
    }


    @Override
    public boolean delete(TextNote note) {


        db = this.getWritableDatabase();

        try {
           // return db.delete(SQLNote.Notes.TABLE_NAME, SQLNote.Notes._ID + " = ?", new String[]{Integer.toString(note.getId())}) == 1;
            return db.delete(SQLNote.Notes.TABLE_NAME, SQLNote.Notes.COLUMN_NAME_TEXT + " = ? and "+ SQLNote.Notes.COLUMN_NAME_DATE +" = ?", new String[]{note.getText(), note.getDate()}) == 1;
        } finally {
            db.close();
        }

    }

    @Override
    public boolean updateTextNote(TextNote textNote) {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            db = this.getWritableDatabase();

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");


            ContentValues values = new ContentValues();
            values.put(SQLNote.Notes.COLUMN_NAME_TEXT, textNote.getText());
            values.put(SQLNote.Notes.COLUMN_NAME_DATE, formatter.format(localDateTime));
            try {
                return db.update(SQLNote.Notes.TABLE_NAME, values, SQLNote.Notes._ID + " = ?", new String[]{Integer.toString(textNote.getId())}) == 1;
            } finally {
                db.close();
            }

        }
        return false;


    }

    @Override
    public boolean clear() {

        int rows = getAllNotes().size();
        db = this.getWritableDatabase();

        try {
            return db.delete(SQLNote.Notes.TABLE_NAME, null, null) == rows;
        } finally {
            db.close();
        }

    }

    public TreeMap<Integer, TextNote> getAllNotes() {

        TreeMap<Integer, TextNote> notes = new TreeMap<>();
        db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from " + SQLNote.Notes.TABLE_NAME, null);


        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(SQLNote.Notes.COLUMN_NAME_TEXT));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(SQLNote.Notes._ID));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(SQLNote.Notes.COLUMN_NAME_DATE));
            notes.put(id, new TextNote(id, text, date));
        }
        try {
            return notes;
        } finally {
            db.close();
        }

    }


}
