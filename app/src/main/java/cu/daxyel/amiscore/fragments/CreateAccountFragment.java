package cu.daxyel.amiscore.fragments;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import cu.daxyel.amiscore.R;
import android.widget.Button;
import android.view.View.OnClickListener;

public class CreateAccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_createaccount, container, false);
        Button b = view.findViewById(R.id.switch_sign_in_btn);
        b.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new LoginFragment()).commit();
                }
            });
        return view;
    }

}
