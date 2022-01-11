package com.example.notesapp.noteController;


import java.util.ArrayList;
import java.util.List;

/**
 * Interface for the creation of the class that manages notes
 *
 * @author Alexander Schmidt
 */
public interface INoteController {
    /**
     * Method for saving text
     *
     * @param text Text for storage
     * @return true if text was successfully saved in the note, otherwise false
     */
    Note save(CharSequence text);


    /**
     * Updates the passed note
     *
     * @param textNote The note that should be updated
     * @return true if the note was updated successfully, false otherwise
     */
    boolean update(TextNote textNote);

    /**
     * Sorts the notes by name
     *
     * @param textNotes Notes that should be sorted
     */
    void sortByName(List<TextNote> textNotes);

    /**
     * Sorts the notes by creation or modification time, from early to later time
     *
     * @param textNotes Notes that should be sorted
     */
    void sortNewToOld(List<TextNote> textNotes);

    /**
     * Sorts the notes by creation or modification time, from later to early time
     *
     * @param textNotes Notes that should be sorted
     */
    void sortOldToNew(List<TextNote> textNotes);

    /**
     * Deletes the note from memory by ID number
     *
     * @param textNote The note to be removed from the device
     * @return true if the note was deleted successfully, false otherwise
     */
    boolean removeLocal(TextNote textNote);

    /**
     * Deletes a note from the external database
     *
     * @param textNote The note that should be removed from the cloud
     */
    void removeFromCloud(TextNote textNote);


    /**
     * Recovers the deleted note
     *
     * @param textNote The note that should be restored
     * @return true if recovery was successful, false otherwise
     */
    boolean restore(TextNote textNote);


    /**
     * Recovers the deleted notes
     *
     * @param textNotes Notes that should be restored
     * @return true if recovery was successful, false otherwise
     */
    boolean restore(ArrayList<TextNote> textNotes);

    /**
     * Saves the passed note in a cloud
     * @param textNote
     */
    void saveInCloud(TextNote textNote);

    /**
     * Deletes all locally saved notes
     *
     * @param textNotes Notes that should be cleared
     * @return
     */
    boolean clearLocal(ArrayList<TextNote> textNotes);

    /**
     *Deletes all notes stored in the cloud
     */
    void clearCloud();
}
