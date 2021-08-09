package cu.daxyel.amiscore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosticsAdapter.ViewHolder holder, int position) {
        String txt = filteredDiagnosis.get(position).getDisease() + " , " + filteredDiagnosis.get(position).getProbabilityInfo();
        holder.nameTv.setText(filteredDiagnosis.get(position).getName());
        holder.patient_id.setText(filteredDiagnosis.get(position).getCi());
        holder.diagnosis_date.setText(filteredDiagnosis.get(position).getDate());
        holder.disease.setText(txt);

        if (isSelected(position)) {
            Anim anim=new Anim(Utils.dpToPx(context, 40), holder.checkBox);
            anim.setDuration(300);
            holder.checkBox.setAnimation(anim);
            holder.checkBox.setChecked(true);
            holder.checkBox.setClickable(false);
        } else {
            Anim anim=new Anim(0, holder.checkBox);
            anim.setDuration(300);
            holder.checkBox.setAnimation(anim);
            holder.checkBox.setChecked(false);
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

        public ViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);

            this.listener = listener;
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

    class Anim extends Animation {
        private int width,startWidth;
        private View view;

        public Anim(int width, View view) {
            this.width = width;
            this.view = view;
            this.startWidth = view.getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newWidth=startWidth + (int) ((width - startWidth) * interpolatedTime);
            view.getLayoutParams().width = newWidth;
            view.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}


