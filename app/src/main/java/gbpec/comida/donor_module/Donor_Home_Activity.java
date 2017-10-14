package gbpec.comida.donor_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import gbpec.comida.FoodItems;
import gbpec.comida.R;

/**
 * Created by Mohit Chauhan on 10/6/2017.
 */

public class Donor_Home_Activity extends Fragment implements View.OnContextClickListener {
RelativeLayout donate;
    String username;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.donor_homeactivity, container, false);

        username = getArguments().getString("username");
        donate=(RelativeLayout)v.findViewById(R.id.donate_food);
        Toast.makeText(getActivity().getApplicationContext(),username,Toast.LENGTH_LONG).show();
        donate.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(),
                        FoodItems.class);
                myIntent.putExtra("user",username);
                startActivity(myIntent);
            }
        });
        getActivity().setTitle("Home");
        return v;
    }

    public void getUser(String user){

    }
    @Override
    public boolean onContextClick(View view) {
        return false;
    }
}
