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
    private RecyclerView criteriasRv;
    private TextView diagnosisTv,infoTv;
    private int index;
    private int total;
    private int critValueMed, critValueHigh;
    private ProgressBar diagnosisPb;
    private Context context;
    private Button indexButton;
    private CriteriaAdapter criteriaAdapter;
    private String probabilityInfo;

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
        criteriasRv = view.findViewById(R.id.criteria_rv);
        criteriasRv.setLayoutManager(new LinearLayoutManager(context));
        diagnosisTv = view.findViewById(R.id.diagnosis_tv);
        diagnosisPb = view.findViewById(R.id.diagnosis_pb);
        infoTv = view.findViewById(R.id.info_tv);

        indexButton = view.findViewById(R.id.index_select_btn);

        indexButton.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {
                    showIndexPickerDialog();
                }
            });
        loadIndex("AMIScore");

        return view;
    }


    public void loadIndex(String name) {
        indexButton.setText(name);
        ArrayList<Criteria> criterias = new ArrayList<Criteria>();
        String info="";
        switch (name) {
            case "AMIScore":
                total = 121;
                critValueMed = 58;
                critValueHigh = 81;

                if (Locale.getDefault().toString().startsWith("es")) {
                    criterias.add(new Criteria(21, "Ateromatosis de la aorta", false));
                    criterias.add(new Criteria(25, "Patrón gaseoso utrasonográfico aumentado", false));
                    criterias.add(new Criteria(36, "Fibrilacion auricular", false));
                    criterias.add(new Criteria(39, "Lactato mayor que 2.1", false));
                    info = "Dolor abdominal inespecífico sin diagnóstico constatado \n+";
                } else {
                    criterias.add(new Criteria(21, "Aorta ateromatosis", false));
                    criterias.add(new Criteria(25, "Increased ultrasonografic gaseous pattern", false));
                    criterias.add(new Criteria(36, "Atrial fibrillation", false));
                    criterias.add(new Criteria(39, "Lactate higher than 2.1", false));
                    info = "Non specific abdominal pain without clear diagnosis \n+";
                }
                break;
            case "Index 2":
                total = 122;
                critValueMed = 59;
                critValueHigh = 82;

                criterias.add(new Criteria(21, "Variable 1", false));
                criterias.add(new Criteria(25, "Variable 2", false));
                criterias.add(new Criteria(37, "Variable 3", false));
                criterias.add(new Criteria(39, "Variable 4", false));
                break;

        }
        diagnosisPb.setMax(total);

        if (info.isEmpty()) {
            infoTv.setVisibility(View.GONE);
        } else {
            infoTv.setVisibility(View.VISIBLE);
            infoTv.setText(info);
        }

        criteriaAdapter = new CriteriaAdapter(criterias);
        criteriasRv.setAdapter(criteriaAdapter);

        updateProbability();

    }

    public void updateProbability() {
        if (index > critValueHigh) {
            diagnosisTv.setText(getString(R.string.very_high_probability) + " " + index + "/" + total);
            probabilityInfo = getString(R.string.very_high_probability).toLowerCase();
        } else if (index > critValueMed) {
            diagnosisTv.setText(getString(R.string.high_probability) + " " + index + "/" + total);
            probabilityInfo = getString(R.string.high_probability).toLowerCase();
        } else {
            diagnosisTv.setText(getString(R.string.low_probability) + " " + index + "/" + total);
            probabilityInfo = getString(R.string.low_probability).toLowerCase();
        }
    }

    class CriteriaAdapter extends RecyclerView.Adapter<CriteriaAdapter.CriteriaViewHolder> {
        private ArrayList<Criteria> criteriaArrayList;

        public CriteriaAdapter(ArrayList<Criteria> criteriaArrayList) {
            this.criteriaArrayList = criteriaArrayList;
        }

        @NonNull
        @Override
        public CriteriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view_entry_criteria = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_criteria, null, false);
            return new CriteriaViewHolder(view_entry_criteria);
        }

        @Override
        public void onBindViewHolder(@NonNull CriteriaViewHolder holder, int position) {
            holder.criteriaChkbx.setText(criteriaArrayList.get(position).getName());
            holder.criteriaChkbx.setChecked(criteriaArrayList.get(position).getSelected());
        }

        public ArrayList<Criteria> getCriteriaArrayList() {
            return criteriaArrayList;
        }

        public boolean isAnyChekBoxIsCheked() {
            for (Criteria criteria : criteriaArrayList) {
                if (criteria.getSelected()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int getItemCount() {
            return criteriaArrayList.size();
        }

        public class CriteriaViewHolder extends RecyclerView.ViewHolder {
            CheckBox criteriaChkbx;

            public CriteriaViewHolder(@NonNull View itemView) {
                super(itemView);
                criteriaChkbx = itemView.findViewById(R.id.criteria_chkbx);
                criteriaChkbx.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!criteriaArrayList.get(getAdapterPosition()).getSelected()) {
                                index += criteriaArrayList.get(getAdapterPosition()).getWeight();
                                criteriaArrayList.get(getAdapterPosition()).setSelected(true);
                                diagnosisPb.setProgress(index);
                                updateProbability();
                            } else {
                                index -= criteriaArrayList.get(getAdapterPosition()).getWeight();
                                criteriaArrayList.get(getAdapterPosition()).setSelected(false);
                                diagnosisPb.setProgress(index);
                                updateProbability();

                            }
                        }
                    });
            }

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_diagnose, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (criteriaAdapter.isAnyChekBoxIsCheked()) {
                    showSaveDialog(null);
                } else {
                    Toast.makeText(context, getString(R.string.toast_criteria), Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSaveDialog(String[] info) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm aaa", Locale.getDefault());
        Date date = new Date();
        final String consult_date = dateFormat.format(date);
        final DbDiagnostics dbDiagnostics = new DbDiagnostics(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog);
        builder.setTitle(getString(R.string.dialog_save_diagnosis_TITLE));
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_save_diagnosis, null);
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.dialog_save_diagnosis_SAVE), null);
        builder.setNegativeButton(getString(R.string.dialog_save_diagnosis_CANCEL), null);
        builder.setNeutralButton(getString(R.string.dialog_save_diagnosis_QR), new DialogInterface.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface p1, int p2) {
                    if (Build.VERSION.SDK_INT < 22 || context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        startActivityForResult(new Intent(context, ScanQRActivity.class), Utils.SCAN_REQUEST_CODE);
                    } else
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 58);

                }
            });

        final TextInputEditText nameEt = view.findViewById(R.id.name_et);
        final TextInputEditText idEt = view.findViewById(R.id.id_et);
        final TextInputEditText observationsEt = view.findViewById(R.id.observations_et);


        if (info != null) {
            nameEt.setText(info[0]);
            idEt.setText(info[1]);
        }

        final AlertDialog dialog = builder.create();

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View p1) {
                    String name = nameEt.getText().toString();
                    String id = idEt.getText().toString();
                    String observations = observationsEt.getText().toString();
                    if (name.isEmpty()) {
                        nameEt.setError(getString(R.string.dialog_save_diagnosis_input_name_error));
                    } else {
                        if (id.length() < 11) {
                            idEt.setError(getString(R.string.dialog_save_diagnosis_input_ID_error));
                        } else {
                            long rowId = dbDiagnostics.addDiagnostic(name, id, indexButton.getText().toString(), probabilityInfo, consult_date, observations);
                            for (int i = 0; i < criteriaAdapter.getCriteriaArrayList().size(); i++) {
                                criteriaAdapter.getCriteriaArrayList().get(i).setSelected(false);
                                criteriaAdapter.notifyItemChanged(i);
                                index = 0;
                                diagnosisPb.setProgress(index);
                                updateProbability();
                            }

                            if (rowId > 0) {
                                Toast.makeText(context, getString(R.string.toast_save_diagnosis_success), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, getString(R.string.toast_save_diagnosis_failed), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();

                        }
                    }

                }
            });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Utils.SCAN_REQUEST_CODE && data != null) {
            String result = data.getStringExtra("result");
            showSaveDialog(Utils.parseScanResult(result));
        } else if (requestCode == 58 && resultCode == Activity.RESULT_OK) {
            startActivityForResult(new Intent(context, ScanQRActivity.class), Utils.SCAN_REQUEST_CODE);
        }
    }
    public void showIndexPickerDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.Dialog);
        View view = getLayoutInflater().inflate(R.layout.dialog_indexes, null);
        ExpandableListView indexLv=view.findViewById(R.id.indexes_lv);
        ArrayList<Category> categories=new ArrayList<Category>();

        ArrayList<Index> traumaIndexes=new ArrayList<Index>();
        traumaIndexes.add(new Index("Pediatric Trauma Score"));
        traumaIndexes.add(new Index("Revised Trauma Score"));
        traumaIndexes.add(new Index("Escala CRAMS"));
        categories.add(new Category("Trauma", traumaIndexes));

        categories.add(new Category("Abdomen agudo :", new ArrayList<Index>()));

        ArrayList<Index> pancreatitisIndexes=new ArrayList<Index>();
        pancreatitisIndexes.add(new Index("Indice CTSI"));
        pancreatitisIndexes.add(new Index("Criterios de Ramson no biliares"));
        pancreatitisIndexes.add(new Index("Criterios de Ramson biliares"));        
        categories.add(new Category("Pancreatitis", pancreatitisIndexes));

        ArrayList<Index> imaIndexes=new ArrayList<Index>();
        imaIndexes.add(new Index("AMIScore"));
        categories.add(new Category("Isquemia mesentérica aguda", imaIndexes));

        categories.add(new Category("Apendicitis", new ArrayList<Index>()));

        ArrayList<Index> peritonitisIndexes=new ArrayList<Index>();
        peritonitisIndexes.add(new Index("Indice de Mannheim"));
        peritonitisIndexes.add(new Index("IPR"));
        categories.add(new Category("Peritonitis", peritonitisIndexes));

        final IndexAdapter indexAdapter = new IndexAdapter(categories, context);
        indexLv.setAdapter(indexAdapter);

        builder.setView(view);

        final AlertDialog dialog = builder.create();

        indexLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

                @Override
                public boolean onChildClick(ExpandableListView p1, View p2, int p3, int p4, long p5) {
                    Index ind=(Index) indexAdapter.getChild(p3, p4);
                    loadIndex(ind.getName());
                    dialog.dismiss();
                    return false;
                }
            });
        dialog.show();
    }


}
