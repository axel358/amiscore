package cu.daxyel.amiscore;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;

import cu.daxyel.amiscore.fragments.DiagnoseFragment;
import cu.daxyel.amiscore.fragments.ManageIndexesFragment;
import cu.daxyel.amiscore.fragments.ManageDiagnosticsFragment;

import android.view.View.OnClickListener;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Intent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fm;
    private int selected;
    private NavigationView navigationView;
    private SharedPreferences sp;
    private boolean isNightMode;
    private String SELECTED_ITEM_KEY = "selected_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if ((isNightMode = sp.getBoolean("night_mode", false)))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        selected = getIntent().getIntExtra(SELECTED_ITEM_KEY, 0);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(selected).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(selected));


    }

    public void toggleTheme(View j) {

        sp.edit().putBoolean("night_mode", !isNightMode).commit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra(SELECTED_ITEM_KEY, selected).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        }).run();

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_ITEM_KEY, selected);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        selected = savedInstanceState.getInt(SELECTED_ITEM_KEY);
        navigationView.getMenu().getItem(selected).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(selected));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_diagnose:
                fm.beginTransaction().replace(R.id.main_container, new DiagnoseFragment()).commit();
                selected = 0;
                break;
            case R.id.nav_manage_indexes:
                fm.beginTransaction().replace(R.id.main_container, new ManageIndexesFragment()).commit();
                selected = 3;
                break;
            case R.id.nav_manage_diagnosis:
                fm.beginTransaction().replace(R.id.main_container, new ManageDiagnosticsFragment()).commit();
                selected = 1;
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
