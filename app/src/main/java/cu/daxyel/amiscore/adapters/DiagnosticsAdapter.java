package cu.daxyel.amiscore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.db.DbDiagnostics;
import cu.daxyel.amiscore.models.Diagnosis;
import cu.daxyel.amiscore.Utils;


public class DiagnosticsAdapter extends SelectableAdapter<DiagnosticsAdapter.ViewHolder> {
    private ArrayList<Diagnosis> filteredDiagnosis;
    private ArrayList<Diagnosis> diagnosisArrayList;
    private DbDiagnostics dbDiagnostics;
    private ViewHolder.ClickListener clickListener;
    private Context context;

    public DiagnosticsAdapter(ArrayList<Diagnosis> diagnosisArrayList, Context context, ViewHolder.ClickListener clickListener) {
        this.diagnosisArrayList = diagnosisArrayList;
        this.context = context;
        filteredDiagnosis = diagnosisArrayList;
        dbDiagnostics = new DbDiagnostics(context);
        this.clickListener = clickListener;

    }

    public ArrayList<Diagnosis> getDiagnosis() {
        return filteredDiagnosis;
    }

    public void filter(String query) {
        ArrayList<Diagnosis> results = new ArrayList<Diagnosis>();
        for (Diagnosis diagnosis : diagnosisArrayList)
            if (diagnosis.getName().contains(query)) results.add(diagnosis);

        filteredDiagnosis = results;
        notifyDataSetChanged();

    }

    public void clearFilter() {
        filteredDiagnosis = diagnosisArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_diagnosis, null, false);
        return new ViewHolder(view, clickListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosticsAdapter.ViewHolder holder, int position) {
        holder.nameTv.setText(filteredDiagnosis.get(position).getName());
        holder.patient_id.setText(filteredDiagnosis.get(position).getCi());
        holder.diagnosis_date.setText(filteredDiagnosis.get(position).getDate().split(",")[0]);
        holder.disease.setText(filteredDiagnosis.get(position).getDisease() + ", " + filteredDiagnosis.get(position).getProbabilityInfo());

        if (filteredDiagnosis.get(position).isSelected()) {
            holder.checkBox.setWidth(Utils.dpToPx(context, 40));
            holder.checkBox.setChecked(true);
            holder.checkBox.setClickable(false);
        } else {
            holder.checkBox.setWidth(Utils.dpToPx(context, 0));
        }
    }


    @Override
    public int getItemCount() {
        return filteredDiagnosis.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView nameTv, patient_id, disease, diagnosis_date;
        private ClickListener listener;
        private LinearLayout linearLayout;
        private CheckBox checkBox;
        private Context context;


        public ViewHolder(@NonNull View itemView, ClickListener listener, Context context) {
            super(itemView);
            this.listener = listener;
            this.context = context;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            nameTv = itemView.findViewById(R.id.diagnosis_name_tv);
            patient_id = itemView.findViewById(R.id.diagnosis_patient_id);
            disease = itemView.findViewById(R.id.diagnosis_disease);
            diagnosis_date = itemView.findViewById(R.id.diagnosis_date);
            linearLayout = itemView.findViewById(R.id.linear_entry_diagnosis);
            checkBox = itemView.findViewById(R.id.checkBox_select);
        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }

        public interface ClickListener {
            void onItemClicked(int position);

            boolean onItemLongClicked(int position);
        }
    }
}


