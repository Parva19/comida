package gbpec.comida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohit Chauhan on 10/16/2017.
 */

public class Feedback_activity extends AppCompatActivity implements View.OnClickListener  {
    ProgressDialog progressDialog;
    private static final String REGISTER_URL="http://mohit.hol.es/FactoPedia/feedback.php";
    EditText name,email,feedback;
    Button send;
    String user_id,user_name,user_email, user_feedback;
    ImageView bck_btn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);
        name=(EditText)findViewById(R.id.fb_name);
        email=(EditText)findViewById(R.id.fb_email);
        feedback=(EditText)findViewById(R.id.fb__feedback);
        send=(Button) findViewById(R.id.send_feedback);
        bck_btn=(ImageView)findViewById(R.id.bck_btn);
        bck_btn.setOnClickListener(this);

        send.setOnClickListener(this);



    }
    public void sendFeedback(){

        // Showing progress dialog at user registration time.
        final ProgressDialog progressDialog = new ProgressDialog(Feedback_activity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data Please wait...");
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(Feedback_activity.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Feedback_activity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                //params.put("mnumber",bnumber);
                params.put("Device_ID", user_id);
                params.put("Name", user_name);
                params.put("Email",user_email);
                params.put("Feedback",user_feedback);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Feedback_activity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View view) {
        int id =view.getId();
        if(id==R.id.send_feedback)
        {
            user_email=email.getText().toString();
            user_name=name.getText().toString();
            user_feedback=feedback.getText().toString();
            if(user_feedback.equals("") || user_name.equals("") || user_email.equals(""))
            {
                Toast.makeText(Feedback_activity.this, "Enter your name,email and Feedback", Toast.LENGTH_LONG).show();
            }
            else {
                user_id = FirebaseInstanceId.getInstance().getToken();

                sendFeedback();
                finish();
            }
        }
        else if(id==R.id.bck_btn)
        {
            Intent bck= new Intent(this,Settings_activity.class);
            startActivity(bck);
            finish();
        }

    }
}
