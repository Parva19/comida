package gbpec.comida.reciever_module;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class Reciever_Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String food_id,contact,details,donor,pickupTime,validDate,validTime,username,ngo_latiude,ngo_longitude,fLatitude,fLongitude,fRecieverId;
    private static Double lat1=0.0,lon1=0.0;
    private OnFragmentInteractionListener mListener;
    //for recycler view
    private List<Food> foodList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FoodAdapter mAdapter;
    //
   ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    ViewPager viewPager;
    public static final int[] IMAGE_NAME = {R.drawable.ngo1,R.drawable.ngo2,R.drawable.ngo3,R.drawable.ngo4};
    static final int NUM_ITEMS = 4;
    public Reciever_Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reciever_Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Reciever_Home newInstance(String param1, String param2) {
        Reciever_Home fragment = new Reciever_Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SessionManager session;
        session = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        username = user1.get(SessionManager.USER_CONTACT);
        Toast.makeText(getActivity().getApplicationContext(), username, Toast.LENGTH_LONG).show();


        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_reciever__home, container, false);

       /* imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.setAdapter(imageFragmentPagerAdapter);*/

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);

        String URL1="http://vipul.hol.es/getNgoLocation.php?contactno="+username;

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET,URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   loading.dismiss();
             //   Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
                //WORKING CORRECTLY NAD GETTING DATA
                try {
                    JSONObject  jsonObject1 = new JSONObject(response);
                 //   Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
                    JSONArray result = jsonObject1.getJSONArray("result");
                  //   Toast.makeText(getContext(),"2",Toast.LENGTH_LONG).show();

                    JSONObject businessData = result.getJSONObject(0);
                    ngo_latiude=businessData.getString("Latitude");
                    ngo_longitude=businessData.getString("Longitude");
                    lat1=28.4;
                    lon1=77.5;
                   // lat1=Double.parseDouble(ngo_latiude);
                   // lon1=Double.parseDouble(ngo_longitude);
                    foodItems();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getContext(),ngo_latiude+"lo-"+ngo_longitude,Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
        //  Toast.makeText(getContext(),"exe",Toast.LENGTH_LONG).show();
        requestQueue1.add(stringRequest1);
        mAdapter = new FoodAdapter(foodList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

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

    public void foodItems(){

        String URL="http://vipul.hol.es/foodDetails.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   loading.dismiss();
             //   Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
                //WORKING CORRECTLY NAD GETTING DATA
                try {
                    JSONObject  jsonObject1 = new JSONObject(response);
                    // Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
                    JSONArray result = jsonObject1.getJSONArray("result");
                    //  Toast.makeText(getContext(),"2",Toast.LENGTH_LONG).show();
                    for(int i=0;i<result.length();i++){
                        JSONObject businessData = result.getJSONObject(i);
                        prepareMovieData(businessData);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //  Toast.makeText(getContext(),"exe",Toast.LENGTH_LONG).show();
        requestQueue.add(stringRequest);
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515* 1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void prepareMovieData(JSONObject business) throws JSONException {


        food_id=business.getString("id");
        contact =business.getString("contact");
        details = business.getString("details");
        details= details.replace("&"," \n");
        donor = business.getString("donor");
        pickupTime=business.getString("pickupTime");
        validDate=business.getString("validDate");
        validTime=business.getString("validTime");
        fLatitude=business.getString("fLatitude");
        fLongitude=business.getString("fLongitude");
        fRecieverId=business.getString("fRecieverId");

        Double lat2,lon2;
        lat2=Double.parseDouble(fLatitude);
        lon2=Double.parseDouble(fLongitude);
     //   Toast.makeText(getContext(), "lat-"+Double.toString(lat1),Toast.LENGTH_LONG).show();
        double i=distance(lat1,lon1,lat2,lon2);
     // Toast.makeText(getContext(), "dis-"+Double.toString(i),Toast.LENGTH_LONG).show();

        if(i<25.00) {
            Food food = new Food(food_id,donor, contact,details,pickupTime,validDate,validTime,fRecieverId);
            foodList.add(food);
        }

        mAdapter.notifyDataSetChanged();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
