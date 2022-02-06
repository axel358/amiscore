package cu.daxyel.amiscore.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.adapters.IndexAdapter;
import cu.daxyel.amiscore.models.Category;
import cu.daxyel.amiscore.models.Index;

import java.util.ArrayList;

import android.widget.Button;
import android.widget.TextView;

public class DiagnoseFragment extends Fragment {

    private Context context;
    private Button loadIndexButton;
    private TextView showIndexLoaded;
    private FragmentManager fm;

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
        View view = inflater.inflate(R.layout.fragment_diagnose, container, false);
        fm = getActivity().getSupportFragmentManager();

        loadIndexButton = view.findViewById(R.id.load_index_btn);
        showIndexLoaded=view.findViewById(R.id.index_selected);
        loadIndexButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                showIndexPickerDialog();
            }
        });
        loadIndex("AMIScore");

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_diagnose, menu);
    }


    public void loadIndex(String name) {
        showIndexLoaded.setText(name);
        if (name == "AMIScore"||name==getString(R.string.escala_alvarado)||name==getString(R.string.Mannheim_index)) {
            fm.beginTransaction().replace(R.id.containerIndexes, new DiagnoseSimpleFragment()).commit();
        } else if (name == getString(R.string.CRAMS_index)||name==getString(R.string.Balthazar_index)|| name=="Pediatric Trauma Score"||name==getString(R.string.criterios_de_ramson_no_biliares_index)||name==getString(R.string.criterios_de_ramson_biliares_index)) {
            fm.beginTransaction().replace(R.id.containerIndexes, new DiagnoseSectionedIndexFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
    }

    public void showIndexPickerDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog);
        View view = getLayoutInflater().inflate(R.layout.dialog_indexes, null);
        ExpandableListView indexLv = view.findViewById(R.id.indexes_lv);
        ArrayList<Category> categories = new ArrayList<Category>();

        ArrayList<Index> traumaIndexes = new ArrayList<Index>();
        traumaIndexes.add(new Index("Pediatric Trauma Score"));
        traumaIndexes.add(new Index("Revised Trauma Score"));
        traumaIndexes.add(new Index(getString(R.string.CRAMS_index)));
        categories.add(new Category(getString(R.string.trauma_category), traumaIndexes));

        categories.add(new Category(getString(R.string.abdomen_agudo_category), new ArrayList<Index>()));

        ArrayList<Index> pancreatitisIndexes = new ArrayList<Index>();
        pancreatitisIndexes.add(new Index(getString(R.string.CTSI_index)));
        pancreatitisIndexes.add(new Index(getString(R.string.criterios_de_ramson_no_biliares_index)));
        pancreatitisIndexes.add(new Index(getString(R.string.criterios_de_ramson_biliares_index)));
        pancreatitisIndexes.add(new Index(getString(R.string.Balthazar_index)));
        categories.add(new Category(getString(R.string.pancreatitis_category), pancreatitisIndexes));

        ArrayList<Index> imaIndexes = new ArrayList<Index>();
        imaIndexes.add(new Index("AMIScore"));
        categories.add(new Category(getString(R.string.isquemia_mesenterica_aguda_category), imaIndexes));

        ArrayList<Index> appIndexes = new ArrayList<Index>();
        appIndexes.add(new Index(getString(R.string.escala_alvarado)));
        categories.add(new Category(getString(R.string.apendicitis_category),appIndexes));

        ArrayList<Index> peritonitisIndexes = new ArrayList<Index>();
        peritonitisIndexes.add(new Index(getString(R.string.Mannheim_index)));
        peritonitisIndexes.add(new Index("IPR"));
        categories.add(new Category(getString(R.string.peritonitis_category), peritonitisIndexes));

        final IndexAdapter indexAdapter = new IndexAdapter(categories, context);
        indexLv.setAdapter(indexAdapter);

        builder.setView(view);

        final AlertDialog dialog = builder.create();

        indexLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView p1, View p2, int p3, int p4, long p5) {
                Index ind = (Index) indexAdapter.getChild(p3, p4);
                loadIndex(ind.getName());
                dialog.dismiss();
                return true;
            }
        });
        dialog.show();
    }
}
