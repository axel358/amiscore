package cu.daxyel.amiscore.fragments;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import cu.daxyel.amiscore.R;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import cu.daxyel.amiscore.Diagnosis;
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
        ArrayList<Diagnosis> diagnosis=new ArrayList<Diagnosis>();
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

            Diagnosis diagnosis = getItem(position);

            //Map data from the diagnosis object to the view

            return view;
        }


    }
}
