package com.example.notesapp.noteController;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NoteControllerTest {

    TextNote note1 = new TextNote(0, "a", "2022.11.9 12:22:22");
    TextNote note2 = new TextNote(1, "b", "2022.11.6 12:22:22");
    TextNote note3 = new TextNote(2, "c", "2022.11.10 12:22:22");
    List<TextNote> noteList = new ArrayList<>();

    @Before
    public void before() {
        noteList.add(note1);
        noteList.add(note2);
        noteList.add(note3);
    }

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    NoteController controller = new NoteController(appContext);

    @Test
    public void sortByNameTest() {

        controller.sortByName(noteList);
        assertEquals(0, noteList.get(0).getId());
        assertEquals(1, noteList.get(1).getId());
        assertEquals(2, noteList.get(2).getId());
    }

    @Test
    public void sortNewToOldTest() {
        controller.sortNewToOld(noteList);
        assertEquals(2, noteList.get(0).getId());
        assertEquals(0, noteList.get(1).getId());
        assertEquals(1, noteList.get(2).getId());
    }

    @Test
    public void sortOldToNewTest() {
        controller.sortOldToNew(noteList);
        assertEquals(1, noteList.get(0).getId());
        assertEquals(0, noteList.get(1).getId());
        assertEquals(2, noteList.get(2).getId());
    }
}