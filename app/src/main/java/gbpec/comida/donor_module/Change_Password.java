package gbpec.comida.donor_module;

import android.content.Context;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gbpec.comida.R;
import gbpec.comida.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Change_Password.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Change_Password#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Change_Password extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
public  static EditText oldPassword,newPassword,confirmPassword;
    String user;
    Button submit;
    SessionManager session;
    private OnFragmentInteractionListener mListener;

    public Change_Password() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Change_Password.
     */
    // TODO: Rename and change types and number of parameters
    public static Change_Password newInstance(String param1, String param2) {
        Change_Password fragment = new Change_Password();
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
        session = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        user = user1.get(SessionManager.USER_CONTACT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_change__password, container, false);
        oldPassword=(EditText)v.findViewById(R.id.oldPassword);
        newPassword=(EditText)v.findViewById(R.id.newPassword);
        confirmPassword=(EditText)v.findViewById(R.id.confirmPassword);
        submit=(Button)v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newP,conP,oldP;
                newP=newPassword.getText().toString();
                conP=confirmPassword.getText().toString();
                oldP=oldPassword.getText().toString();

                  if(!validPassword(newP)){
                     newPassword.setError("Password should be 8-16 chars");
                      Toast.makeText(getActivity().getApplicationContext(),"Password should contain atleast one special, numeric and digit",Toast.LENGTH_LONG).show();
                      if(!newP.equals(conP)){
                          confirmPassword.setError("Password doesn't match");
                      }
                  }
                  else{
                      if(!newP.equals(conP)){
                          confirmPassword.setError("Password doesn't match");
                      }
                      else{
                          updatePassword(oldP,newP,conP);
                      }
                  }

            }
        });
       // AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //awesomeValidation.addValidation(getActivity().getApplicationContext(), R.id.newPassword, "", "sfc");
        //awesomeValidation.validate();

        return v;
    }
    boolean validPassword(String newP){
        String regEx="^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,16}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher=pattern.matcher(newP);
        return matcher.matches();
    }
    public void updatePassword(final String oldP, final String newP, String conP){

        String REGISTER_URL="http://vipul.hol.es/ChangePassword.php";

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        // progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(getActivity().getApplicationContext(), ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity().getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
params.put("user",user);
                params.put("oldPassword",oldP);
                params.put("newPassword",newP);
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
