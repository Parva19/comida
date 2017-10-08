package gbpec.comida;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Mohit Chauhan on 10/6/2017.
 */

public class Registration_options extends AppCompatActivity implements View.OnClickListener {

RelativeLayout donor,reciever;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_option);


        donor = (RelativeLayout) findViewById(R.id.donor_btn);
        reciever = (RelativeLayout) findViewById(R.id.reciver_btn);
        donor.setOnClickListener(this);
        reciever.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.donor_btn){
            Intent i= new Intent(Registration_options.this,RegistrationBusiness.class);
            startActivity(i);
        }
        if(v.getId()== R.id.reciver_btn){
            Intent i= new Intent(Registration_options.this,RegistrationNGO.class);
            startActivity(i);
        }

    }
}