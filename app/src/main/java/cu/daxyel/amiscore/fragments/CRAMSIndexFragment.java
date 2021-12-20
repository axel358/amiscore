package cu.daxyel.amiscore.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.ScanQRActivity;
import cu.daxyel.amiscore.Utils;
import cu.daxyel.amiscore.db.DbDiagnostics;
import cu.daxyel.amiscore.models.Criteria;
import cu.daxyel.amiscore.models.Section;

public class CRAMSIndexFragment extends Fragment {
    private Context context;
    private int total;
    private int critValueMed, critValueHigh;
    private ProgressBar diagnosisPb;
    private String probabilityInfo;
    private CriteriaAdapter criteriaAdapter;
    private Button indexButton;
    private TextView diagnosisTv;
    private ArrayList<Section> sectionList = new ArrayList<>();
    private RecyclerView mainRecycler;
    private SectionAdapter sectionAdapter;


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
        View view = inflater.inflate(R.layout.fragment_crams_index, container, false);
        diagnosisTv = view.findViewById(R.id.diagnosisCRAMS_tv);
        diagnosisPb = view.findViewById(R.id.diagnosisCRAMS_pb);
        indexButton = getActivity().findViewById(R.id.index_select_btn);
        mainRecycler = view.findViewById(R.id.diagnosisCRAMS_rv);
        mainRecycler.setLayoutManager(new LinearLayoutManager(context));


        loadIndex();

