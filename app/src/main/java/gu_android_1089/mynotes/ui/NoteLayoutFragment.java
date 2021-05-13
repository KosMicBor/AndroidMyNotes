package gu_android_1089.mynotes.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Notes;
import gu_android_1089.mynotes.logic.OnEditClickListener;
import gu_android_1089.mynotes.logic.OnNoteClickListener;

public class NoteLayoutFragment extends Fragment implements OnNoteClickListener, OnEditClickListener {

    private static final String NOTE_KEY = "NOTE_KEY";
    private static final String EDIT_NOTE_POSITION_KEY = "EDIT_NOTE_POSITION_KEY";

    private OnNoteClickListener onNoteClick;
    private OnEditClickListener onEditClickListener;
    private Notes restoredNote;
    private int notePosition;


    public NoteLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNoteClickListener) {
            onNoteClick = (OnNoteClickListener) context;
            onEditClickListener = (OnEditClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        onNoteClick = null;
        onEditClickListener = null;
        super.onDetach();
    }


    public static NoteLayoutFragment newInstance(Notes note, int position) {
        NoteLayoutFragment fragment = new NoteLayoutFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_KEY, note);
        args.putInt(EDIT_NOTE_POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            menu.clear();
            inflater.inflate(R.menu.action_menu_note, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_edit) {
            onEditClickListener(restoredNote, notePosition);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        assert getArguments() != null;
        restoredNote = getArguments().getParcelable(NOTE_KEY);
        notePosition = getArguments().getInt(EDIT_NOTE_POSITION_KEY);

        if (restoredNote != null) {
            title.setText(restoredNote.getTitle());
            note.setText(restoredNote.getNote());
            dateField.setText(restoredNote.getDate().toString());
        }
    }

    @Override
    public void onNoteClickListener(Notes note, int position) {

        if (onNoteClick != null) {
            onNoteClick.onNoteClickListener(note, position);
            notePosition = position;
        }
    }

    @Override
    public void onEditClickListener(Notes note, int position) {
        if (onEditClickListener != null) {
            onEditClickListener.onEditClickListener(note, position);
        }
    }
}