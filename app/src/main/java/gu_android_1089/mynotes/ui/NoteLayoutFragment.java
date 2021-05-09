package gu_android_1089.mynotes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Notes;
import gu_android_1089.mynotes.logic.OnNoteClick;

public class NoteLayoutFragment extends Fragment implements OnNoteClick {

    private static final String NOTE_KEY = "NOTE_KEY";

    private OnNoteClick onNoteClick;

    public NoteLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNoteClick) {
            onNoteClick = (OnNoteClick) context;
        }
    }

    @Override
    public void onDetach() {
        onNoteClick = null;
        super.onDetach();
    }


    public static NoteLayoutFragment newInstance(Notes note) {
        NoteLayoutFragment fragment = new NoteLayoutFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_KEY, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.title_input);
        TextView note = view.findViewById(R.id.note_input);
        TextView dateField = view.findViewById(R.id.note_date_text);

        Notes restoredNote = getArguments().getParcelable(NOTE_KEY);

        title.setText(restoredNote.getTitle());
        note.setText(restoredNote.getNote());
        dateField.setText(restoredNote.getDate().toString());
    }

    @Override
    public void onNoteClick(Notes note) {

        if (onNoteClick != null) {
            onNoteClick.onNoteClick(note);
        }
    }
}