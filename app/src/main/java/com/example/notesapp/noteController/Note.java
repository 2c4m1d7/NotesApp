package com.example.notesapp.noteController;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Note implements Comparable<Note> {


    private int id;
    private String date;

    public Note(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public Note(Note note) {
        this.id = note.id;
        this.date = note.date;
    }


    /**
     * @return id of the note
     */
    public final int getId() {
        return id;
    }


    /**
     * @return creation or modification date
     */
    public final String getDate() {
        return date;
    }

    @Override
    public int compareTo(Note o) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            Date date1 = format.parse(this.date);
            Date date2 = format.parse(o.getDate());

            return date1 != null ? date2.compareTo(date1) : 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
