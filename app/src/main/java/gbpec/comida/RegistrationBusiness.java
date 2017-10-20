package gbpec.comida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationBusiness extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String bnumber,bname,type,password,confirm_password,email,head_name,bnumber2,address,additional;
    private EditText business_name,business_email,business_head,business_additional,others;
    private TextInputLayout other;
    private EditText business_num,business_num2;
    private Spinner spinner;
    private EditText bpassword,cbusiness;
    private EditText baddress;
int itemType=1;
    private Button register,validate;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    static int d=1;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    //String password=null;
    private static final String REGISTER_URL="http://vipul.hol.es/bregister.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_business);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tab_register_business);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle("Create an account");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*View logoView = getToolbarLogoView(toolbar);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
                //logo clicked
            }

            private void change() {
               // Intent i=new Intent(this,Registration_options.class);
            }
        });*/

        business_name=(EditText) findViewById(R.id.business_name);
        business_num=(EditText) findViewById(R.id.business_num1);
        business_num2=(EditText) findViewById(R.id.business_num2);
        baddress=(EditText) findViewById(R.id.business_address1);
        spinner= (Spinner) findViewById(R.id.business_type);
        business_email= (EditText) findViewById(R.id.business_email);
        business_head= (EditText) findViewById(R.id.business_head);
        bpassword=(EditText)findViewById(R.id.business_password);
        cbusiness=(EditText)findViewById(R.id. business_confirm);
        business_additional=(EditText)findViewById(R.id. business_additional);
        others=(EditText)findViewById(R.id.business_others);
        other=(TextInputLayout) findViewById(R.id.business_other) ;

        register=(Button)findViewById(R.id.register);
        other.setVisibility(View.GONE);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(RegistrationBusiness.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(RegistrationBusiness.this);


        //adding validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.business_num1, "^.{10,13}$", R.string.contact1);
        awesomeValidation.addValidation(this, R.id.business_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.business_password, "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,16}$", R.string.passworderror);
        // awesomeValidation.addValidation(this, R.id.business_confirm, password,R.string.passwordconfirm);



        //SPINNER CODE
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.business_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //SPINNER CODE END

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(this, Registration_options.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void registerBusiness(View v){

        bnumber=business_num.getText().toString();
        bnumber2=business_num2.getText().toString();
        bname=business_name.getText().toString();
        password=bpassword.getText().toString();
        confirm_password=cbusiness.getText().toString();
        email=business_email.getText().toString();
        head_name=business_head.getText().toString();
        address=baddress.getText().toString();
        additional=business_additional.getText().toString();
        if(itemType==2){
            type=others.getText().toString();
        }
       /* if(!password.equals(confirm_password)){
            cbusiness.setError("Password doesn't match");
          //  Toast.makeText(getApplicationContext(), " Confirm Password and password should be same", Toast.LENGTH_LONG).show();
            awesomeValidation.validate();
        }
        else{
            if ( awesomeValidation.validate()){
                //Toast.makeText(getApplicationContext(), "First ", Toast.LENGTH_LONG).show();
                UserRegistration();
            }
            //awesomeValidation.validate();

        }*/
         /*int length=email.length();
       sub1=email.substring(email.length()-3);
       sub2=email.substring(email.length()-2);
        if(sub1.equals(".com") || sub2.equals(".in")) {*/

        int emv=emailValid(email);
        if(emv==0){
            business_email.setError("invalid email");
        }
        else {
            if (!password.equals(confirm_password)) {
                cbusiness.setError("Password doesn't match");
                //  Toast.makeText(getApplicationContext(), " Confirm Password and password should be same", Toast.LENGTH_LONG).show();
                awesomeValidation.validate();

            } else {
                if (awesomeValidation.validate()) {
                    //Toast.makeText(getApplicationContext(), "First ", Toast.LENGTH_LONG).show();
                    UserRegistration();
                }
                //awesomeValidation.validate();

            }
        }
        //  }
        /*else{
            business_email.setError("invalid email");
        }*/


    }
    private int emailValid(String email) {
        // EditText usiness_email= (EditText) findViewById(R.id.business_email);
        String mail=email;
        int le=email.length();
        if(le==0){
            return 0;
        }
        else{
            String sub1=mail.substring(email.length()-3);
            Toast.makeText(getApplicationContext(), sub1, Toast.LENGTH_LONG).show();

           String sub2=mail.substring(email.length()-2);
            if(sub1.equals("com") || sub2.equals("in")) {
                return 1;
            }
            return 0;
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
                        Toast.makeText(RegistrationBusiness.this, ServerResponse, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(RegistrationBusiness.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                //params.put("mnumber",bnumber);
                params.put("bName", bname);
                params.put("bType", type);
                params.put("bContact",bnumber);
                params.put("bContact2",bnumber2);
                params.put("bEmail",email);
                params.put("bAddress",address);
                params.put("bHead",head_name);
                params.put("bPassword",password);
                // params.put("confirm_password",confirm_password);
                params.put("bAdditionalInfo",additional);
                params.put("btype","business");
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(RegistrationBusiness.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        if(item.equals("Others")){
            other.setVisibility(View.VISIBLE);
            itemType=2;
           // type=others.getText().toString();
        }
        else{
            other.setVisibility(View.GONE);
            itemType=1;
            type=item;
        }


        // Showing selected spinner item
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
