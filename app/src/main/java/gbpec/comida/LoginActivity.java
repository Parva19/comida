package gbpec.comida;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        username=(EditText)findViewById(R.id.t_username);
        password=(EditText)findViewById(R.id.t_password);
        signUp= (Button)findViewById(R.id.signup_bttn);
        login= (Button)findViewById(R.id.btn_login);
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);

    }
    private void checkLogin(String username, String password) {





        String url = "http://vipul.hol.es/login_fetch.php?user_no="+username+"&password="+password;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("success");
            if(result.length()!=0)
            {
                Toast.makeText(LoginActivity.this,"password and id matched",Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup_bttn) {
            Intent i = new Intent(LoginActivity.this, Registration_options.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.btn_login) {
            String user_name = username.toString();
            String pass_word = password.toString();
            checkLogin(user_name, pass_word);


        }
    }
}

