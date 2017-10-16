package gbpec.comida.donor_module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import gbpec.comida.R;
import gbpec.comida.RegistrationBusiness;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Edit_Profilr.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Edit_Profilr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Edit_Profilr extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private String Contact,Email,Address,Info,username;

    EditText contact,address,email,info;
    private OnFragmentInteractionListener mListener;

    public Edit_Profilr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Edit_Profilr.
     */
    // TODO: Rename and change types and number of parameters
    public static Edit_Profilr newInstance(String param1, String param2) {
        Edit_Profilr fragment = new Edit_Profilr();
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
        Contact=getArguments().getString("Contact");
        Address=getArguments().getString("Address");
        Email=getArguments().getString("Email");
        Info=getArguments().getString("Info");
        Toast.makeText(getActivity().getApplicationContext(),"ttt--"+username,Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View v= inflater.inflate(R.layout.fragment_edit__profilr, container, false);
        contact=(EditText)v.findViewById(R.id.edit_contact);
        address=(EditText)v.findViewById(R.id.edit_address);
       email=(EditText)v.findViewById(R.id.edit_email);
        info=(EditText)v.findViewById(R.id.edit_info);
        Button update=(Button)v.findViewById(R.id.update);

        contact.setText(Contact);
        address.setText(Address);
        email.setText(Email);
        info.setText(Info);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        return v;
    }
    public void updateProfile(){
        final String new_contact=contact.getText().toString();
        final String new_address=address.getText().toString();
        final String new_email=email.getText().toString();
        final String new_info=info.getText().toString();
        String REGISTER_URL="http://vipul.hol.es/editprofile.php";

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                     //   progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(getActivity().getApplicationContext(), ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                    //    progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity().getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                //params.put("mnumber",bnumber);
                params.put("username",username);
                params.put("Contact",new_contact);
                params.put("Address",new_address);
                params.put("Email",new_email);
                params.put("Info",new_info);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
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
