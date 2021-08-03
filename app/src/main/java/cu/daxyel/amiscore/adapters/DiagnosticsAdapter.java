package cu.daxyel.amiscore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.db.DbDiagnostics;
import cu.daxyel.amiscore.models.Diagnosis;


public class DiagnosticsAdapter extends RecyclerView.Adapter<DiagnosticsAdapter.DiagnosticsViewHolder> {
    private ArrayList<Diagnosis> filteredDiagnosis;
    private ArrayList<Diagnosis> diagnosisArrayList;
    private DbDiagnostics dbDiagnostics;

    public DiagnosticsAdapter(ArrayList<Diagnosis> diagnosisArrayList, Context context) {
        this.diagnosisArrayList = diagnosisArrayList;
        filteredDiagnosis = diagnosisArrayList;
        dbDiagnostics = new DbDiagnostics(context);

    }

    public ArrayList<Diagnosis> getDiagnosis() {
        return filteredDiagnosis;
    }

    public void filter(String query) {
        ArrayList<Diagnosis> results=new ArrayList<Diagnosis>();
        for (Diagnosis diagnosis:diagnosisArrayList) 
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
    public DiagnosticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_diagnosis, null, false);
        return new DiagnosticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosticsViewHolder holder, int position) {
        String txt=filteredDiagnosis.get(position).getDisease() + " , " + filteredDiagnosis.get(position).getProbabilityInfo();
        holder.nameTv.setText(filteredDiagnosis.get(position).getName());
        holder.patient_id.setText(filteredDiagnosis.get(position).getCi());
        holder.diagnosis_date.setText(filteredDiagnosis.get(position).getDate());
        holder.disease.setText(txt);

    }

    @Override
    public int getItemCount() {
        return filteredDiagnosis.size();
    }

    class DiagnosticsViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv,patient_id,disease,diagnosis_date;
        public DiagnosticsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.diagnosis_name_tv);
            patient_id = itemView.findViewById(R.id.diagnosis_patient_id);
            disease = itemView.findViewById(R.id.diagnosis_disease);
            diagnosis_date = itemView.findViewById(R.id.diagnosis_date);
        }
    }

}
