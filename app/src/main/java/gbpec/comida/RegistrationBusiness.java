package gbpec.comida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.LinearLayout;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class RegistrationBusiness extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String bnumber,bname,type,password,confirm_password,email,head_name,bnumber2,address,additional, mVerificationId;
    private EditText business_name,business_email,business_head,business_additional,others;
    private TextInputLayout other;
    private EditText business_num,otpnum;
    private Spinner spinner,spinner_donorType;
    private EditText bpassword,cbusiness;
    private EditText baddress;
    LinearLayout afterotp;
    LinearLayout business_type_layout;
    int itemType=1;
    private Button register,validate,verifymail,sendotp,verifyotp;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    static int d=1;
    int otpcomplete=1;
    String donor_type_selected;

    FirebaseAuth mAuth,auth,mauth;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
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

        auth=FirebaseAuth.getInstance();
        mauth=FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();

        afterotp=(LinearLayout)findViewById(R.id.afterotp);
        business_name=(EditText) findViewById(R.id.business_name);
        business_num=(EditText) findViewById(R.id.business_num1);
        otpnum=(EditText) findViewById(R.id.otpnum);
        baddress=(EditText) findViewById(R.id.business_address1);
        spinner= (Spinner) findViewById(R.id.business_type);
        spinner_donorType= (Spinner) findViewById(R.id.donor_type);
        business_email= (EditText) findViewById(R.id.business_email);
        business_head= (EditText) findViewById(R.id.business_head);
        bpassword=(EditText)findViewById(R.id.business_password);
        cbusiness=(EditText)findViewById(R.id. business_confirm);
        business_additional=(EditText)findViewById(R.id. business_additional);
        others=(EditText)findViewById(R.id.business_others);
        business_type_layout=(LinearLayout)findViewById(R.id.business_type_layout);
        business_type_layout.setVisibility(View.GONE);
        business_head.setVisibility(View.GONE);
        other=(TextInputLayout) findViewById(R.id.business_other) ;

        register=(Button)findViewById(R.id.register);
        verifymail=(Button)findViewById(R.id.verifymail);
        sendotp=(Button)findViewById(R.id.sendotp);
        verifyotp=(Button)findViewById(R.id.verifyotp);

        other.setVisibility(View.GONE);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(RegistrationBusiness.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(RegistrationBusiness.this);


        //adding validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.business_num1, "^.{10,13}$", R.string.contact1);
       // awesomeValidation.addValidation(this, R.id.business_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
       // awesomeValidation.addValidation(this, R.id.business_password, "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,16}$", R.string.passworderror);
        //awesomeValidation.addValidation(this, R.id.business_confirm, password,R.string.passwordconfirm);



        //SPINNER CODE
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.business_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //SPINNER CODE END

        spinner_donorType.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.donor_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_donorType.setAdapter(adapter2);

//for otp
        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
               // Toast.makeText(RegistrationBusiness.this,"111",Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegistrationBusiness.this,"phone number is incorrect or some network issue is there",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //  Log.d(TAG, "onCodeSent:" + verificationId);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                // ...
            }
        };

    }


    public void sendotp(View v){
        bnumber=business_num.getText().toString();

        if (awesomeValidation.validate()) {
            startPhoneNumberVerification(bnumber);
            otpnum.setVisibility(View.VISIBLE);
            verifyotp.setVisibility(View.VISIBLE);
            awesomeValidation.addValidation(this, R.id.business_password, "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,16}$", R.string.passworderror);

        }
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(RegistrationBusiness.this,"You are verified, congragts",Toast.LENGTH_LONG).show();
                            afterotp.setVisibility(View.VISIBLE);
                            business_num.setEnabled(false);
                            otpnum.setEnabled(false);
                            sendotp.setEnabled(false);
                            verifyotp.setEnabled(false);

                        } else {
                            Toast.makeText(RegistrationBusiness.this,"wrong otp",Toast.LENGTH_LONG).show();
                            // Sign in failed
                        }
                    }
                });
    }

    public void verifyotp(View v){
        String verificationCode=otpnum.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
        signInWithPhoneAuthCredential(credential);
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

    public void verifymail(View v){
        password=bpassword.getText().toString();
        confirm_password=cbusiness.getText().toString();
        email=business_email.getText().toString();
        //first check wether email have in or com, then check passwords after that email is sent for verification
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
                  //  Toast.makeText(getApplicationContext(), "First ", Toast.LENGTH_LONG).show();

//sending email verification
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    // If sign in fails, display a message to the user. If sign in succeeds the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegistrationBusiness.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegistrationBusiness.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                        FirebaseUser user = auth.getCurrentUser();

                                        final String email2 = user.getEmail();
                                        String uid = user.getUid();

                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(RegistrationBusiness.this, "email sent to registered email\n PLEASE VERIFY FROM THERE" + task.getException(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    Toast.makeText(RegistrationBusiness.this, "failed to send email verification due to invalid email or network problem" + task.getException(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                        //emailv.setText(email2+"::"+ user.isEmailVerified());

                                    }
                                }
                            });


                }

            }
        }

    }


    public void registerBusiness(View v){

        bnumber=business_num.getText().toString();
        bnumber2=otpnum.getText().toString();
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

        mauth.signInWithEmailAndPassword(email,password).
                addOnCompleteListener(RegistrationBusiness.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(RegistrationBusiness.this,"failed to login",Toast.LENGTH_LONG).show();
                        }
                        else{

                            FirebaseUser user = auth.getCurrentUser();
                            final String email2 = user.getEmail();
                            boolean emailv=user.isEmailVerified();
                           // verifyemail.setText(email2+"::"+ user.isEmailVerified());
                            if(emailv==true) {
                                Toast.makeText(RegistrationBusiness.this, "login complete " + emailv, Toast.LENGTH_LONG).show();
                                UserRegistration();
                            }
                            else{
                                Toast.makeText(RegistrationBusiness.this, "login incomplete please verify your email account" , Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
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

        /*int emv=emailValid(email);
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
        }*/
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
        if(adapterView.getId()==R.id.business_type){


        String item = adapterView.getItemAtPosition(i).toString();
        if(item.equals("Others")){
            other.setVisibility(View.VISIBLE);
            itemType=2;
            type=others.getText().toString();
        }
        else{
            other.setVisibility(View.GONE);
            itemType=1;
            type=item;
        }
    }
    else if(adapterView.getId()==R.id.donor_type){
            donor_type_selected= adapterView.getItemAtPosition(i).toString();
            if(donor_type_selected.equals("One Time Donor")){
               business_type_layout.setVisibility(View.GONE);
               business_head.setVisibility(View.GONE);
               business_name.setHint("Name");
               type=donor_type_selected;
            }
            else{
                business_type_layout.setVisibility(View.VISIBLE);
                business_head.setVisibility(View.VISIBLE);
                business_name.setHint("Business Name");
            }
        }



        // Showing selected spinner item
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
