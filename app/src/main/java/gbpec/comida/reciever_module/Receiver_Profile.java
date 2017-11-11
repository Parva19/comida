package gbpec.comida.reciever_module;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import gbpec.comida.R;
import gbpec.comida.SessionManager;
import gbpec.comida.Utility;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Receiver_Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Receiver_Profile#newInstance} factory method to
 * create an instance of this fragment.
 */


public class Receiver_Profile  extends Fragment {
    String username;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String Contact,Email,Address,Head,business_name,picUrl,type;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int REQUEST_CAMERA=1,SELECT_FILE=2;
    String userChoosenTask;

    private Receiver_Profile.OnFragmentInteractionListener mListener;
    TextView contact,address,email,head,changePassword,profile_name,Type;
    Button edit,profilePicEdit;
    ImageView profilePic;
    Bitmap image,bm;
    RelativeLayout cancelPic,donePic;
    LinearLayout buttonPicBlock;


    public Receiver_Profile() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Receiver_Profile.
     */
// TODO: Rename and change types and number of parameters

    public static Receiver_Profile newInstance(String param1, String param2) {
        Receiver_Profile fragment = new Receiver_Profile();
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

//        username=getArguments().getString("username");
        //      Toast.makeText(getActivity().getApplicationContext(),"profile- "+username,Toast.LENGTH_LONG).show();
        SessionManager session;
        session = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
//        username = user1.get(SessionManager.KEY_NAME);
        username = "0000000002";
        Toast.makeText(getActivity().getApplicationContext(), username, Toast.LENGTH_LONG).show();
        //getProfileData();

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_reciever_profile, container, false);


        // final ProgressDialog loading;
        // loading = ProgressDialog.show(getContext(),"Please wait...","Fetching...",false,false);

        String URL1="http://vipul.hol.es/getProfileNgo.php?contactno="+username;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL1, new Response.Listener<String>() {
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
                    Head=businessData.getString("head");
                    business_name=businessData.getString("bName");
                    picUrl=businessData.getString("picUrl");
                    type=businessData.getString("type");
                    //setting values
                    contact.setText(Contact);
                    address.setText(Address);
                    email.setText(Email);
                    head.setText(Head);
                    Type.setText(type);
                    profile_name.setText(business_name);
                    loadimage(picUrl);

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
                        Toast.makeText(getActivity().getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        //  Toast.makeText(getContext(),"execute",Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //  Toast.makeText(getContext(),"exe",Toast.LENGTH_LONG).show();
        requestQueue.add(stringRequest);


        contact=(TextView)v.findViewById(R.id.contact_ngo);
        address=(TextView)v.findViewById(R.id.address_ngo);
        email=(TextView)v.findViewById(R.id.email_ngo);
        head=(TextView)v.findViewById(R.id.head_ngo);
        profile_name=(TextView)v.findViewById(R.id.profile_name_ngo);
        profilePicEdit=(Button)v.findViewById(R.id.profile_pic_edit_ngo);
        profilePic=(ImageView) v.findViewById(R.id.profilepic_iv_ngo);
        buttonPicBlock=(LinearLayout)v.findViewById(R.id.buttons_pic_block_ngo);
        buttonPicBlock.setVisibility(View.GONE);
        cancelPic=(RelativeLayout)v.findViewById(R.id.cancel_pic_ngo);
        donePic=(RelativeLayout)v.findViewById(R.id.done_pic_ngo);
        Type=(TextView)v.findViewById(R.id.business_type_ngo1);

        cancelPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image!=null){
                    profilePic.setImageBitmap(image);
                    buttonPicBlock.setVisibility(View.GONE);
                }
            }
        });
        donePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url2="http://vipul.hol.es/comida/PicUploadNGO.php";
                final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                //Disimissing the progress dialog
                                loading.dismiss();
                                buttonPicBlock.setVisibility(View.GONE);
                                //Showing toast message of the response
                                Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                //Dismissing the progress dialog
                                loading.dismiss();

                                //Showing toast
                                Toast.makeText(getActivity(), "volley error" +
                                        "", Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //Converting Bitmap to String
                        String image = getStringImage(bm);
//
                        //Creating parameters
                        Map<String,String> params = new Hashtable<String, String>();

                        //Adding parameters
                        params.put("image", image);
                        params.put("contact", username);

                        //returning parameters
                        return params;
                    }
                };

                //Creating a Request Queue
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                //Adding request to the queue
                requestQueue.add(stringRequest);

            }
        });



        profilePicEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        edit=(Button)v.findViewById(R.id.edit_ngo);
        edit.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                Class fragmentClass=Edit_Profilr_Ngo.class;
                bundle.putString("username",username);
                bundle.putString("Contact",Contact);
                bundle.putString("Address",Address);
                bundle.putString("Email",Email);
                bundle.putString("Head",Head);
                Fragment fragment = null;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    fragment.setArguments(bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


            }
        });

        changePassword=(TextView)v.findViewById(R.id.change_password_ngo);
        changePassword.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                 Bundle bundle=new Bundle();
                Class fragmentClass=Change_Password_Ngo.class;
               /* bundle.putString("username",username);
                bundle.putString("Contact",Contact);
                bundle.putString("Address",Address);
                bundle.putString("Email",Email);
                bundle.putString("Info",Info);*/
                Fragment fragment = null;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    //  fragment.setArguments(bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


            }
        });




        return  v;
    }

    private void loadimage(final String picUrl) {
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    bm = BitmapFactory.decodeStream((InputStream) new URL(picUrl).getContent());
                    profilePic.post(new Runnable()
                    {
                        public void run()
                        {
                            if(bm !=null)
                            {
                                profilePic.setImageBitmap(bm);
                            }
                        }
                    });
                } catch (Exception e)
                {
                    // TODO: handle exception
                }
            }
        }).start();
    }

    private void getStringImage() {
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
        if (context instanceof Receiver_Profile.OnFragmentInteractionListener) {
            mListener = (Receiver_Profile.OnFragmentInteractionListener) context;
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

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        profilePic.setImageBitmap(bm);
//        Drawable dr = new BitmapDrawable(getResources(), bm);
//        profilePic.setBackground(dr);
        buttonPicBlock.setVisibility(View.VISIBLE);

    }
    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profilePic.setImageBitmap(bm);
//        Drawable dr = new BitmapDrawable(getResources(), bm);
//        profilePic.setBackground(dr);
        buttonPicBlock.setVisibility(View.VISIBLE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

}
