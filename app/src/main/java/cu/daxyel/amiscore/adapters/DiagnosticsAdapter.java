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


public class DiagnosticsAdapter extends RecyclerView.Adapter<DiagnosticsAdapter.DiagnosticsViewHolder>implements Filterable {
    private ArrayList<Diagnosis> diagnosis;
    private DbDiagnostics dbDiagnostics;
    private CustomFilter mFilter;

    public DiagnosticsAdapter(ArrayList<Diagnosis> diagnosisArrayList, Context context) {

        this.diagnosis = diagnosisArrayList;
        this.mFilter = new CustomFilter();
        this.dbDiagnostics = new DbDiagnostics(context);
    }

    public ArrayList<Diagnosis> getDiagnosis() {
        return diagnosis;
    }

    @NonNull
    @Override
    public DiagnosticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_diagnosis, null, false);
        return new DiagnosticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosticsViewHolder holder, int position) {
        String txt=diagnosis.get(position).getDisease() + " , " + diagnosis.get(position).getProbabilityInfo();
        holder.nameTv.setText(diagnosis.get(position).getName());
        holder.patient_id.setText(diagnosis.get(position).getCi());
        holder.diagnosis_date.setText(diagnosis.get(position).getDate());
        holder.disease.setText(txt);

    }

    @Override
    public int getItemCount() {
        return diagnosis.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            ArrayList<Diagnosis> resultsList=new ArrayList<Diagnosis>();

            if (constraint.length() == 0) {
                results.values = diagnosis;
                results.count = diagnosis.size();    
            } else {
                for (final Diagnosis ddiagnosis : diagnosis) {
                    if (ddiagnosis.getName().toLowerCase().contains(constraint.toString().toLowerCase().trim())) {
                        resultsList.add(ddiagnosis);
                    }
                }
                results.values = resultsList;
                results.count = resultsList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    }


    public class DiagnosticsViewHolder extends RecyclerView.ViewHolder {
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
