package cu.daxyel.amiscore.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.adapters.DiagnosticsAdapter;
import cu.daxyel.amiscore.db.DbDiagnostics;
import cu.daxyel.amiscore.models.Diagnosis;
import java.util.ArrayList;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.Toast;

public class ManageDiagnosticsFragment extends Fragment {
    private RecyclerView diagnosisRv;
    private DbDiagnostics dbDiagnostics;
    private TextView loadingTv;
    private ProgressBar loadingPb;
    private Context context;
    private SearchView searchView;
    private DiagnosticsAdapter diagnosticsAdapter;
    private String index;
    private boolean show_load;

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
        Spinner indexSpinner = view.findViewById(R.id.indexes_spnr);

        dbDiagnostics = new DbDiagnostics(getActivity());

        //Create dummy data
        String[] inexes = new String[]{"All", "Index 1", "Index 2"};

        indexSpinner.setAdapter(new ArrayAdapter<String>(context, R.layout.entry_index_spnr, inexes));
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

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            private Bitmap editIcon=getBitmapFromVector(R.drawable.ic_edit);
            private Bitmap deletIcon=getBitmapFromVector(R.drawable.ic_delete);
            private Paint paint = new Paint();
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
                    diagnosticsAdapter.notifyItemChanged(position);
                } else {
                    final Diagnosis diagnosis = diagnosticsAdapter.getDiagnosis().get(viewHolder.getAdapterPosition());
                    diagnosticsAdapter.getDiagnosis().remove(position);
                    diagnosticsAdapter.notifyItemRemoved(position);

                    Snackbar.make(diagnosisRv, "Item removed", Snackbar.LENGTH_LONG).setAction("Undo", new OnClickListener(){

                            @Override
                            public void onClick(View p1) {
                                diagnosticsAdapter.getDiagnosis().add(position, diagnosis);
                                diagnosticsAdapter.notifyItemInserted(position);
                            }
                        }).setCallback(new Snackbar.Callback(){ 
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                //Its gone, remove it from db
                            }
                        }).show();
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                int top = dpToPx(32) + editIcon.getWidth();
                if (dX < 0) {
                    paint.setColor(context.getResources().getColor(R.color.color_delete));

                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                               (float) itemView.getRight(), (float) itemView.getBottom(), paint);

                    c.drawBitmap(deletIcon,
                                 (float) itemView.getRight() - dpToPx(16) - deletIcon.getWidth(),
                                 (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - deletIcon.getHeight()) / 2,
                                 paint);

                } else if (dX > 0) {


                    if (dX >= top) {
                        dX = top;
                        Toast.makeText(context, "Yahoo", Toast.LENGTH_SHORT).show();
                        itemView.setOnTouchListener(new OnTouchListener(){

                                @Override
                                public boolean onTouch(View p1, MotionEvent p2) {
                                    if (p2.getAction() == MotionEvent.ACTION_UP) {
                                        Toast.makeText(context, "Yahoo", Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                            });
                    }

                    paint.setColor(context.getResources().getColor(R.color.color_edit));

                    c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                               (float) itemView.getBottom(), paint);

                    c.drawBitmap(editIcon,
                                 (float) itemView.getLeft() + dpToPx(16),
                                 (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - editIcon.getHeight()) / 2,
                                 paint);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, ItemTouchHelper.ACTION_STATE_IDLE, isCurrentlyActive);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(diagnosisRv);



        return view;
    }

    public int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
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
                    if (p1.length() > 0)
                        diagnosticsAdapter.getFilter().filter(p1);
                    else if (p1.length() == 0)
                        diagnosticsAdapter.getFilter().filter("");
                    return true;
                }
            });
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
            diagnosticsAdapter = new DiagnosticsAdapter(result, context);
            diagnosisRv.setAdapter(diagnosticsAdapter);
        }
    }
}
