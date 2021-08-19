package cu.daxyel.amiscore.fragments;

import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ActionMode;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import cu.daxyel.amiscore.MainActivity;
import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.SplashActivity;
import cu.daxyel.amiscore.adapters.DiagnosticsAdapter;
import cu.daxyel.amiscore.db.DbDiagnostics;
import cu.daxyel.amiscore.models.Criteria;
import cu.daxyel.amiscore.models.Diagnosis;

import java.util.ArrayList;

import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Color;

import cu.daxyel.amiscore.Utils;

public class ManageDiagnosticsFragment extends Fragment implements DiagnosticsAdapter.ViewHolder.ClickListener {
    private RecyclerView diagnosisRv;
    private DbDiagnostics dbDiagnostics;
    private TextView loadingTv;
    private ProgressBar loadingPb;
    private Context context;
    private SearchView searchView;
    private DiagnosticsAdapter diagnosticsAdapter;
    private String index;
    private boolean show_load;
    public ActionMode actionMode;
    private boolean editMode = false;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    ItemTouchHelper itemTouchHelper;

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
        View view = inflater.inflate(R.layout.fragment_manage_diagnostics, container, false);
        diagnosisRv = view.findViewById(R.id.diagnosis_rv);
        diagnosisRv.setLayoutManager(new LinearLayoutManager(context));
        loadingTv = view.findViewById(R.id.diagnosis_loading_tv);
        loadingPb = view.findViewById(R.id.diagnosis_loading_pb);
        final Spinner indexSpinner = view.findViewById(R.id.indexes_spnr);

        dbDiagnostics = new DbDiagnostics(getActivity());


        //Create dummy data
        String[] indexes = new String[]{"All", "Index 1", "Index 2"};

