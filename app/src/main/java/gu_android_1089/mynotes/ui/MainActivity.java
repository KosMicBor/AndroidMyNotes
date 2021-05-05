package gu_android_1089.mynotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

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

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

        if (savedInstanceState == null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_main_list);

                if (fragment == null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_main_list, new MainListFragment())
                            .commit();
                }
            }
        }
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
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {

        return false;
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public void onNoteClick(Notes note) {

        FragmentManager fragmentManager = getSupportFragmentManager();

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
}