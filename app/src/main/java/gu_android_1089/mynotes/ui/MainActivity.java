package gu_android_1089.mynotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Notes;
import gu_android_1089.mynotes.logic.OnNoteClick;

public class MainActivity extends AppCompatActivity implements OnNoteClick {

    private static final String KEY = "KEY";
    private Notes noteForSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.main_fragment);

                if (fragment == null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, new MainListFragment())
                            .commit();
                }
            }
        }
    }

    @Override
    public void onNoteClick(Notes note) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, NoteLayoutFragment.newInstance(note))
                    .addToBackStack(null)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.note_layout_fragment, NoteLayoutFragment.newInstance(note))
                    .commit();
        }

        noteForSave = note;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY, noteForSave);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        Notes note = savedInstanceState.getParcelable(KEY);

        if (note != null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, NoteLayoutFragment.newInstance(note))
                        .addToBackStack(null)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.note_layout_fragment, NoteLayoutFragment.newInstance(note))
                        .commit();
            }
        }
    }
}