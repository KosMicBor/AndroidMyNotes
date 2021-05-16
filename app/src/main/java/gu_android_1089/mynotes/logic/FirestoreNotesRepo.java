package gu_android_1089.mynotes.logic;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FirestoreNotesRepo implements NotesRepoInterface {

    private static final String NOTES = "notes";
    private static final String TITLE = "title";
    private static final String DATE = "createdDate";
    private static final String NOTE_TEXT = "noteText";
    private static final String CATEGORY = "category";

    private final FirebaseFirestore firebase = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(CallbackInterface<List<Note>> callback) {
        ArrayList<Note> noteList = new ArrayList<>();
        firebase.collection(NOTES)
                .orderBy(DATE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<DocumentSnapshot> docs = task.getResult().getDocuments();
                            for (DocumentSnapshot doc : docs) {
                                String id = doc.getId();
                                String title = doc.getString(TITLE);
                                String noteText = doc.getString(NOTE_TEXT);
                                Date createdDate = doc.getDate(DATE);
                                String category = doc.getString(CATEGORY);

                                noteList.add(new Note(id, title, noteText, category, createdDate));
                            }

                            callback.onSuccess(noteList);

                        } else {

                            callback.onError(task.getException());

                        }
                    }
                });
    }

    @Override
    public void addNote(String title, String noteText, CallbackInterface<Note> newNote) {

        HashMap<String, Object> data = new HashMap<>();
        Date date = new Date();
        String category = "Standard";
        data.put(TITLE, title);
        data.put(NOTE_TEXT, noteText);
        data.put(DATE, date);
        data.put(CATEGORY, category);

        firebase.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if (task.isSuccessful()) {

                            newNote.onSuccess(new Note(task.getResult().getId(), title,
                                    noteText, category, date));

                        } else {
                            newNote.onError(task.getException());
                        }
                    }
                });

    }

    @Override
    public void editNote(Note note, CallbackInterface<Note> editNoteCallback) {

        HashMap<String, Object> data = new HashMap<>();
        data.put(TITLE, note.getTitle());
        data.put(NOTE_TEXT, note.getNote());
        data.put(DATE, note.getDate());
        data.put(CATEGORY, note.getCategory());
        firebase.collection(NOTES)
                .document(note.getId())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            editNoteCallback.onSuccess(note);
                        } else {
                            editNoteCallback.onError(task.getException());
                        }
                    }
                });

    }

    @Override
    public void deleteNote(Note note, CallbackInterface<Note> removeNoteCallback) {

        firebase.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            removeNoteCallback.onSuccess(note);
                        } else {
                            removeNoteCallback.onError(task.getException());
                        }
                    }
                });
    }
}
