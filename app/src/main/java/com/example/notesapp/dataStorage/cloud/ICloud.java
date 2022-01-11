package com.example.notesapp.dataStorage.cloud;

import com.example.notesapp.noteController.TextNote;

/**
 * An interface for creating a class that works with an external database
 *
 * @author Alexander Schmidt
 */
public interface ICloud {
    /**
     * Saves the note in an external database
     *
     * @param textNote Instance of the class Note
     */
    void save(TextNote textNote);

    /**
     * Deletes a note
     *
     * @param textNote Instance of the class Note
     */
    void delete(TextNote textNote);

    /**
     * Deletes all saved notes
     */
    void clear();
}
