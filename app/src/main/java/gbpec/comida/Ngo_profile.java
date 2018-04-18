package gbpec.comida;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ngo_profile  extends AppCompatActivity implements View.OnClickListener {
    public String name,email,address,contact,head,collector,food_id;
    private TextView Collector,Name,Email,Address,Contact,Head;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_view_donor);
        Collector=(TextView)findViewById(R.id.ngo_collector);
        Name=(TextView)findViewById(R.id.Ngo_name);
        Email=(TextView)findViewById(R.id.ngo_email);
        Address=(TextView)findViewById(R.id.ngo_address);
        Contact=(TextView)findViewById(R.id.Ngo_contact);
        Head=(TextView)findViewById(R.id.ngo_head);
        Bundle bundle = getIntent().getExtras();
        food_id = bundle.getString("food_id");
        String URL1="http://vipul.hol.es/ngo_detail.php?food_id="+food_id;

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET,URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   loading.dismiss();
                //  Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
                //WORKING CORRECTLY NAD GETTING DATA
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    // Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
                    JSONArray result = jsonObject1.getJSONArray("result");
                    //  Toast.makeText(getContext(),"2",Toast.LENGTH_LONG).show();
                    JSONObject Data = result.getJSONObject(0);
                    name=Data.getString("name");
                    email=Data.getString("email");
                    address=Data.getString("address");
                    collector=Data.getString("collector");
                    head=Data.getString("head");
                    contact=Data.getString("contact");

                    //   Toast.makeText(getContext(),ngos+your_foods,Toast.LENGTH_LONG).show();
                    //
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //                        Toast.makeText(getActivity().getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        //  Toast.makeText(getContext(),"execute",Toast.LENGTH_LONG).show();
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(stringRequest1);


//



    }

    @Override
    public void onClick(View v) {

    }
}
