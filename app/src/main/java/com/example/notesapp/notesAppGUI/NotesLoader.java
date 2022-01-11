package com.example.notesapp.notesAppGUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import com.example.notesapp.noteController.TextNote;
import com.example.notesapp.noteController.NoteController;

public class NotesLoader implements Response.Listener<String> {

    private final Context context;
    private final RecyclerView recyclerView;
    private NoteController controller;
    private TrashRecyclerViewAdapter trashRecyclerViewAdapter;

    public NotesLoader(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        controller = new NoteController(context);


    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResponse(String response) {
        ArrayList<TextNote> textNotes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String text = jsonObject.getString("text");
                String date = jsonObject.getString("date");
                TextNote textNote = new TextNote(id, text, date);

                textNotes.add(textNote);

            }

            Collections.sort(textNotes);
            trashRecyclerViewAdapter = new TrashRecyclerViewAdapter(context);
            trashRecyclerViewAdapter.setNotes(textNotes);
            recyclerView.setAdapter(trashRecyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            trashRecyclerViewAdapter.notifyDataSetChanged();

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    int position = viewHolder.getBindingAdapterPosition();
                    TextNote textNote = textNotes.remove(position);
                    controller.removeFromCloud(textNote);

                    trashRecyclerViewAdapter.notifyDataSetChanged();

                    Snackbar.make(recyclerView, "Delete " + textNote.getShortText() + " forever?", Snackbar.LENGTH_LONG).setAction("Cancel", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            controller.saveInCloud(textNote);
                            textNotes.add(position, textNote);

                            trashRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }).show();

                }
            }).attachToRecyclerView(recyclerView);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                    int position = viewHolder.getBindingAdapterPosition();
                    TextNote textNote = textNotes.remove(position);
                    controller.restore(textNote);

                    trashRecyclerViewAdapter.notifyDataSetChanged();

                    Snackbar.make(recyclerView, "Restore " + textNote.getShortText() + "?", Snackbar.LENGTH_LONG).setAction("Cancel", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            controller.removeLocal(textNote);
                            textNotes.add(position, textNote);

                            trashRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }).show();

                }
            }).attachToRecyclerView(recyclerView);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}