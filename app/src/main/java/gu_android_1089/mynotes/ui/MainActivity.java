package gu_android_1089.mynotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Note;
import gu_android_1089.mynotes.logic.OnCreateBtnClickListener;
import gu_android_1089.mynotes.logic.OnEditClickListener;
import gu_android_1089.mynotes.logic.OnNoteClickListener;
import gu_android_1089.mynotes.logic.SignInClickListener;

public class MainActivity extends AppCompatActivity implements OnNoteClickListener, OnCreateBtnClickListener
        , OnEditClickListener, SignInClickListener {

    private static final String KEY = "KEY";
    private static final String POSITION = "POSITION";

    private Toolbar toolbar;
    private Note noteForSave;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private int noteClickedPosition;

    public void setNoteForSave(Note noteForSave) {
        this.noteForSave = noteForSave;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = initToolbar();
        initDrawer(toolbar);

        if (savedInstanceState == null) {

            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_main_list);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_list, new SignInFragment())
                        .commit();
                toolbar.setVisibility(View.GONE);
            }
        }
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (MainActivity.this.navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (MainActivity.this.navigateFragment(id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id) {

        switch (id) {
            case R.id.main_menu_settings:
                openFragmentFromDrawer(new SettingsFragment());
                return true;
            case R.id.main_menu_main:
                openFragmentFromDrawer(new MainListFragment());
                return true;
            case R.id.main_menu_about:
                openFragmentFromDrawer(new AboutFragment());
                return true;
        }

        return false;
    }

    private void openFragmentFromDrawer(Fragment fragment) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main_list, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY, noteForSave);
        outState.putInt(POSITION, noteClickedPosition);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragmentManager.popBackStack();
        Note note = savedInstanceState.getParcelable(KEY);

        if (note != null) {
            noteForSave = note;
            noteClickedPosition = savedInstanceState.getInt(POSITION);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_list, NoteLayoutFragment.newInstance(note, noteClickedPosition))
                        .addToBackStack(null)
                        .commit();

            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.note_layout_fragment, NoteLayoutFragment.newInstance(note, noteClickedPosition))
                        .commit();
            }
        }
    }

    @Override
    public void onNoteClickListener(Note note, int position) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main_list, NoteLayoutFragment.newInstance(note, position))
                    .addToBackStack(null)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.note_layout_fragment, NoteLayoutFragment.newInstance(note, position))
                    .commit();
        }
        noteClickedPosition = position;
        noteForSave = note;
    }

    @Override
    public void onCreateBtnClickListener(Fragment fragment) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main_list, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {

            fragmentManager.beginTransaction()
                    .replace(R.id.note_layout_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onEditClickListener(Note note, int position) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main_list, NoteEditFragment.newInstance(note, position))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void signInClickListener(Fragment fragment, String name, String email) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main_list, fragment)
                .commit();
        toolbar.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        TextView userName = findViewById(R.id.drawer_user_name);
        TextView userEmail = findViewById(R.id.drawer_user_email);
        userName.setText(name);
        userEmail.setText(email);
    }
}