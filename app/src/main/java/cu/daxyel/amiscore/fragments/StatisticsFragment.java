package cu.daxyel.amiscore.fragments;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import cu.daxyel.amiscore.R;
import com.github.mikephil.charting.charts.PieChart;
import cu.daxyel.amiscore.db.DbDiagnostics;
import java.util.ArrayList;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import android.graphics.Color;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.components.Legend;

public class StatisticsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_statisticsfragment, container, false);
        PieChart chart = view.findViewById(R.id.stats_chart);
        chart.setCenterTextSize(16.0f);
        chart.setCenterText(getString(R.string.diagnostics_by_index));
        chart.setHoleColor(getResources().getColor(android.R.color.transparent, getActivity().getTheme()));
        chart.setCenterTextColor(getResources().getColor(R.color.text, getActivity().getTheme()));
        
        DbDiagnostics db = new DbDiagnostics(getActivity());
        
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        
        for(Object item : db.getDiseases()){
            entries.add(new PieEntry((float) db.getDiagnosisCountByIndex(item.toString()), item.toString()));
        }
        
        PieDataSet ds = new PieDataSet(entries, "");
        ds.setColors(ColorTemplate.COLORFUL_COLORS);
        ds.setSliceSpace(2f);
        ds.setValueTextColor(Color.WHITE);
        ds.setValueTextSize(14f);
        
        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        legend.setTextSize(14f);
        legend.setTextColor(getResources().getColor(R.color.text, getActivity().getTheme()));
        chart.setDrawEntryLabels(false);
        
        chart.setData(new PieData(ds));
        
        return view;
    }

}
