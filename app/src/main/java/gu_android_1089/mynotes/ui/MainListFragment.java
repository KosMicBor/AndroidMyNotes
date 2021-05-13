package gu_android_1089.mynotes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Notes;
import gu_android_1089.mynotes.logic.OnCreateBtnClickListener;
import gu_android_1089.mynotes.logic.OnNoteClickListener;


public class MainListFragment extends Fragment implements OnCreateBtnClickListener {

    private OnCreateBtnClickListener onCreateBtnClickListener;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        CoordinatorLayout coordinatorLayout = view.findViewById(R.id.coordinator_list);
        RecyclerView mainList = view.findViewById(R.id.list_fragment);
        mainList.setLayoutManager(new LinearLayoutManager(
                mainList.getContext(), RecyclerView.VERTICAL, false));

        MainListAdapter mainListAdapter = new MainListAdapter();

        mainList.setAdapter(mainListAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        mainList.addItemDecoration(itemDecoration);

        mainListAdapter.notifyDataSetChanged();

        coordinatorLayout.findViewById(R.id.fab).setOnClickListener(v ->
                onCreateBtnClickListener(new CreateNoteFragment()));
    }

    @Override
    public void onCreateBtnClickListener(Fragment fragment) {
        if (onCreateBtnClickListener != null) {
            onCreateBtnClickListener.onCreateBtnClickListener(fragment);
        }
    }
}
