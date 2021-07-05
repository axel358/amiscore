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

public class ManageDiagnosticsFragment extends Fragment
{
    private ListView diagnosisLv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_manage_diagnostics, container, false);
        diagnosisLv = view.findViewById(R.id.diagnosis_lv);
        
        //Get real data from the db
        DbDiagnostics dbDiagnostics=new DbDiagnostics(getActivity());
        ArrayList<Diagnosis> diagnosis =dbDiagnostics.listAllDiagnostics();

        //TODO: Move this to an AsyncTask
        diagnosisLv.setAdapter(new DiagnosisAdapter(getActivity(),diagnosis));
        
        return view;
    }

    class DiagnosisAdapter extends ArrayAdapter<Diagnosis>
    {
        private Context context;
        public DiagnosisAdapter(Context context, ArrayList<Diagnosis> diagnosis)
        {
            super(context, R.layout.diagnosis_entry, diagnosis);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.diagnosis_entry, null);

            TextView nameTv = view.findViewById(R.id.diagnosis_name_tv);
            TextView patient_id = view.findViewById(R.id.diagnosis_patient_id);
            TextView disease = view.findViewById(R.id.diagnosis_disease);
            TextView diagnosis_date = view.findViewById(R.id.diagnosis_date);

            Diagnosis diagnosis = getItem(position);

            //Map data from the diagnosis object to the view
            nameTv.setText(diagnosis.getFull_name());
            patient_id.setText(diagnosis.getCi());
            disease.setText(diagnosis.getDisease());
            diagnosis_date.setText(diagnosis.getConsult_date());

            return view;
        }


    }
}
