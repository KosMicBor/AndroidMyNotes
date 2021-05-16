package gu_android_1089.mynotes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Note;

public class CreateNoteFragment extends Fragment {

    TextInputEditText title;
    TextInputEditText noteText;
    private GeneralViewModel viewModel;

    public CreateNoteFragment() {
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
            inflater.inflate(R.menu.action_menu_create, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_create_done) {
            viewModel.addNewNoteLiveData(Objects.requireNonNull(title.getText()).toString(),
                    Objects.requireNonNull(noteText.getText()).toString());
            requireActivity().onBackPressed();
        }

        if (id == R.id.action_create_abort) {
            requireActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(GeneralViewModel.class);
        title = view.findViewById(R.id.create_title_input);
        noteText = view.findViewById(R.id.create_content_input);
    }
}