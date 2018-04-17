package gbpec.comida.reciever_module;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.HashMap;


import java.util.ArrayList;
import java.util.List;

import gbpec.comida.R;
import gbpec.comida.SessionManager;
import gbpec.comida.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Clothes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Clothes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Clothes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String contact,details,donor,pickupTime,validDate,validTime,username,ngo_latiude,ngo_longitude,cLatitude,cLongitude;
    private static Double lat1=0.0,lon1=0.0;
    private OnFragmentInteractionListener mListener;
    //for recycler view
    private List<Food> foodList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BooknClothesAdapter mAdapter;

    public Clothes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Clothes.
     */
    // TODO: Rename and change types and number of parameters
    public static Clothes newInstance(String param1, String param2) {
        Clothes fragment = new Clothes();
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
        // Inflate the layout for this fragment

        SessionManager session;
        session = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        username = user1.get(SessionManager.USER_CONTACT);
        Toast.makeText(getActivity().getApplicationContext(), username, Toast.LENGTH_LONG).show();

        View v= inflater.inflate(R.layout.fragment_clothes, container, false);
        clothesItems();

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        mAdapter = new BooknClothesAdapter(foodList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    public void clothesItems(){
        String URL="http://vipul.hol.es/clothdetails.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject  jsonObject1 = new JSONObject(response);

                    JSONArray result = jsonObject1.getJSONArray("result");

                    for(int i=0;i<result.length();i++){
                        JSONObject businessData = result.getJSONObject(i);
                        prepareClothData(businessData);
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
        //  Toast.makeText(getContext(),"exe",Toast.LENGTH_LONG).show();
        requestQueue.add(stringRequest);

    }

    public void prepareClothData(JSONObject business) throws JSONException{
        contact =business.getString("contact");
        details = business.getString("details");
        details="Cloth Items- "+details.replaceAll("-.*?&","");
        donor = business.getString("donor");
        pickupTime="Pickup Time- "+business.getString("pickupTime");
        validDate="Clothes valid upto- "+business.getString("validDate");
        validTime="     "+business.getString("validTime");
        cLatitude=business.getString("fLatitude");
        cLongitude=business.getString("fLongitude");
        Double lat2,lon2;
        //lat2=Double.parseDouble(bLatitude);
        //lon2=Double.parseDouble(bLongitude);

        Food food = new Food(donor, contact,details,pickupTime,validDate,validTime);
        foodList.add(food);

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
