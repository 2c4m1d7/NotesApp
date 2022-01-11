package com.example.notesapp.noteController;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.example.notesapp.dataStorage.cloud.Cloud;
import com.example.notesapp.dataStorage.local.Storage;
import com.example.notesapp.dataStorage.uniqueIDStorage.UniqueIDStorage;

public class NoteController implements INoteController {


    private Storage storage;
    private Cloud cloud;
    private Context context;


    public NoteController(Context context) {
        this.context = context;
        cloud = new Cloud(context);
        storage = new Storage(context);
    }

    @Override
    public Note save(CharSequence text) {

        return storage.saveText(text);

    }

    @Override
    public boolean update(TextNote textNote) {
        return storage.updateTextNote(textNote);
    }

    @Override
    public void sortByName(List<TextNote> textNotes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(textNotes, Comparator.comparing(note -> note.getText()));
        }
    }

    @Override
    public void sortNewToOld(List<TextNote> textNotes) {
        Collections.sort(textNotes);
    }

    @Override
    public void sortOldToNew(List<TextNote> textNotes) {
        Collections.sort(textNotes, (note1, note2) -> {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            try {

                Date date1 = format.parse(note1.getDate());
                Date date2 = format.parse(note2.getDate());

                return date1 != null ? date1.compareTo(date2) : 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }


    @Override
    public boolean removeLocal(TextNote textNote) {

        if (storage.delete(textNote)) {
            cloud.save(textNote);
            return true;
        } else
            return false;

    }


    @Override
    public void removeFromCloud(TextNote textNote) {
        cloud.delete(textNote);
    }

    @Override
    public boolean restore(TextNote textNote) {

        cloud.delete(textNote);
        return storage.restoreTextNote(textNote);

    }

    @Override
    public boolean restore(ArrayList<TextNote> textNotes) {
        for (TextNote textNote : textNotes) {
            cloud.delete(textNote);
            if (!storage.restoreTextNote(textNote))
                return true;
        }
        return false;
    }

    @Override
    public void saveInCloud(TextNote textNote) {
        cloud.save(textNote);
    }

    @Override
    public boolean clearLocal(ArrayList<TextNote> textNotes) {
        for (TextNote textNote : textNotes) {
            cloud.save(textNote);
        }
        return storage.clear();
    }


    @Override
    public void clearCloud() {
        cloud.clear();

    }

    public void login() {
        cloud.loginOrAddUser(new UniqueIDStorage(context).getUniqueID());
    }
}
