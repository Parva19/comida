package gbpec.comida;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gbpec.comida.donor_module.Donor_NavigationMainActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signUp,login;
    EditText username,password;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signUp= (Button)findViewById(R.id.signup_bttn);
        login= (Button)findViewById(R.id.btn_login);
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.signup_bttn){
            Intent i= new Intent(LoginActivity.this,Registration_options.class);
           startActivity(i);
        }
        else if(v.getId()== R.id.btn_login){

         Intent log = new Intent(LoginActivity.this, Donor_NavigationMainActivity.class);
            startActivity(log);

        }
    }
}

