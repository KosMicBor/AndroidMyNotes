package gu_android_1089.mynotes.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import gu_android_1089.mynotes.logic.Notes;
import gu_android_1089.mynotes.logic.NotesRepo;
import gu_android_1089.mynotes.logic.NotesRepoInterface;

public class GeneralViewModel extends ViewModel {

    private final MutableLiveData<List<Notes>> notesLiveData = new MutableLiveData<>();

    private final MutableLiveData<Notes> newNoteLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> noteDeleteLiveData = new MutableLiveData<>();
    private final MutableLiveData<Notes> noteEditLiveData = new MutableLiveData<>();

    private NotesRepo repository;

    public List<Notes> getNotes() {
        return notes;
    }

    private List<Notes> notes;

    public LiveData<List<Notes>> getNotesLiveData() {
        return notesLiveData;
    }

    public void requestNotes() {
        repository = new NotesRepo();
        notes = repository.getNotes();
        notesLiveData.setValue(notes);
    }

    public LiveData<Notes> getNewNoteLiveData() {
        return newNoteLiveData;
    }

    public void addNewNoteLiveData(Notes newNote) {
        repository.addNote(newNote);
        newNoteLiveData.setValue(newNote);
    }

    public void editNoteLiveData(Notes editedNote, int position) {
        repository.editNote(position, editedNote);
        newNoteLiveData.setValue(editedNote);
    }

    public void deleteNoteClicked(int position) {
        repository.deleteNoteFromList(position);
        noteDeleteLiveData.setValue(position);
    }

    public LiveData<Integer> getNoteDeleteLiveData() {
        return noteDeleteLiveData;
    }

    public MutableLiveData<Notes> getNoteEditLiveData() {
        return noteEditLiveData;
    }
}
