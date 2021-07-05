package cu.daxyel.amiscore;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import androidx.fragment.app.FragmentManager;
import cu.daxyel.amiscore.fragments.DiagnoseFragment;
import cu.daxyel.amiscore.fragments.ManageIndexesFragment;
import cu.daxyel.amiscore.fragments.ManageDiagnosticsFragment;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener
{
	private FragmentManager fm;
	MenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
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

        NavigationView navigationView = findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));


	}

    @Override
    public void onBackPressed()
	{
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			super.onBackPressed();
		}
	}

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
            case R.id.nav_diagnose:
                fm.beginTransaction().replace(R.id.main_container, new DiagnoseFragment()).commit();
                break;
            case R.id.nav_manage_indexes:
                fm.beginTransaction().replace(R.id.main_container, new ManageIndexesFragment()).commit();
                break;
            case R.id.nav_manage_diagnosis:
                fm.beginTransaction().replace(R.id.main_container, new ManageDiagnosticsFragment()).commit();
                break;
		}

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
	}


}