        return view;
    }

    public void loadIndex() {
        total = 12;
        critValueHigh = 8;
        Utils.index=0;

        String sectionOne = getString(R.string.crams_section_one);
        String sectionTwo = getString(R.string.crams_section_two);
        String sectionThree = getString(R.string.crams_section_three);
        String sectionFour = getString(R.string.crams_section_four);

        ArrayList<Criteria> sectionOneItems = new ArrayList<Criteria>();
        sectionOneItems.add(new Criteria(2, getString(R.string.crams_section_one_crit1), false));
        sectionOneItems.add(new Criteria(1, getString(R.string.crams_section_one_crit2), false));
        sectionOneItems.add(new Criteria(0, getString(R.string.crams_section_one_crit3), false));


        ArrayList<Criteria> sectionTwoItems = new ArrayList<Criteria>();
        sectionTwoItems.add(new Criteria(2, getString(R.string.crams_section_two_crit1), false));
        sectionTwoItems.add(new Criteria(1, getString(R.string.crams_section_two_crit2), false));
        sectionTwoItems.add(new Criteria(0, getString(R.string.crams_section_two_crit3), false));

        ArrayList<Criteria> sectionThreeItems = new ArrayList<Criteria>();
        sectionThreeItems.add(new Criteria(2, getString(R.string.crams_section_three_crit1), false));
        sectionThreeItems.add(new Criteria(1, getString(R.string.crams_section_three_crit2), false));
        sectionThreeItems.add(new Criteria(0, getString(R.string.crams_section_three_crit3), false));

        ArrayList<Criteria> sectionFourItems = new ArrayList<Criteria>();
        sectionFourItems.add(new Criteria(2, getString(R.string.crams_section_four_crit1), false));
        sectionFourItems.add(new Criteria(1, getString(R.string.crams_section_four_crit2), false));
        sectionFourItems.add(new Criteria(0, getString(R.string.crams_section_four_crit3), false));


        sectionList.add(new Section(sectionOne, sectionOneItems));
        sectionList.add(new Section(sectionTwo, sectionTwoItems));
        sectionList.add(new Section(sectionThree, sectionThreeItems));
        sectionList.add(new Section(sectionFour, sectionFourItems));
        sectionAdapter = new SectionAdapter(sectionList);
        mainRecycler.setAdapter(sectionAdapter);

        diagnosisPb.setMax(total);

       updateProbability();


    }
    public void updateProbability() {
        if (Utils.index > critValueHigh) {
            diagnosisTv.setText(getString(R.string.crams_crit_not_serious) + " " + Utils.index + "/" + total);
            probabilityInfo = getString(R.string.crams_crit_not_serious).toLowerCase();
        }  else if(Utils.index<=critValueHigh) {
            diagnosisTv.setText(getString(R.string.crams_crit_serious) + " " + Utils.index + "/" + total);
            probabilityInfo = getString(R.string.crams_crit_serious).toLowerCase();
        }
    }

    class CriteriaAdapter extends RecyclerView.Adapter<CRAMSIndexFragment.CriteriaAdapter.CriteriaViewHolder> {
        private ArrayList<Criteria> criteriaArrayList;

        public CriteriaAdapter(ArrayList<Criteria> criteriaArrayList) {
            this.criteriaArrayList = criteriaArrayList;
        }

        @NonNull
        @Override
        public CRAMSIndexFragment.CriteriaAdapter.CriteriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view_entry_criteria = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_criteria, null, false);
            return new CRAMSIndexFragment.CriteriaAdapter.CriteriaViewHolder(view_entry_criteria);
        }



        @Override
        public void onBindViewHolder(@NonNull CriteriaAdapter.CriteriaViewHolder holder, int position) {
            holder.criteriaChkbx.setText(criteriaArrayList.get(position).getName());
            holder.criteriaChkbx.setChecked(criteriaArrayList.get(position).getSelected());
        }

        public ArrayList<Criteria> getCriteriaArrayList() {
            return criteriaArrayList;
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
                criteriaChkbx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!criteriaArrayList.get(getAdapterPosition()).getSelected()) {
                            Utils.index += criteriaArrayList.get(getAdapterPosition()).getWeight();
                            criteriaArrayList.get(getAdapterPosition()).setSelected(true);
                            diagnosisPb.setProgress(Utils.index);
                            updateProbability();
                        } else {
                            Utils.index -= criteriaArrayList.get(getAdapterPosition()).getWeight();
                            criteriaArrayList.get(getAdapterPosition()).setSelected(false);
                            diagnosisPb.setProgress(Utils.index);
                            updateProbability();

                        }
                    }
                });
            }

        }
    }
    class SectionAdapter extends RecyclerView.Adapter<CRAMSIndexFragment.SectionAdapter.ViexHolder> {
        private ArrayList<Section> sectionArrayList;
        private CriteriaAdapter criteriaAdapte;



        public SectionAdapter(ArrayList<Section> sectionArrayList) {
            this.sectionArrayList = sectionArrayList;
        }

        public CriteriaAdapter getCriteriaAdapte() {
            return criteriaAdapte;
        }

        @NonNull
        @Override
        public CRAMSIndexFragment.SectionAdapter.ViexHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sectionrow, null, false);
            return new CRAMSIndexFragment.SectionAdapter.ViexHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SectionAdapter.ViexHolder holder, int position) {
            Section section = sectionArrayList.get(position);
            String sectionname = section.getSection_name();
            ArrayList<Criteria> items = section.getSection_Items();
            holder.sectionname.setText(sectionname);
            criteriaAdapte = new CriteriaAdapter(items);

            holder.child.setLayoutManager(new LinearLayoutManager(context));
            holder.child.setAdapter(criteriaAdapte);

        }


        @Override
        public int getItemCount() {
            return sectionArrayList.size();
        }

        class ViexHolder extends RecyclerView.ViewHolder {
            TextView sectionname;
            RecyclerView child;

            public ViexHolder(@NonNull View itemView) {
                super(itemView);
                sectionname = itemView.findViewById(R.id.itemsection);
                child = itemView.findViewById(R.id.sectionchildrv);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ArrayList<Criteria> allCriterias = new ArrayList<>();
        switch (item.getItemId()) {
            case R.id.menu_save:
                for (int i = 0; i < sectionList.size(); i++) {
                    allCriterias.addAll(sectionList.get(i).getSection_Items());
                }
                criteriaAdapter=new CriteriaAdapter(allCriterias);
                if (Utils.isAnyChekBoxIsCheked(criteriaAdapter.getCriteriaArrayList())) {
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
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

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
                        for (int i = 0; i < sectionAdapter.sectionArrayList.size(); i++) {
                            for (int j = 0; j <sectionAdapter.sectionArrayList.get(i).getSection_Items().size() ; j++) {
                               sectionAdapter.sectionArrayList.get(i).getSection_Items().get(j).setSelected(false);
                                sectionAdapter.criteriaAdapte.notifyItemChanged(j);
                            }
                            sectionAdapter.notifyItemChanged(i);
                            Utils.index = 0;
                            diagnosisPb.setProgress(Utils.index);
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

}
