package com.example.notesapp.notesAppGUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;

import com.example.notesapp.R;

import com.example.notesapp.dataStorage.cloud.URLs;

import com.example.notesapp.dataStorage.uniqueIDStorage.UniqueIDStorage;
import com.example.notesapp.noteController.NoteController;

public class Trash extends AppCompatActivity {


    private RecyclerView recyclerView;
    private NoteController controller;
    private NotesLoader notesLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        controller = new NoteController(this);
        recyclerView = findViewById(R.id.trashRecyclerView);

        loadNotes();
    }

    private void loadNotes() {
        controller.login();
        RequestQueue queue = Volley.newRequestQueue(this);

        notesLoader = new NotesLoader(this, recyclerView);

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL + URLs.SELECT_ALL, notesLoader, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", new UniqueIDStorage(Trash.this).getUniqueID());
                return params;
            }

        };
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trashmenu_menu, menu);
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clearAllFromTrash) {
            controller.clearCloud();
            try {//TODO Check
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loadNotes();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}