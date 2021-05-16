package gu_android_1089.mynotes.logic;

import java.util.List;

public interface NotesRepoInterface {

    void getNotes(CallbackInterface<List<Note>> callback);

    void addNote(String title, String noteText, CallbackInterface<Note> newNote);

    void editNote(Note note, CallbackInterface<Note> editNoteCallback);

    void deleteNote(Note note, CallbackInterface<Note> removeNoteCallback);
}
