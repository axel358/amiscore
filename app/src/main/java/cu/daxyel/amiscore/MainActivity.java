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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener
{
	private ListView criteriasLv;
	private TextView diagnosisTv;
	private int index;
	private int total;
	private int critValueMed,critValueHigh;
	private ProgressBar diagnosisPb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

		criteriasLv =  findViewById(R.id.criteria_lv);
		diagnosisTv =  findViewById(R.id.diagnosis_tv);
		diagnosisPb =  findViewById(R.id.diagnosis_pb);

		//Subject to changes depending on the loaded index

		total = 121;
		critValueMed = 58;
		critValueHigh = 81;

		diagnosisPb.setMax(total);

		ArrayList < Criteria > criterias = new ArrayList<Criteria>();
		criterias.add(new Criteria(21, "Adenomatosis intensa de la aorta"));
		criterias.add(new Criteria(25, "Patrón gaseoso aumentado en ultrasonido"));
		criterias.add(new Criteria(36, "Fibrilacion articular"));
		criterias.add(new Criteria(39, "Lactato mayor a 2.1"));

		criteriasLv.setAdapter(new CriteriaAdapter(this, criterias));

		updateProbability();


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
    public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{

		}
        return super.onOptionsItemSelected(item);
	}

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{

		}

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
	}

	public void updateProbability()
	{
		if (index >  critValueHigh)
		{
			diagnosisTv.setText(getString(R.string.very_high_probability) + " " + index + "/" + total);
		}
		else if (index > critValueMed)
		{
			diagnosisTv.setText(getString(R.string.high_probability) + " " + index + "/" + total);
		}
		else
		{
			diagnosisTv.setText(getString(R.string.low_probability) + " " + index + "/" + total);
		}
	}

	class CriteriaAdapter extends ArrayAdapter<Criteria>
	{
		public CriteriaAdapter(Context context, ArrayList<Criteria> criterias)
		{
			super(context, R.layout.entry_criteria, criterias);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view = getLayoutInflater().inflate(R.layout.entry_criteria, null);

			CheckBox criteriaChkbx = view.findViewById(R.id.criteria_chkbx);

			final Criteria criteria = getItem(position);

			criteriaChkbx.setText(criteria.getName());

			criteriaChkbx.setOnCheckedChangeListener(new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton p1, boolean p2)
					{
						if (p2)
						{
							index += criteria.getWeight();
						}
						else
						{
							index -= criteria.getWeight();
						}

						diagnosisPb.setProgress(index);

						updateProbability();
					}
				});

			return view;
		}


	}


}
