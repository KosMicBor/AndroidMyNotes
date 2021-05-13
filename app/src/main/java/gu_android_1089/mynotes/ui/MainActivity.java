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
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Notes;
import gu_android_1089.mynotes.logic.OnCreateBtnClickListener;
import gu_android_1089.mynotes.logic.OnEditClickListener;
import gu_android_1089.mynotes.logic.OnNoteClickListener;

public class MainActivity extends AppCompatActivity implements OnNoteClickListener, OnCreateBtnClickListener
, OnEditClickListener {

    private static final String KEY = "KEY";
    private Notes noteForSave;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

        if (savedInstanceState == null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_main_list);

                if (fragment == null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_main_list, new MainListFragment())
                            .commit();
                }
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
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_landscape, fragment)
                    .addToBackStack(null)
                    .commit();
        }
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
            noteForSave = note;

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_list, NoteLayoutFragment.newInstance(note))
                        .addToBackStack(null)
                        .commit();

            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.note_layout_fragment, NoteLayoutFragment.newInstance(note))
                        .commit();
            }
        }
    }

    @Override
    public void onNoteClickListener(Notes note) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main_list, NoteLayoutFragment.newInstance(note))
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
    public void onEditClickListener(Notes note) {
        fragmentManager.beginTransaction()
                .replace(R.id.created_note_layout, new NoteEditFragment())
                .addToBackStack(null)
                .commit();

    }
}