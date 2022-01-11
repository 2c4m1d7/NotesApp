package com.example.notesapp.dataStorage.cloud;


import android.content.Context;

import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;

import com.example.notesapp.dataStorage.cloud.listeners.IsNewAccountListener;
import com.example.notesapp.dataStorage.uniqueIDStorage.UniqueIDStorage;
import com.example.notesapp.noteController.TextNote;


public class Cloud implements ICloud {


    private final Context context;

    private RequestQueue queue;
    private String user;

    public Cloud(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        user = new UniqueIDStorage(context).getUniqueID();
    }


    @Override
    public void save(TextNote textNote) {

        String text = textNote.getText();
        String date = textNote.getDate();


        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL + URLs.INSERT, response -> {

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet connection is missing", Toast.LENGTH_SHORT).show();
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

                params.put("text", text);
                params.put("date", date);
                params.put("user", user);
                return params;
            }
        };
        queue.add(request);

    }

    @Override
    public void delete(TextNote textNote) {

        String text = textNote.getText();
        String date = textNote.getDate();


        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL + URLs.DELETE, response -> {

        }, error -> {

        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("text", text);
                params.put("date", date);
                params.put("user", user);
                return params;
            }
        };
        queue.add(request);


    }

    @Override
    public void clear() {

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL + URLs.CLEAR, response -> {

        }, error -> {

        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", user);
                return params;
            }
        };
        queue.add(request);

    }


    public void loginOrAddUser(String user) {


        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL + URLs.IS_NEW_ACCOUNT,
                new IsNewAccountListener(context, user),
                error -> Toast.makeText(context, "Internet connection is missing", Toast.LENGTH_SHORT).show()) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", user);
                return params;
            }
        };
        queue.add(request);

    }


}
