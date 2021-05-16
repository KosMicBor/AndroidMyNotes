package gu_android_1089.mynotes.logic;

import java.util.List;

public interface NotesRepoInterface {

    List<Notes> getNotes();

    void addNote(Notes newNote);

    void editNote(int position, Notes note);

    void deleteNoteFromList(int position);
}
