package com.example.notesapp.dataStorage.cloud.listeners;


import android.content.Context;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.notesapp.dataStorage.cloud.Cloud;
import com.example.notesapp.dataStorage.cloud.URLs;
import com.example.notesapp.dataStorage.uniqueIDStorage.UniqueIDStorage;


public class IsNewAccountListener implements Response.Listener<String> {
    private Context context;
    private String user;

    public IsNewAccountListener(Context context, String user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onResponse(String response) {
        UniqueIDStorage uniqueIDStorage = new UniqueIDStorage(context);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (user.equals(" ")) {
                new Cloud(context).loginOrAddUser(UUID.randomUUID().toString());
            } else if (uniqueIDStorage.getUniqueID().equals(" ")) {
                if (jsonObject.getBoolean("toCreate")) {
                    uniqueIDStorage.saveID(user);
                    add();
                } else {
                    new Cloud(context).loginOrAddUser(UUID.randomUUID().toString());
                }
            } else {
                if (jsonObject.getBoolean("toCreate")) {
                        add();
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void add() {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL + URLs.ADD_USER, response -> {},
                error -> {
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
}



