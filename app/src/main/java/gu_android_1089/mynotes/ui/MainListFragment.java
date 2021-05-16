package gu_android_1089.mynotes.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.OnCreateBtnClickListener;

public class MainListFragment extends Fragment implements OnCreateBtnClickListener {

    private static final int MY_DEFAULT_DURATION = 500;
    private OnCreateBtnClickListener onCreateBtnClickListener;
    private MainListAdapter mainListAdapter;
    private GeneralViewModel viewModel;

    public MainListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnCreateBtnClickListener) {
            onCreateBtnClickListener = (OnCreateBtnClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        onCreateBtnClickListener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        viewModel = new ViewModelProvider(requireActivity()).get(GeneralViewModel.class);

        if (savedInstanceState == null) {
            viewModel.requestNotes();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_list, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainListAdapter = new MainListAdapter(this);

        CoordinatorLayout coordinatorLayout = view.findViewById(R.id.coordinator_list);
        RecyclerView mainList = view.findViewById(R.id.list_recycler_view);
        mainList.setLayoutManager(new LinearLayoutManager(
                mainList.getContext(), RecyclerView.VERTICAL, false));

        mainList.setAdapter(mainListAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(mainList.getContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        mainList.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        mainList.setItemAnimator(animator);

        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), notes -> {
            mainListAdapter.setNotes(notes);
        });

        coordinatorLayout.findViewById(R.id.fab).setOnClickListener(v -> {
            onCreateBtnClickListener(new CreateNoteFragment());
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            coordinatorLayout.findViewById(R.id.fab).setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.action_menu_main, menu);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = mainListAdapter.getSelectedItemPosition();

        if (item.getItemId() == R.id.context_menu_action_delete) {
            viewModel.deleteNoteClicked(mainListAdapter.getNote(position));
            ((MainActivity) requireActivity()).setNoteForSave(null);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.note_layout_fragment, NoteLayoutFragment.newInstance(null, 0))
                .commit();
            }
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateBtnClickListener(Fragment fragment) {
        if (onCreateBtnClickListener != null) {
            onCreateBtnClickListener.onCreateBtnClickListener(fragment);
        }
    }
}
