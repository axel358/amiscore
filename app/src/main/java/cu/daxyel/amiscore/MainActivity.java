package cu.daxyel.amiscore;

import android.os.Bundle;
import android.view.MenuItem;
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
import android.view.View;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Intent;
import java.util.Locale;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.view.Menu;
import cu.daxyel.amiscore.fragments.StatisticsFragment;
import cu.daxyel.amiscore.fragments.AboutFragment;

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

        Locale locale=new Locale(sp.getString("pref_language", "es"));
        Locale.setDefault(locale);
        Resources resources=getResources(); 
        Configuration con=resources.getConfiguration();
        con.setLocale(locale);
        resources.updateConfiguration(con, resources.getDisplayMetrics());

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
        relaunchApp();
    }

    public void relaunchApp() {
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
            case R.id.nav_manage_diagnosis:
                fm.beginTransaction().replace(R.id.main_container, new ManageDiagnosticsFragment()).commit();
                selected = 1;
                break;
            case R.id.nav_manage_indexes:
                fm.beginTransaction().replace(R.id.main_container, new ManageIndexesFragment()).commit();
                selected = 2;
                break;
            case R.id.nav_statistics:
                fm.beginTransaction().replace(R.id.main_container, new StatisticsFragment()).commit();
                selected = 3;
                break;
            case R.id.nav_about:
                fm.beginTransaction().replace(R.id.main_container, new AboutFragment()).commit();
                selected = 4;
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem lang=menu.add(0, 2, 0, R.string.lang);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2) {
            if (Locale.getDefault().toString().startsWith("es")) {
                sp.edit().putString("pref_language", "en").commit();
            } else {
                sp.edit().putString("pref_language", "es").commit();
            }
            relaunchApp();
        }
        return super.onOptionsItemSelected(item);
    }

}
