package cu.daxyel.amiscore.fragments;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.adapters.DiagnosticsAdapter;
import cu.daxyel.amiscore.db.DbDiagnostics;
import cu.daxyel.amiscore.models.Diagnosis;;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.ArrayList;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

public class ManageDiagnosticsFragment extends Fragment {
    private RecyclerView diagnosisRv;
    private DbDiagnostics dbDiagnostics;
    private TextView loadingTv;
    private ProgressBar loadingPb;
    private Context context;
    private SearchView searchView;
    private DiagnosticsAdapter diagnosticsAdapter;
    private ArrayList<Diagnosis> results;
    private String index;
    private boolean show_load;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_diagnostics, container, false);
        diagnosisRv = view.findViewById(R.id.diagnosis_rv);
        diagnosisRv.setLayoutManager(new LinearLayoutManager(context));
        loadingTv = view.findViewById(R.id.diagnosis_loading_tv);
        loadingPb = view.findViewById(R.id.diagnosis_loading_pb);
        Spinner indexSpinner = view.findViewById(R.id.indexes_spnr);

        dbDiagnostics = new DbDiagnostics(getActivity());

        //Create dummy data
        String[] inexes = new String[]{"All", "Index 1", "Index 2"};

        indexSpinner.setAdapter(new ArrayAdapter<String>(context, R.layout.entry_index_spnr, inexes));
        indexSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
                index = p1.getItemAtPosition(p3).toString();
                show_load = true;
                if (index.equalsIgnoreCase("all")) {
                    new LoadDiagnosisTask().execute();
                } else {
                    new LoadDiagnosisTask().execute(index);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> p1) {
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manage_diagnosis, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_search_diagnosis).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String p1) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String p1) {
                if (p1.length() > 0)
                    diagnosticsAdapter.getFilter().filter(p1);
                else if (p1.length() == 0)
                    diagnosticsAdapter.getFilter().filter("");
                return true;
            }
        });
    }

    class LoadDiagnosisTask extends AsyncTask<String, Void, ArrayList<Diagnosis>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (show_load) {
                loadingTv.setVisibility(View.VISIBLE);
                loadingPb.setVisibility(View.VISIBLE);
            } else {
                loadingTv.setVisibility(View.INVISIBLE);
                loadingPb.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        protected ArrayList<Diagnosis> doInBackground(String[] p1) {
            return p1.length == 0 ? dbDiagnostics.listAllDiagnostics() : dbDiagnostics.listDiagnosticsByDiseases(p1[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Diagnosis> result) {
            super.onPostExecute(result);
            loadingTv.setVisibility(View.GONE);
            loadingPb.setVisibility(View.GONE);
            diagnosticsAdapter = new DiagnosticsAdapter(result,context);
            diagnosisRv.setAdapter(diagnosticsAdapter);
        }
    }
}
