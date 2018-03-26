package gbpec.comida.donor_module;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gbpec.comida.Clothes;
import gbpec.comida.Education;
import gbpec.comida.FoodItems;
import gbpec.comida.R;

/**
 * Created by Vipul Chauhan on 3/22/2018.
 */

public class DonationOptionDialog extends android.support.v4.app.DialogFragment implements View.OnClickListener {
    Button donateFood;
    Button donateBooks;
    Button donateClothes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.donation_options_dialog, container,
                false);

        getDialog().setTitle("Donate..");

        donateFood=(Button)rootView.findViewById(R.id.donate_food);
        donateBooks=(Button)rootView.findViewById(R.id.donate_book);
        donateClothes=(Button)rootView.findViewById(R.id.donate_clothes);

        donateFood.setOnClickListener(this);
        donateBooks.setOnClickListener(this);
        donateClothes.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.donate_food:

                Intent myIntent = new Intent(getActivity(),
                        FoodItems.class);
                //   myIntent.putExtra("user",username);
                startActivity(myIntent);

                break;
            case R.id.donate_clothes:
                Intent myIntent2 = new Intent(getActivity(),
                        Clothes.class);
                //   myIntent.putExtra("user",username);
                startActivity(myIntent2);

                break;
            case R.id.donate_book:
                Intent myIntent3 = new Intent(getActivity(),
                        Education.class);
                //   myIntent.putExtra("user",username);
                startActivity(myIntent3);

                break;
        }
    }
}
