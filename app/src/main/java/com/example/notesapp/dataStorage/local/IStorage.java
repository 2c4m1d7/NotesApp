package com.example.notesapp.dataStorage.local;


import com.example.notesapp.noteController.Note;
import com.example.notesapp.noteController.TextNote;

/**
 * Interface for creating classes for storing notes on the device
 *
 * @author Alexander Schmidt
 */
public interface IStorage {
    /**
     * Saves the note on the device
     *
     * @param text text
     * @return true if the storage was successful, otherwise false
     */
    TextNote saveText(CharSequence text);

    /**
     * Restores the deleted passed note
     *
     * @param textNote Instance of the class Note
     * @return true if the note was successfully restored, false otherwise
     */
    boolean restoreTextNote(TextNote textNote);

    /**
     * Deletes the passed note
     *
     * @param note Instance of the class Note
     * @return true if the note was deleted successfully, otherwise false
     */
    boolean delete(TextNote note);

    /**
     * Updates the passed note
     *
     * @param textNote Instance of the class Note
     * @return true if the note was updated successfully, false otherwise
     */
    boolean updateTextNote(TextNote textNote);

    /**
     * Cleans the storage from the notes
     *
     * @return true if the storage was successfully cleaned from the notes, false otherwise
     */
    boolean clear();
}
