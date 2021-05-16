package gu_android_1089.mynotes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Note;
import gu_android_1089.mynotes.logic.OnEditClickListener;

public class NoteEditFragment extends Fragment implements OnEditClickListener {

    private static final String EDIT_NOTE_KEY = "EDIT_NOTE_KEY";
    private static final String EDIT_NOTE_POSITION_KEY = "EDIT_NOTE_POSITION_KEY";
    private Note editedNote;
    TextInputEditText title;
    TextInputEditText noteText;
    private GeneralViewModel viewModel;
    private int editNotePosition;

    public NoteEditFragment() {
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
            inflater.inflate(R.menu.action_menu_edit, menu);
        }
    }


    public static NoteEditFragment newInstance(Note note, int position) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(EDIT_NOTE_KEY, note);
        args.putInt(EDIT_NOTE_POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        editNotePosition = getArguments().getInt(EDIT_NOTE_POSITION_KEY);

        viewModel = new ViewModelProvider(requireActivity()).get(GeneralViewModel.class);

        title = view.findViewById(R.id.edit_title_input);
        noteText = view.findViewById(R.id.edit_content_input);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.action_edit_done) {
            assert getArguments() != null;
            String titleNew = title.getText().toString();
            String noteTextNew = noteText.getText().toString();

            Note oldNote = getArguments().getParcelable(EDIT_NOTE_KEY);
            editedNote = new Note (oldNote.getId(), titleNew, noteTextNew, oldNote.getCategory(), oldNote.getDate());
            viewModel.editNoteLiveData(editedNote, editNotePosition);
            fm.popBackStack();
            fm.beginTransaction()
                    .replace(R.id.fragment_main_list, NoteLayoutFragment.newInstance(editedNote, editNotePosition))
                    .commit();
            ((MainActivity) requireActivity()).setNoteForSave(editedNote);
        }

        if (id == R.id.action_edit_abort) {
            requireActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onEditClickListener(Note note, int position) {
        editedNote = note;
        editNotePosition = position;
    }
}
