package gbpec.comida;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohit Chauhan on 10/8/2017.
 */

public class Settings_activity extends AppCompatActivity implements View.OnClickListener {
    TextView feedback,rateUs,shareApp,devProfile;
    String playstore_id,shareMessage;
    SessionManager sessionManager;
    ImageView bck_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        feedback=(TextView)findViewById(R.id.btn_feedback);
        sessionManager = new SessionManager(getApplicationContext());
        bck_btn=(ImageView)findViewById(R.id.back_btn);
        getInfo();
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton1);

        if(sessionManager.isNotification())
        {
            toggle.setChecked(true);
        }
        else
            toggle.setChecked(false);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sessionManager.notificationOn();
                } else {
                    sessionManager.notificationOff();
                }
            }
        });
        rateUs=(TextView)findViewById(R.id.btn_Rate_us);
        shareApp=(TextView)findViewById(R.id.btn_share_app);
        feedback.setOnClickListener(this);
        bck_btn.setOnClickListener(this);

        shareApp.setOnClickListener(this);
        rateUs.setOnClickListener(this);

    }
    private void getInfo() {





        String url ="http://mohit.hol.es/FactoPedia/settings_factopedia.php";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Settings_activity.this,error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("server_response");
            JSONObject info = result.getJSONObject(0);
            playstore_id= info.getString("playstore_id");
            shareMessage= info.getString("shareMessage");



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch(id)
        {
            case R.id.btn_feedback:
                Intent fb= new Intent(this,Feedback_activity.class);
                startActivity(fb);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

                break;
            case R.id.btn_Rate_us:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+playstore_id)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+playstore_id)));
                }
                break;
            case R.id.btn_share_app :
                Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, "Donate Food and feed the hungry! Download Comida at Playstore" );
                startActivity(Intent.createChooser(intent2, "Complete Action Using"));
                break;

            case R.id.back_btn:
                finish();
                break;


        }

    }
}