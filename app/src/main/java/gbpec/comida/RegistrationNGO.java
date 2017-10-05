package gbpec.comida;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.HashMap;
import java.util.Map;

public class RegistrationNGO extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String ngonumber,ngoname,type,password,confirm_password,email,head_name,ngonumber2,address,additional;
    private EditText ngo_name,ngo_email,ngo_head,ngo_additional;
    private EditText ngo_num,ngo_num2;
    private Spinner spinner;
    private EditText ngopassword,cngo;
    private EditText ngoaddress;
    private Button register;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    //String password=null;
    private static final String REGISTER_URL="http://vipul.hol.es/nregister.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_ngo);

        ngo_name=(EditText) findViewById(R.id.ngo_name);
        ngo_num=(EditText) findViewById(R.id.ngo_num1);
        ngo_num2=(EditText) findViewById(R.id.ngo_num2);
        ngoaddress=(EditText) findViewById(R.id.ngo_address);
        spinner= (Spinner) findViewById(R.id.ngo_type);
        ngo_email= (EditText) findViewById(R.id.ngo_email);
        ngo_head= (EditText) findViewById(R.id.ngo_head);
        ngopassword=(EditText)findViewById(R.id.ngo_password);
        cngo=(EditText)findViewById(R.id. ngo_confirm);
        ngo_additional=(EditText)findViewById(R.id. ngo_additional);

        register=(Button)findViewById(R.id.register);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(RegistrationNGO.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(RegistrationNGO.this);

        //adding validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.ngo_num1, "^.{10,}$", R.string.contact1);
        awesomeValidation.addValidation(this, R.id.ngo_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.ngo_password, "^.{8,}$", R.string.passworderror);
        // awesomeValidation.addValidation(this, R.id.business_confirm, password,R.string.passwordconfirm);
        //SPINNER CODE
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ngo_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //SPINNER CODE END

    }

    public void registerNgo(View v){
        ngonumber=ngo_num.getText().toString();
        ngonumber2=ngo_num2.getText().toString();
        ngoname=ngo_name.getText().toString();
        password=ngopassword.getText().toString();
        confirm_password=cngo.getText().toString();
        email=ngo_email.getText().toString();
        head_name=ngo_head.getText().toString();
        address=ngoaddress.getText().toString();
        additional=ngo_additional.getText().toString();
        if(!password.equals(confirm_password)){
            cngo.setError("Password doesn't match");
         //   Toast.makeText(getApplicationContext(), " Confirm Password and password should be same", Toast.LENGTH_LONG).show();
            awesomeValidation.validate();

        }
        else{
            if ( awesomeValidation.validate()){
           //     Toast.makeText(getApplicationContext(), "First ", Toast.LENGTH_LONG).show();
                UserRegistration();
            }
            //awesomeValidation.validate();

        }
    }

    public void UserRegistration(){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(RegistrationNGO.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(RegistrationNGO.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.

                params.put("nName", ngoname);
                params.put("nType", type);
                params.put("nContact",ngonumber);
                params.put("nContact2",ngonumber2);
                params.put("nHead",head_name);
                params.put("nPassword",password);
                //params.put("confirm_password",confirm_password);
                params.put("nEmail",email);
                params.put("nAddress",address);// convert into latitude and longitude
                params.put("nEstPeople",additional);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(RegistrationNGO.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        type=item;
        // Showing selected spinner item
    //    Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
