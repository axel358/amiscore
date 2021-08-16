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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fm;
    private int selected;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(selected).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(selected));


    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("selected_item", selected);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        selected =savedInstanceState.getInt("selected_item");
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
                selected=0;
                break;
            case R.id.nav_manage_indexes:
                fm.beginTransaction().replace(R.id.main_container, new ManageIndexesFragment()).commit();
                selected=3;
                break;
            case R.id.nav_manage_diagnosis:
                fm.beginTransaction().replace(R.id.main_container, new ManageDiagnosticsFragment()).commit();
                selected=1;
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
