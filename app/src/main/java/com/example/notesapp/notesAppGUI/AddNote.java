package com.example.notesapp.notesAppGUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.TreeMap;

import com.example.notesapp.R;
import com.example.notesapp.dataStorage.local.Storage;
import com.example.notesapp.noteController.TextNote;
import com.example.notesapp.noteController.NoteController;

public class AddNote extends AppCompatActivity {

    private EditText text;
    private NoteController controller;
    private boolean update;
    private TreeMap<Integer, TextNote> notes;
    private TextNote textNote;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        text = findViewById(R.id.multilineEditText);
        Storage storage = new Storage(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra(MainMenu.EXTRA_MESSAGE_ID);
        if (id != null) {
            notes = storage.getAllNotes();
            textNote = notes.get(Integer.parseInt(id));
            message = textNote.getText();
            text.setText(message);

            storage.close();
            update = true;

        } else update = false;

        controller = new NoteController(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addnote_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (update)
            textNote.setText(text.getText().toString());
        if (item.getItemId() == R.id.saveMenu) {
            if (!text.getText().toString().equals("")) {
                if (update) {
                    message = text.getText().toString();
                    return controller.update(textNote);
                } else {
                    update = true;
                    textNote = (TextNote) controller.save(text.getText());
                    message = text.getText().toString();
                    if (textNote == null) {
                        return false;
                    } else return true;
                }
            } else return super.onOptionsItemSelected(item);
        } else
            return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        if (!text.getText().toString().equals("")) {
            if (update && !message.equals(text.getText().toString())) {
              controller.update(textNote);
            } else if (!update) {
                controller.save(text.getText());
            }
        }
        super.onPause();
    }
}