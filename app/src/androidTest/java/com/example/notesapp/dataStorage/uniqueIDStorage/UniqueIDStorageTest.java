package com.example.notesapp.dataStorage.uniqueIDStorage;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.io.File;
import java.util.UUID;

public class UniqueIDStorageTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    UniqueIDStorage uniqueIDStorage = new UniqueIDStorage(appContext);
    String filename = "UniqueID.txt";
    File fileWithUniqueID = appContext.getFileStreamPath(filename);

    @Test
    public void uniqueIDStorageTest() {
        fileWithUniqueID.delete();
        assertEquals(" ", uniqueIDStorage.getUniqueID());
        String id = UUID.randomUUID().toString();
        assertTrue(uniqueIDStorage.saveID(id));
        assertEquals(id, uniqueIDStorage.getUniqueID());
    }


}