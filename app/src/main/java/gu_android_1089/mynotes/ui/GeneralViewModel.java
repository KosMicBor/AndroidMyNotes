package gu_android_1089.mynotes.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import gu_android_1089.mynotes.logic.CallbackInterface;
import gu_android_1089.mynotes.logic.FirestoreNotesRepo;
import gu_android_1089.mynotes.logic.Note;
import gu_android_1089.mynotes.logic.NotesRepoInterface;

public class GeneralViewModel extends ViewModel {

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    private final NotesRepoInterface repository = new FirestoreNotesRepo();

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public void requestNotes() {
        repository.getNotes(new CallbackInterface<List<Note>>() {
            @Override
            public void onSuccess(List<Note> value) {
                notesLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void addNewNoteLiveData(String title, String noteText) {
        repository.addNote(title, noteText, new CallbackInterface<Note>() {
            @Override
            public void onSuccess(Note value) {
                if (notesLiveData.getValue() != null){
                    ArrayList<Note> notes = new ArrayList<>(notesLiveData.getValue());

                    notes.add(value);

                    notesLiveData.setValue(notes);
                } else {

                    ArrayList<Note> newEmptyNotes = new ArrayList<>();
                    newEmptyNotes.add(value);
                    notesLiveData.setValue(newEmptyNotes);
                }

            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void editNoteLiveData(Note editedNote, int position) {
        repository.editNote(editedNote, new CallbackInterface<Note>() {
            @Override
            public void onSuccess(Note value) {
                if (notesLiveData.getValue() != null){
                    ArrayList<Note> notes = new ArrayList<>(notesLiveData.getValue());
                    notes.set(position, editedNote);
                    notesLiveData.setValue(notes);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
//        repository.editNote(position, editedNote);
//        newNoteLiveData.setValue(editedNote);
    }

    public void deleteNoteClicked(Note note) {
        repository.deleteNote(note, new CallbackInterface<Note>() {
            @Override
            public void onSuccess(Note value) {

                if (notesLiveData.getValue() != null){
                    ArrayList<Note> notes = new ArrayList<>(notesLiveData.getValue());
                    notes.remove(value);
                    notesLiveData.setValue(notes);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
