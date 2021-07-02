package cu.daxyel.amiscore.fragments;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import cu.daxyel.amiscore.Criteria;
import cu.daxyel.amiscore.R;
import java.util.ArrayList;

public class DiagnoseFragment extends Fragment
{
    private ListView criteriasLv;
    private TextView diagnosisTv;
    private int index;
    private int total;
    private int critValueMed,critValueHigh;
	private ProgressBar diagnosisPb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_diagnose, container, false);
        criteriasLv =  view.findViewById(R.id.criteria_lv);
        diagnosisTv =  view.findViewById(R.id.diagnosis_tv);
        diagnosisPb =  view.findViewById(R.id.diagnosis_pb);

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

        criteriasLv.setAdapter(new CriteriaAdapter(getActivity(), criterias));

		updateProbability();
        return view;
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
