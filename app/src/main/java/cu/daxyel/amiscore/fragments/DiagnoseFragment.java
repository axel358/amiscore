package cu.daxyel.amiscore.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.ScanQRActivity;
import cu.daxyel.amiscore.Utils;
import cu.daxyel.amiscore.adapters.IndexAdapter;
import cu.daxyel.amiscore.db.DbDiagnostics;
import cu.daxyel.amiscore.models.Category;
import cu.daxyel.amiscore.models.Criteria;
import cu.daxyel.amiscore.models.Index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.widget.Button;

public class DiagnoseFragment extends Fragment {

    private Context context;
    private Button indexButton;
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
        fm = getParentFragmentManager();

        indexButton = view.findViewById(R.id.index_select_btn);
        indexButton.setOnClickListener(new OnClickListener() {

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
        indexButton.setText(name);
        if (name == "AMIScore") {
            fm.beginTransaction().replace(R.id.containerIndexes, new DiagnoseSimpleFragment()).commit();
        } else if (name == "Escala CRAMS") {
            fm.beginTransaction().replace(R.id.containerIndexes, new CRAMSIndexFragment()).commit();

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
        traumaIndexes.add(new Index("Escala CRAMS"));
        categories.add(new Category("Trauma", traumaIndexes));

        categories.add(new Category("Abdomen agudo :", new ArrayList<Index>()));

        ArrayList<Index> pancreatitisIndexes = new ArrayList<Index>();
        pancreatitisIndexes.add(new Index("Indice CTSI"));
        pancreatitisIndexes.add(new Index("Criterios de Ramson no biliares"));
        pancreatitisIndexes.add(new Index("Criterios de Ramson biliares"));
        categories.add(new Category("Pancreatitis", pancreatitisIndexes));

        ArrayList<Index> imaIndexes = new ArrayList<Index>();
        imaIndexes.add(new Index("AMIScore"));
        categories.add(new Category("Isquemia mesentérica aguda", imaIndexes));

        categories.add(new Category("Apendicitis", new ArrayList<Index>()));

        ArrayList<Index> peritonitisIndexes = new ArrayList<Index>();
        peritonitisIndexes.add(new Index("Indice de Mannheim"));
        peritonitisIndexes.add(new Index("IPR"));
        categories.add(new Category("Peritonitis", peritonitisIndexes));

        final IndexAdapter indexAdapter = new IndexAdapter(categories, context);
        indexLv.setAdapter(indexAdapter);

        builder.setView(view);

        final AlertDialog dialog = builder.create();

        indexLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView p1, View p2, int p3, int p4, long p5) {
                Index ind = (Index) indexAdapter.getChild(p3, p4);
                System.out.println(ind.getName());
                loadIndex(ind.getName());
                dialog.dismiss();
                return true;
            }
        });
        dialog.show();
    }
}