        indexSpinner.setAdapter(new ArrayAdapter<String>(context, R.layout.entry_index_spnr, indexes));
        indexSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
                index = p1.getItemAtPosition(p3).toString();
                show_load = true;
                if (index.equalsIgnoreCase("all")) {
                    new LoadDiagnosisTask().execute();
                } else {
                    new LoadDiagnosisTask().execute(index);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> p1) {
            }
        });

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            private int backgroundColor = ContextCompat.getColor(context, R.color.delete_background);
            private int deleteColor = ContextCompat.getColor(context, R.color.color_delete);
            private int iconPadding = Utils.dpToPx(context, 16);
            private Drawable deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
            private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            public final float CIRCLE_ACCELERATION = 3;

            {
                circlePaint.setColor(deleteColor);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();
                final Diagnosis diagnosis = diagnosticsAdapter.getDiagnosis().get(viewHolder.getAdapterPosition());
                diagnosticsAdapter.getDiagnosis().remove(position);
                diagnosticsAdapter.notifyItemRemoved(position);

                Snackbar.make(diagnosisRv, getString(R.string.snackbar_text), Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_action), new OnClickListener() {

                    @Override
                    public void onClick(View p1) {
                        diagnosticsAdapter.getDiagnosis().add(position, diagnosis);
                        diagnosticsAdapter.notifyItemInserted(position);
                    }

                }).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onShown(Snackbar sb) {
                        super.onShown(sb);
                        indexSpinner.setEnabled(false);
                    }

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        //Its gone, remove it from db
                        if (event == Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            indexSpinner.setEnabled(true);
                        }
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE) {
                            indexSpinner.setEnabled(true);
                            dbDiagnostics.deleteDiagnostic(diagnosis.getId());
                        }
                    }
                }).show();
            }

            public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (dX == 0f) {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    return;
                }

                float left = viewHolder.itemView.getLeft();
                float top = viewHolder.itemView.getTop();
                float right = viewHolder.itemView.getRight();
                float bottom = viewHolder.itemView.getBottom();
                float width = right - left;
                float height = bottom - top;
                float saveCount = canvas.save();

                canvas.clipRect(right + dX, top, right, bottom);
                canvas.drawColor(backgroundColor);

                float progress = -dX / width;
                float swipeThreshold = getSwipeThreshold(viewHolder);
                float iconScale = 1f;
                float circleRadius = 0f;

                circleRadius = (progress - swipeThreshold) * width * CIRCLE_ACCELERATION;
                if (deleteIcon != null) {
                    float cx = right - iconPadding - deleteIcon.getIntrinsicWidth() / 2f;
                    float cy = top + height / 2f;
                    float halfIconSize = deleteIcon.getIntrinsicWidth() * iconScale / 2f;

                    deleteIcon.setBounds((int) (cx - halfIconSize), (int) (cy - halfIconSize), (int) (cx + halfIconSize), (int) (cy + halfIconSize));
                    if (circleRadius > 0f) {
                        canvas.drawCircle(cx, cy, circleRadius, circlePaint);
                        deleteIcon.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));

                    } else
                        deleteIcon.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN));

                    deleteIcon.draw(canvas);
                }

                canvas.restoreToCount(Math.round(saveCount));

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(diagnosisRv);

        return view;
    }


    public Bitmap getBitmapFromVector(int resid) {
        Drawable vector = ContextCompat.getDrawable(context, resid);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            vector = DrawableCompat.wrap(vector).mutate();

        Bitmap bitmap = Bitmap.createBitmap(vector.getIntrinsicWidth(), vector.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vector.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vector.draw(canvas);

        return bitmap;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manage_diagnosis, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_search_diagnosis).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String p1) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String p1) {
                if (p1.length() > 1)
                    diagnosticsAdapter.filter(p1);
                else
                    diagnosticsAdapter.clearFilter();
                return true;
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        if (actionMode != null) {
            toggleSelection(position);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog);
            builder.setTitle(getResources().getString(R.string.dialog_edit_detail_Title));
            View views = LayoutInflater.from(context).inflate(R.layout.dialog_edit_details_diagnosis, null);
            builder.setView(views);
            builder.setPositiveButton(getString(R.string.dialog_edit_detail_Button), null);
            builder.setNegativeButton(getString(R.string.dialog_save_diagnosis_CANCEL), null);
            builder.setNeutralButton(getString(R.string.dialog_edit_detail_button_Edit), null);

            final TextInputEditText nameEt = views.findViewById(R.id.name_edit_details);
            final TextInputEditText idEt = views.findViewById(R.id.id_edit_details);
            final TextInputEditText observationsEt = views.findViewById(R.id.observations_edit_details);

            nameEt.setText(diagnosticsAdapter.getDiagnosis().get(position).getName());
            idEt.setText(diagnosticsAdapter.getDiagnosis().get(position).getCi());
            observationsEt.setText(diagnosticsAdapter.getDiagnosis().get(position).getObservations());

            Drawable drawableNameEt = nameEt.getBackground();
            nameEt.setFocusable(false);
            nameEt.setCursorVisible(false);
            nameEt.setBackgroundColor(Color.TRANSPARENT);

            Drawable drawableIdEt = idEt.getBackground();
            idEt.setFocusable(false);
            idEt.setCursorVisible(false);
            idEt.setBackgroundColor(Color.TRANSPARENT);

            Drawable drawableObservationEt = observationsEt.getBackground();
            observationsEt.setFocusable(false);
            observationsEt.setCursorVisible(false);
            observationsEt.setBackgroundColor(Color.TRANSPARENT);

            final AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editMode = true;
                    nameEt.setCursorVisible(true);
                    nameEt.setFocusableInTouchMode(true);
                    nameEt.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(nameEt, InputMethodManager.SHOW_IMPLICIT);
                    nameEt.setBackground(drawableNameEt);

                    idEt.setCursorVisible(true);
                    idEt.setFocusableInTouchMode(true);
                    idEt.setBackground(drawableIdEt);

                    observationsEt.setCursorVisible(true);
                    observationsEt.setFocusableInTouchMode(true);
                    observationsEt.setBackground(drawableObservationEt);

                }
            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View p1) {
                    String name = nameEt.getText().toString();
                    String id = idEt.getText().toString();
                    String observations = observationsEt.getText().toString();
                    String disease = diagnosticsAdapter.getDiagnosis().get(position).getDisease();
                    if (name.isEmpty()) {
                        nameEt.setError(getString(R.string.dialog_save_diagnosis_input_name_error));
                    } else {
                        if (id.length() < 11) {
                            idEt.setError(getString(R.string.dialog_save_diagnosis_input_ID_error));
                        } else {
                            if (editMode) {
                                dbDiagnostics.editDiagnostic(diagnosticsAdapter.getDiagnosis().get(position).getId(), name, id, disease, diagnosticsAdapter.getDiagnosis().get(position).getProbabilityInfo(), diagnosticsAdapter.getDiagnosis().get(position).getDate(), observations);
                                diagnosticsAdapter.getDiagnosis().get(position).setNamme(name);
                                diagnosticsAdapter.getDiagnosis().get(position).setCi(id);
                                diagnosticsAdapter.getDiagnosis().get(position).setDisease(disease);
                                diagnosticsAdapter.getDiagnosis().get(position).setObservations(observations);
                                diagnosticsAdapter.notifyItemChanged(position);
                            }
                            dialog.dismiss();

                        }
                    }

                }
            });
        }

    }


    @Override
    public boolean onItemLongClicked(int position) {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);

        return true;
    }

    private void toggleSelection(int position) {
        diagnosticsAdapter.toggleSelection(position, diagnosticsAdapter.getDiagnosis());
        itemTouchHelper.attachToRecyclerView(null);
        int count = diagnosticsAdapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();

        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String TAG = ActionModeCallback.class.getSimpleName();


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
            itemTouchHelper.attachToRecyclerView(null);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog);
                    final ArrayList<Diagnosis> diagnosisToRemove = new ArrayList<>();   //array con los objetos a remover
                    //Array clonado con las posiciones seleccionada se haca porque cuando se ejecuta
                    // mode.finish pues se resetea el array selecteditems
                    final ArrayList<Integer> selectedItems = new ArrayList<>();
                    selectedItems.addAll(diagnosticsAdapter.getSelectedItems());

                    if (diagnosticsAdapter.getSelectedItemCount() == 1) {
                        builder.setTitle(getString(R.string.dialog_remove_one_diagnosis_TITLE));
                        builder.setMessage(getString(R.string.dialog_remove_one_diagnosis_MESSAGE));
                    } else {
                        builder.setTitle(getString(R.string.dialog_remove_multiple_diagnosis_TITLE));
                        builder.setMessage(getString(R.string.dialog_remove_multiple_diagnosis_MESSAGE));
                    }
                    builder.setPositiveButton(getString(R.string.dialog_remove_diagnosis_DELETE), null);
                    builder.setNegativeButton(getString(R.string.dialog_save_diagnosis_CANCEL), null);

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (Integer i : selectedItems) {
                                diagnosisToRemove.add(diagnosticsAdapter.getDiagnosis().get(i)); //add al array los objetos diagnosis
                                diagnosticsAdapter.getDiagnosis().get(i).setSelected(false);
                            }
                            diagnosticsAdapter.getDiagnosis().removeAll(diagnosisToRemove);  //remover la colleccion de datos entera
                            diagnosticsAdapter.notifyDataSetChanged();

                            for (int i = 0; i < diagnosisToRemove.size(); i++) {
                                dbDiagnostics.deleteDiagnostic(diagnosisToRemove.get(i).getId());
                            }

                            dialog.dismiss();
                            mode.finish();
                        }
                    });
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            diagnosticsAdapter.clearSelection();
            for (int i = 0; i < diagnosticsAdapter.getDiagnosis().size(); i++) {
                diagnosticsAdapter.getDiagnosis().get(i).setSelected(false);
            }
            actionMode = null;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                 itemTouchHelper.attachToRecyclerView(diagnosisRv);
                }
            },100);

        }
    }

    class LoadDiagnosisTask extends AsyncTask<String, Void, ArrayList<Diagnosis>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (show_load) {
                loadingTv.setVisibility(View.VISIBLE);
                loadingPb.setVisibility(View.VISIBLE);
            } else {
                loadingTv.setVisibility(View.INVISIBLE);
                loadingPb.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        protected ArrayList<Diagnosis> doInBackground(String[] p1) {
            return p1.length == 0 ? dbDiagnostics.listAllDiagnostics() : dbDiagnostics.listDiagnosticsByDiseases(p1[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Diagnosis> result) {
            super.onPostExecute(result);
            loadingTv.setVisibility(View.GONE);
            loadingPb.setVisibility(View.GONE);
            diagnosticsAdapter = new DiagnosticsAdapter(result, context, ManageDiagnosticsFragment.this);
            diagnosisRv.setAdapter(diagnosticsAdapter);
        }
    }

}
