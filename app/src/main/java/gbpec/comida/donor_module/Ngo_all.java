package gbpec.comida.donor_module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import java.util.List;

import gbpec.comida.R;

public class Ngo_all extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<NgoData> ngoList=new ArrayList<>();
    private NgoListAdapter nAdapter;
    private String nName,nContact,nEmail,nAddress,nPic,nHead,nType;
String URL="http://vipul.hol.es/ngo_list.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_all);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Context c=getApplicationContext();
        nAdapter=new NgoListAdapter(ngoList,c);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(nAdapter);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   loading.dismiss();
                //   Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
                //WORKING CORRECTLY NAD GETTING DATA
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    // Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
                    JSONArray result = jsonObject1.getJSONArray("result");
                    //  Toast.makeText(getContext(),"2",Toast.LENGTH_LONG).show();
                    for(int i=0;i<result.length();i++){
                        JSONObject businessData = result.getJSONObject(i);
                        prepareNgodata(businessData);
                    }

                    //
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getContext(),"data-",Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //  Toast.makeText(getContext(),"exe",Toast.LENGTH_LONG).show();
        requestQueue.add(stringRequest);


    }

    public void prepareNgodata(JSONObject businessData) throws JSONException{

        nName=businessData.getString("nName");
        nContact=businessData.getString("ncontact");
        nType=businessData.getString("nType");
        nHead=businessData.getString("nHead");
        nEmail=businessData.getString("nEmail");
        nAddress=businessData.getString("nAddress");
        nPic=businessData.getString("nPic");
        NgoData data= new NgoData(nName,nContact,nType,nHead,nEmail,nAddress,nPic);
        ngoList.add(data);
        nAdapter.notifyDataSetChanged();

    }
}
