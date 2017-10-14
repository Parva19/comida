package gbpec.comida.donor_module;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import gbpec.comida.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Donor_Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Donor_Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Donor_Profile extends Fragment {
    String username;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String Contact,Email,Address,Info;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    TextView contact,address,email,info;

    public Donor_Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Donor_Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Donor_Profile newInstance(String param1, String param2) {
        Donor_Profile fragment = new Donor_Profile();
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

        username=getArguments().getString("username");
        Toast.makeText(getActivity().getApplicationContext(),"profile- "+username,Toast.LENGTH_LONG).show();

        //getProfileData();

    }

    public void getProfileData(){

    }

    public void showJSON(String response) {

        String contact = "";
        String address = "";
        String email = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(getContext(),"chlaa",Toast.LENGTH_LONG).show();

            JSONArray result = jsonObject.getJSONArray("result");
            Toast.makeText(getContext(),"chl",Toast.LENGTH_LONG).show();

            JSONObject businessData = result.getJSONObject(0);
            contact =businessData.getString("contact");
            address = businessData.getString("address");
            email = businessData.getString("email");
            if(result.length()!=0)
            {
                Toast.makeText(getActivity().getApplicationContext(),"aagya",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(),"nhi aaya ",Toast.LENGTH_LONG).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(),"data-",Toast.LENGTH_LONG).show();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_donor__profile, container, false);


       // final ProgressDialog loading;
       // loading = ProgressDialog.show(getContext(),"Please wait...","Fetching...",false,false);
        String URL="http://vipul.hol.es/getProfile.php?contactno="+username;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   loading.dismiss();
                Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
             //WORKING CORRECTLY NAD GETTING DATA
                try {
                    JSONObject  jsonObject1 = new JSONObject(response);
                   // Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
                    JSONArray result = jsonObject1.getJSONArray("result");
                  //  Toast.makeText(getContext(),"2",Toast.LENGTH_LONG).show();
                    JSONObject businessData = result.getJSONObject(0);
                    Contact =businessData.getString("contact");
                     Address = businessData.getString("address");
                    Email = businessData.getString("email");
                    Info=businessData.getString("info");
                    //setting values
                    contact.setText(Contact);
                    address.setText(Address);
                    email.setText(Email);
                    info.setText(Info);
                    //
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Toast.makeText(getContext(),"data-"+Contact+Address+Email,Toast.LENGTH_LONG).show();
               // showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
      //  Toast.makeText(getContext(),"execute",Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
      //  Toast.makeText(getContext(),"exe",Toast.LENGTH_LONG).show();
        requestQueue.add(stringRequest);


       contact=(TextView)v.findViewById(R.id.contact);
       address=(TextView)v.findViewById(R.id.address);
       email=(TextView)v.findViewById(R.id.email);
       info=(TextView)v.findViewById(R.id.info);




        return  v;
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
