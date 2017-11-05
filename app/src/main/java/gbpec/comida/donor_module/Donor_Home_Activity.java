package gbpec.comida.donor_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import gbpec.comida.FoodItems;
import gbpec.comida.R;
import gbpec.comida.SessionManager;
import gbpec.comida.donor_module.Food;
import gbpec.comida.donor_module.FoodAdapter;

/**
 * Created by Mohit Chauhan on 10/6/2017.
 */

public class Donor_Home_Activity extends Fragment implements View.OnClickListener {
RelativeLayout donate;
    String username,food_details,status,valid_date, check_date;

    private List<Food> foodList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FoodAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState) {

        SessionManager session;
        session = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        username = user1.get(SessionManager.KEY_NAME);
        Toast.makeText(getActivity().getApplicationContext(), username, Toast.LENGTH_LONG).show();

        View v = inflater.inflate(R.layout.donor_homeactivity, container, false);

       // username = getArguments().getString("username");
      donate=(RelativeLayout)v.findViewById(R.id.donate_food);
        donate.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(),
                        FoodItems.class);
             //   myIntent.putExtra("user",username);
                startActivity(myIntent);
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yy");
        check_date = "Current Date : " + mdformat.format(calendar.getTime());
        Toast.makeText(getContext(),check_date,Toast.LENGTH_LONG).show();



        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        mAdapter = new FoodAdapter(foodList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        String URL="http://vipul.hol.es/sender_foods.php?contactno="+username;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   loading.dismiss();
                Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
                //WORKING CORRECTLY NAD GETTING DATA
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    // Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
                    JSONArray result = jsonObject1.getJSONArray("result");
                    //  Toast.makeText(getContext(),"2",Toast.LENGTH_LONG).show();
                    for(int i=0;i<result.length();i++){
                        JSONObject foodData = result.getJSONObject(i);
                        prepareFoodStatus(foodData);
                    }

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

        getActivity().setTitle("Home");
        return v;
    }

public void prepareFoodStatus(JSONObject foodData) throws JSONException {
    food_details=foodData.getString("food_details");
    status=foodData.getString("status");
    valid_date=foodData.getString("valid_date");
   // Toast.makeText(getContext(),valid_date,Toast.LENGTH_LONG).show();

    Food food = new Food(food_details,status);
    foodList.add(food);


    mAdapter.notifyDataSetChanged();
}

    @Override
    public void onClick(View view) {

    }
}
