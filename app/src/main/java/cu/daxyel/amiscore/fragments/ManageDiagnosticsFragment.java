package cu.daxyel.amiscore.fragments;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.db.DbDiagnostics;
import cu.daxyel.amiscore.models.Diagnosis;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.ArrayList;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.appcompat.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Adapter;

public class ManageDiagnosticsFragment extends Fragment
{
    private ListView diagnosisLv;
    private DbDiagnostics dbDiagnostics;
    private TextView loadingTv;
    private ProgressBar loadingPb;
    private Context context;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_manage_diagnostics, container, false);
        diagnosisLv = view.findViewById(R.id.diagnosis_lv);
        loadingTv = view.findViewById(R.id.diagnosis_loading_tv);
        loadingPb = view.findViewById(R.id.diagnosis_loading_pb);
        Spinner indexSpinner=view.findViewById(R.id.indexes_spnr);

        dbDiagnostics = new DbDiagnostics(getActivity());

        //Create dummy data
        String[] inexes=new String[]{"All","Index 1", "Index 2"};

        indexSpinner.setAdapter(new ArrayAdapter<String>(context, R.layout.entry_index_spnr, inexes));

        indexSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
                {
                    String index=p1.getItemAtPosition(p3).toString();
                    if (index.equalsIgnoreCase("all")) 
                        new LoadDiagnosisTask().execute();
                    else new LoadDiagnosisTask().execute(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> p1)
                {

                }
            });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_manage_diagnosis, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_search_diagnosis).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

                @Override
                public boolean onQueryTextSubmit(String p1)
                {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String p1)
                {
                    if (p1.length() > 1)
                        ((DiagnosisAdapter) diagnosisLv.getAdapter()).getFilter().filter(p1);
                    else
                        ((DiagnosisAdapter) diagnosisLv.getAdapter()).getFilter().filter("");
                    return true;
                }
            });
    }

    class DiagnosisAdapter extends ArrayAdapter<Diagnosis>
    {
        private Context context;

        public DiagnosisAdapter(Context context, ArrayList<Diagnosis> diagnosis)
        {
            super(context, R.layout.entry_diagnosis, diagnosis);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
                convertView = LayoutInflater.from(context).inflate(R.layout.entry_diagnosis, null);

            TextView nameTv = convertView.findViewById(R.id.diagnosis_name_tv);
            TextView patient_id = convertView.findViewById(R.id.diagnosis_patient_id);
            TextView disease = convertView.findViewById(R.id.diagnosis_disease);
            TextView diagnosis_date = convertView.findViewById(R.id.diagnosis_date);

            Diagnosis diagnosis = getItem(position);

            //Map data from the diagnosis object to the view
            nameTv.setText(diagnosis.getName());
            patient_id.setText(diagnosis.getCi());
            disease.setText(diagnosis.getDisease());
            diagnosis_date.setText(diagnosis.getDate());

            return convertView;
        }


    }

    class LoadDiagnosisTask extends AsyncTask<String, Void, ArrayList<Diagnosis>>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            loadingTv.setVisibility(View.VISIBLE);
            loadingPb.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Diagnosis> doInBackground(String[] p1)
        {
            return p1.length == 0  ? dbDiagnostics.listAllDiagnostics(): dbDiagnostics.listDiagnosticsByDiseases(p1[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Diagnosis> result)
        {
            super.onPostExecute(result);
            loadingTv.setVisibility(View.GONE);
            loadingPb.setVisibility(View.GONE);
            diagnosisLv.setAdapter(new DiagnosisAdapter(context, result));
        }

    }
}
