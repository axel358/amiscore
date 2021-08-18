package cu.daxyel.amiscore.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cu.daxyel.amiscore.models.Criteria;
import cu.daxyel.amiscore.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.view.View.OnClickListener;

import com.google.android.material.textfield.TextInputEditText;

import android.content.Intent;
import android.widget.Toast;

import cu.daxyel.amiscore.ScanQRActivity;
import cu.daxyel.amiscore.Utils;
import cu.daxyel.amiscore.db.DbDiagnostics;

import android.widget.Spinner;

public class DiagnoseFragment extends Fragment {
    private RecyclerView criteriasRv;
    private TextView diagnosisTv;
    private int index;
    private int total;
    private int critValueMed, critValueHigh;
    private ProgressBar diagnosisPb;
    private Context context;
    private Spinner indexSpinner;
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

        //Subject to changes depending on the loaded index

        total = 121;
        critValueMed = 58;
        critValueHigh = 81;

        diagnosisPb.setMax(total);

        ArrayList<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(new Criteria(21, "Adenomatosis intensa de la aorta", false));
        criterias.add(new Criteria(25, "Patrón gaseoso aumentado en ultrasonido", false));
        criterias.add(new Criteria(36, "Fibrilacion articular", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));
        criterias.add(new Criteria(39, "Lactato mayor a 2.1", false));

        criteriaAdapter = new CriteriaAdapter(criterias);
        criteriasRv.setAdapter(criteriaAdapter);
        indexSpinner = view.findViewById(R.id.indexes_spnr);

        //Create dummy data
        String[] inexes = new String[]{"Index 1", "Index 2"};

        indexSpinner.setAdapter(new ArrayAdapter<String>(context, R.layout.entry_index_spnr, inexes));

        updateProbability();

        return view;
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
            if (criteriaArrayList.get(position).getSelected()) {
                holder.criteriaChkbx.setChecked(true);
            } else {
                holder.criteriaChkbx.setChecked(false);
            }
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

            @Override
            public void onClick(DialogInterface p1, int p2) {
                startActivityForResult(new Intent(context, ScanQRActivity.class), Utils.SCAN_REQUEST_CODE);
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
                        long rowId = dbDiagnostics.addDiagnostic(name, id, indexSpinner.getSelectedItem().toString(), probabilityInfo, consult_date, observations);
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
        }
    }


}
