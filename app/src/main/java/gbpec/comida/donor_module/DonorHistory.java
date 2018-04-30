package gbpec.comida.donor_module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import gbpec.comida.R;
import gbpec.comida.SessionManager;

public class DonorHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DonorHistoryData> historyData=new ArrayList<>();
    private DonorHistoryAdapter nAdapter;
    private String fDetail,fRequestDate,fRequestTime,fStatus,username;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_history);

        SessionManager session;
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        username = user1.get(SessionManager.USER_CONTACT);
        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        nAdapter=new DonorHistoryAdapter(historyData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(nAdapter);
       String URL="http://vipul.hol.es/history.php?contactno="+username;
       StringRequest stringRequest1 = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               //   loading.dismiss();
               // Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
               //WORKING CORRECTLY NAD GETTING DATA
               try {
                   JSONObject jsonObject1 = new JSONObject(response);
                   // Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
                   JSONArray result = jsonObject1.getJSONArray("result");
                   //  Toast.makeText(getContext(),"2",Toast.LENGTH_LONG).show();
                   for(int i=0;i<result.length();i++){
                       JSONObject businessData = result.getJSONObject(i);
                       preparedata(businessData);
                   }


               } catch (JSONException e) {
                   e.printStackTrace();
               }

               //    Toast.makeText(getContext(),ngo_latiude+"lo-"+ngo_longitude,Toast.LENGTH_LONG).show();
               // showJSON(response);
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
       //  Toast.makeText(getContext(),"exe",Toast.LENGTH_LONG).show();
       requestQueue1.add(stringRequest1);

    }

    public void preparedata(JSONObject businessdata) throws JSONException{
       fDetail="Food Details:  "+businessdata.getString("food_details");
       fStatus="Status:  "+businessdata.getString("status");
       fRequestDate="Requested Date: "+businessdata.getString("validDate");
       fRequestDate="Requested Time:  "+businessdata.getString("validTime");

        DonorHistoryData data=new DonorHistoryData(fDetail,fRequestDate,fRequestTime,fStatus);
        historyData.add(data);
        nAdapter.notifyDataSetChanged();
    }
}
