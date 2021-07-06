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

public class ManageDiagnosticsFragment extends Fragment
{
    private ListView diagnosisLv;
    private DbDiagnostics dbDiagnostics;
    private TextView loadingTv;
    private ProgressBar loadingPb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_manage_diagnostics, container, false);
        diagnosisLv = view.findViewById(R.id.diagnosis_lv);
        loadingTv = view.findViewById(R.id.diagnosis_loading_tv);
        loadingPb = view.findViewById(R.id.diagnosis_loading_pb);

        dbDiagnostics = new DbDiagnostics(getActivity());

        new LoadDiagnosisTask().execute();

        return view;
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
    class LoadDiagnosisTask extends AsyncTask<Void,Void,ArrayList<Diagnosis>>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            loadingTv.setVisibility(View.VISIBLE);
            loadingPb.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Diagnosis> doInBackground(Void[] p1)
        {
            return dbDiagnostics.listAllDiagnostics(); 
        }

        @Override
        protected void onPostExecute(ArrayList<Diagnosis> result)
        {
            super.onPostExecute(result);
            loadingTv.setVisibility(View.GONE);
            loadingPb.setVisibility(View.GONE);
            diagnosisLv.setAdapter(new DiagnosisAdapter(getActivity(), result));
        }

    }
}
