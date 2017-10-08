package gbpec.comida.donor_module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gbpec.comida.R;

/**
 * Created by Mohit Chauhan on 10/6/2017.
 */

public class Donor_Home_Activity extends Fragment implements View.OnContextClickListener {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.donor_homeactivity, container, false);
        getActivity().setTitle("Home");
        return v;
    }

    @Override
    public boolean onContextClick(View view) {
        return false;
    }
}
