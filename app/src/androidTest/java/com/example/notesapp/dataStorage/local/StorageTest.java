package com.example.notesapp.dataStorage.local;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.notesapp.noteController.TextNote;

public class StorageTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    Storage storage = new Storage(appContext);

    @Test
    public void saveTextTest() {

        storage.clear();
        assertEquals(0, storage.getAllNotes().size());
        assertEquals(0, storage.saveText("Hallo1").getId());
        assertEquals("Hallo1", storage.getAllNotes().get(0).getText());
        assertEquals(1, storage.getAllNotes().size());

        assertEquals(1, storage.saveText("Hallo2").getId());
        assertEquals("Hallo2", storage.getAllNotes().get(1).getText());
        assertEquals(2, storage.getAllNotes().size());
        storage.clear();
    }

    @Test
    public void restoreTextNoteTest() {
        storage.clear();
        TextNote note = storage.saveText("Hallo");
        TextNote note1 = storage.saveText("Hallo1");

        assertTrue(storage.delete(note));
        assertFalse(storage.delete(note));
        assertTrue(storage.delete(note1));


        assertTrue(storage.restoreTextNote(note));
        assertTrue(storage.restoreTextNote(note1));

        TextNote restoredNote = storage.getAllNotes().get(0);
        TextNote restoredNote1 = storage.getAllNotes().get(1);

        assertEquals(0, restoredNote.getId());
        assertEquals("Hallo", restoredNote.getText());
        assertEquals(1, restoredNote1.getId());
        assertEquals("Hallo1", restoredNote1.getText());
        storage.clear();
    }

    @Test
    public void deleteTest() {
        storage.clear();
        TextNote note = storage.saveText("Hallo");
        TextNote note1 = storage.saveText("Hallo1");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        TextNote problematicNote = new TextNote(1, "c", formatter.format(LocalDateTime.of(2000, 11, 4, 20, 14, 33)));
        assertFalse(storage.delete(problematicNote));

        assertTrue(storage.delete(note));
        assertEquals(1, storage.getAllNotes().size());
        assertFalse(storage.delete(note));
        assertEquals(1, storage.getAllNotes().size());
        assertTrue(storage.delete(note1));
        assertEquals(0, storage.getAllNotes().size());


        storage.clear();
    }

    @Test
    public void updateTextNoteTest() {
        storage.clear();
        TextNote note = storage.saveText("a");
        TextNote note1 = storage.saveText("a1");
        assertEquals("a", storage.getAllNotes().get(0).getText());
        assertEquals("a1", storage.getAllNotes().get(1).getText());
        note.setText("b");
        note1.setText("b1");
        assertTrue(storage.updateTextNote(note));
        assertTrue(storage.updateTextNote(note1));
        assertEquals("b", storage.getAllNotes().get(0).getText());
        assertEquals("b1", storage.getAllNotes().get(1).getText());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        TextNote problematicNote = new TextNote(3, "c", formatter.format(LocalDateTime.now()));
        assertFalse(storage.updateTextNote(problematicNote));
        storage.clear();
    }

    @Test
    public void clearTest() {
        storage.clear();
        storage.saveText("Hallo");
        storage.saveText("Hallo1");
        assertEquals(2, storage.getAllNotes().size());

        assertTrue(storage.clear());
        assertEquals(0, storage.getAllNotes().size());
        storage.clear();
    }
}