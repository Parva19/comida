package gbpec.comida.donor_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
/**
 * Created by Mohit Chauhan on 10/6/2017.
 */

public class Donor_Home_Activity extends Fragment implements View.OnClickListener {
RelativeLayout donate;
    String username,food_details,status,valid_date, check_date, ngos,your_foods;

    private List<Food> foodList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FoodAdapter mAdapter;
    private TextView amount_food,ngos_total;
    static final int NUM_ITEMS = 4;
    ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    ViewPager viewPager;
    public static final int[] IMAGE_NAME = {R.drawable.food_image3,R.drawable.food_image2,R.drawable.food_image4,R.drawable.food_image1};

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState) {

        SessionManager session;
        session = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        username = user1.get(SessionManager.KEY_NAME);
        Toast.makeText(getActivity().getApplicationContext(), username, Toast.LENGTH_LONG).show();

        View v = inflater.inflate(R.layout.donor_homeactivity, container, false);
//Image view Slider
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.setAdapter(imageFragmentPagerAdapter);
//Image slider end
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

        amount_food=(TextView)v.findViewById(R.id.amount_food);
        ngos_total=(TextView)v.findViewById(R.id.ngo_total);

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
              //  Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
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


        String URL1="http://vipul.hol.es/ngos_foods.php?contactno="+username;

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
                      ngos=Data.getString("ngos");
                     your_foods=Data.getString("your_foods");
                    amount_food.setText(your_foods);
                    ngos_total.setText(ngos);
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue1.add(stringRequest1);


        getActivity().setTitle("Home");
        return v;
    }
// Image slider Code--
    public static class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            SwipeFragment fragment = new SwipeFragment();
            return SwipeFragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
            ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
           // String imageFileName = IMAGE_NAME[position];
           // int imgResId = getResources().getIdentifier(imageFileName, "drawable", "com.gbpec.comida.donor_module");
            imageView.setImageResource(IMAGE_NAME[position]);
            return swipeView;
        }

        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);
            return swipeFragment;
        }
    }
//Image slider code end

public void prepareFoodStatus(JSONObject foodData) throws JSONException {
    food_details="Food Items:: "+foodData.getString("food_details");
    status="Status:: "+foodData.getString("status");
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
