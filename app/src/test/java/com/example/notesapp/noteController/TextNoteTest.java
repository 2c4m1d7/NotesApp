package com.example.notesapp.noteController;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextNoteTest {

    TextNote note1 = new TextNote(0, "a" + System.lineSeparator(), "2022.11.9 12:22:22");
    TextNote note2 = new TextNote(1, "123456789101", "2022.11.6 12:22:22");


    @Test
    public void getShortText() {
        assertEquals("a...", note1.getShortText());
        assertEquals("1234567891...", note2.getShortText());
    }

    @Test
    public void compareToTest() {

        assertEquals(-1, note1.compareTo(note2));
        assertEquals(1, note2.compareTo(note1));
        assertEquals(0, note2.compareTo(note2));
    }
}