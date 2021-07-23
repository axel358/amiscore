package cu.daxyel.amiscore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.models.Diagnosis;


public class DiagnosticsAdapter extends RecyclerView.Adapter<DiagnosticsAdapter.DiagnosticsViewHolder>implements Filterable {
    ArrayList<Diagnosis> diagnosisArrayList;

    private ArrayList<Diagnosis> diagnosisArrayListFilter;
    private CustomFilter mFilter;

    public DiagnosticsAdapter(ArrayList<Diagnosis> diagnosisArrayList) {

        this.diagnosisArrayList=diagnosisArrayList;
        this.diagnosisArrayListFilter=new ArrayList<>();
        this.diagnosisArrayListFilter.addAll(diagnosisArrayList);
        this.mFilter = new CustomFilter(DiagnosticsAdapter.this);
    }
    @NonNull
    @Override
    public DiagnosticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_diagnosis,null,false);
       return new DiagnosticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosticsViewHolder holder, int position) {
        String txt=diagnosisArrayListFilter.get(position).getDisease()+" , "+ diagnosisArrayListFilter.get(position).getProbabilityInfo();
        holder.nameTv.setText(diagnosisArrayListFilter.get(position).getName());
        holder.patient_id.setText(diagnosisArrayListFilter.get(position).getCi());
        holder.diagnosis_date.setText(diagnosisArrayListFilter.get(position).getDate());
        holder.disease.setText(txt);

    }

    @Override
    public int getItemCount() {
        return diagnosisArrayListFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class DiagnosticsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv,patient_id,disease,diagnosis_date;
        public DiagnosticsViewHolder(@NonNull View itemView) {
            super(itemView);
             nameTv = itemView.findViewById(R.id.diagnosis_name_tv);
             patient_id = itemView.findViewById(R.id.diagnosis_patient_id);
             disease = itemView.findViewById(R.id.diagnosis_disease);
             diagnosis_date = itemView.findViewById(R.id.diagnosis_date);
        }
    }

    public class CustomFilter extends Filter {
        private DiagnosticsAdapter diagnosticsAdapter;

        private CustomFilter(DiagnosticsAdapter diagnosticsAdapter) {
            super();
            this.diagnosticsAdapter = diagnosticsAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           diagnosisArrayListFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                diagnosisArrayListFilter.addAll(diagnosisArrayList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Diagnosis diagnosis : diagnosisArrayList) {
                    if (diagnosis.getName().toLowerCase().contains(filterPattern)) {
                        diagnosisArrayListFilter.add(diagnosis);
                    }
                }
            }
            results.values = diagnosisArrayListFilter;
            results.count = diagnosisArrayListFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.diagnosticsAdapter.notifyDataSetChanged();
        }
    }
}
