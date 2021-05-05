package gu_android_1089.mynotes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Notes;
import gu_android_1089.mynotes.logic.NotesRepo;
import gu_android_1089.mynotes.logic.OnNoteClick;


public class MainListFragment extends Fragment implements OnNoteClick {

    private OnNoteClick onNoteClick;

    public MainListFragment() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.action_menu_main, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Notes> notes = new NotesRepo().getNotes();
        LinearLayoutCompat mainListLayout = view.findViewById(R.id.list_fragment);

        for (Notes note : notes) {

            View noteView = LayoutInflater.from(requireContext()).inflate(R.layout.note_list_item,
                    mainListLayout, false);

            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNoteClick(note);
                }
            });

            TextView title = noteView.findViewById(R.id.notes_title_in_list);
            title.setText(note.getTitle());

            mainListLayout.addView(noteView);
        }
    }


    @Override
    public void onNoteClick(Notes note) {
        if (onNoteClick != null) {
            onNoteClick.onNoteClick(note);
        }
    }
}
