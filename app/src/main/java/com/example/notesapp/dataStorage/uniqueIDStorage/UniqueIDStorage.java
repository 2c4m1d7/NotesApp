package com.example.notesapp.dataStorage.uniqueIDStorage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class UniqueIDStorage {


    private static final String filename = "UniqueID.txt";
    private String uniqueID;
    private final Context context;

    public UniqueIDStorage(Context context) {
        this.context = context;
    }

    public String getUniqueID() {
        try (FileInputStream fis = context.openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            uniqueID = br.readLine();

        } catch (IOException e) {
            uniqueID = " ";
        }
        return uniqueID;

    }

    public boolean saveID(String id) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {

            bw.write(id);
            return true;
        } catch (IOException ioException) {
            return false;
        }

    }

}
