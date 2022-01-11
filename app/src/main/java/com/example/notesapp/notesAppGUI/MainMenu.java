package com.example.notesapp.notesAppGUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

import com.example.notesapp.R;

import com.example.notesapp.dataStorage.local.Storage;
import com.example.notesapp.noteController.TextNote;
import com.example.notesapp.noteController.NoteController;

public class MainMenu extends AppCompatActivity {


    private NoteController controller;
    private Storage storage;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ArrayList<TextNote> textNotes;
    private ItemTouchHelper itemTouchHelper;

    public static final String EXTRA_MESSAGE_ID = "com.example.notesapp.ID";

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = new Storage(this);
        controller = new NoteController(this);


        FloatingActionButton fab = findViewById(R.id.addNote);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddNote.class);
            startActivity(intent);
        });


    }


    @Override
    protected void onResume() {
        textNotes = new ArrayList<>(storage.getAllNotes().values());
        Collections.sort(textNotes);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(textNotes, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperSimpleCallback(0, ItemTouchHelper.LEFT));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        storage.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu_menu, menu);
        return true;
    }


    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.sortByName:
                controller.sortByName(textNotes);
                recyclerViewAdapter.notifyDataSetChanged();
                return true;
            case R.id.sortNewToOld:
                controller.sortNewToOld(textNotes);
                recyclerViewAdapter.notifyDataSetChanged();
                return true;
            case R.id.sortOldToNew:
                controller.sortOldToNew(textNotes);
                recyclerViewAdapter.notifyDataSetChanged();
                return true;
            case R.id.trash:
                Intent intent = new Intent(this.getApplicationContext(), Trash.class);
                startActivity(intent);
                return true;
            case R.id.clearAll:
                controller.login();
                ArrayList<TextNote> notesTmp = new ArrayList<>(textNotes);
                if (notesTmp.size() > 0) {
                    if (controller.clearLocal(textNotes)) {
                        textNotes.clear();
                        onResume();
                        cancelCleaning(notesTmp);
                        return true;
                    }
                } else return true;
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @SuppressLint("ShowToast")
    private void cancelCleaning(ArrayList<TextNote> notesTmp) {

        Snackbar.make(recyclerView, "Delete all notes?", Snackbar.LENGTH_LONG).setAction("Cancel", new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                textNotes.addAll(notesTmp);
                controller.restore(notesTmp);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        }).show();
    }


    private class MyItemTouchHelperSimpleCallback extends ItemTouchHelper.SimpleCallback {

        public MyItemTouchHelperSimpleCallback(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            int position = viewHolder.getBindingAdapterPosition();
            TextNote textNote = textNotes.remove(position);
            controller.removeLocal(textNote);
            recyclerViewAdapter.notifyDataSetChanged();

            Snackbar.make(recyclerView, "Delete " + textNote.getShortText() + "?", Snackbar.LENGTH_LONG).setAction("Cancel", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    controller.restore(textNote);
                    textNotes.add(position, textNote);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }).show();


        }
    }
}