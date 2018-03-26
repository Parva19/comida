package gbpec.comida;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static gbpec.comida.R.drawable.edittext_border;


public class Clothes extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    LinearLayout cloth_view,layout,layout1;
    EditText quantity,cloth_desc,street,pincode,landmark,available_date,available_time,quantities[]=new EditText[100],desc[]=new EditText[100];
    Spinner cloth_type;
    AutoCompleteTextView city;
    Spinner[] types=new Spinner[100];
    TextView clothnum;
    Button submit,more_items;
    String type[]=new String[100],bstate,bdate,btime;
    Spinner t_state;
    int k=0;
    SessionManager session;
    String user,user_name,latitude,longitude;
    String requestDate,requestTime;
    final StringBuilder sb = new StringBuilder(1000);
    final StringBuilder add = new StringBuilder(1000);
    String pincode_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        user = user1.get(SessionManager.USER_CONTACT);
        user_name=user1.get(SessionManager.USER_NAME);

        cloth_view=(LinearLayout)findViewById(R.id.cloth_view);
        cloth_view.setOrientation(LinearLayout.VERTICAL);

        cloth_type=(Spinner)findViewById(R.id.cloth_type);
        quantity=(EditText)findViewById(R.id.quantity);
        cloth_desc=(EditText)findViewById(R.id.cloth_desc);
        street=(EditText)findViewById(R.id.street);
        city=(AutoCompleteTextView) findViewById(R.id.city);
        pincode=(EditText)findViewById(R.id.pincode);
        available_date=(EditText)findViewById(R.id.available_date);
        available_time=(EditText)findViewById(R.id.available_time);
        t_state=(Spinner)findViewById(R.id.spin_state);

        t_state.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.state_arrays, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        t_state.setAdapter(adapter2);

        clothnum=(TextView)findViewById(R.id.clothnum);

        submit=(Button)findViewById(R.id.submit_clothes);
        submit.setOnClickListener(this);

        more_items=(Button)findViewById(R.id.more_items);

        //SPINNER CODE
        cloth_type.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cloth_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cloth_type.setAdapter(adapter);
        //SPINNER CODE END

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                available_date.setText(sdf.format(myCalendar.getTime()));
            }


        };

        available_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Clothes.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        available_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Clothes.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm;
                        int hour;
                        if(selectedHour>12){
                            am_pm="PM";
                            hour=selectedHour-12;
                        }
                        else{
                            am_pm="AM";
                            hour=selectedHour;
                        }
                        available_time.setText( hour + ":" + selectedMinute+" - "+am_pm);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



    }


    public void more(View v){

        layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,       ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        layout.setLayoutParams(params);

        layout1=new LinearLayout(this);
        layout1.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,       ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        layout1.setLayoutParams(params4);


        types[k] = new Spinner(Clothes.this);
        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams( 190, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
        params1.setMargins(15,0,0,0);
        types[k].setLayoutParams(params1);
        types[k].setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cloth_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types[k].setAdapter(adapter);

        quantities[k] = new EditText(Clothes.this);
        quantities[k].setHint("Quantity");
        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
        params2.setMargins(15,0,10,0);
        // quantities[k].setWidth(100);
        quantities[k].setLayoutParams(params2);
        // quantities[k].setLayoutParams(params);

        desc[k] = new EditText(Clothes.this);
        desc[k].setHint("Clothes Description");
        desc[k].setMinLines(4);
        desc[k].setMaxLines(6);
        desc[k].setGravity(Gravity.LEFT | Gravity.TOP);
        desc[k].setBackgroundResource(edittext_border);
        LinearLayout.LayoutParams params3=new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
        params2.setMargins(2,2,2,2);
        desc[k].setLayoutParams(params3);


        layout.addView(types[k]);
        layout.addView(quantities[k]);
        layout1.addView(layout);
        layout1.addView(desc[k]);
        cloth_view.addView(layout1);

        k++;
    }

    public void submit() {

       /* String typeo ="";
        for(int i=0;i<=k;i++){
            typeo=typeo + type[i]+" ";
        }
        Toast.makeText(Education.this,typeo,Toast.LENGTH_LONG).show();*/


        sb.append("1) ");
        sb.append(type[0] + "-");
        sb.append(quantity.getText().toString() + "::");
        sb.append(cloth_desc.getText().toString() + "&");
        for (int i = 0; i < k; i++) {
            sb.append(i + 2 + ") ");
            sb.append(type[i + 1] + "-");
            sb.append(quantities[i].getText().toString() + "::");
            sb.append(desc[i].getText().toString() + "&");
        }


        add.append(street.getText().toString());
        add.append("#");
        add.append(city.getText().toString());
        add.append("#");
        add.append(bstate);
        add.append("#");
        add.append(pincode.getText().toString());


        bdate = available_date.getText().toString();
        btime = available_time.getText().toString();

        pincode_text=pincode.getText().toString();
        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cMonth = calander.get(Calendar.MONTH) + 1;
        int cYear = calander.get(Calendar.YEAR);
        StringBuilder date = new StringBuilder();
        date.append(cDay);
        date.append("-");
        date.append(cMonth);
        date.append("-");
        date.append(cYear);
        requestDate = String.valueOf(date);
        int cHour = calander.get(Calendar.HOUR);
        int cMinute = calander.get(Calendar.MINUTE);
        StringBuilder time = new StringBuilder();
        if (cHour > 12) {
            time.append(cHour - 12);
            time.append(":");
            time.append(cMinute);
            time.append(" ");
            time.append("PM");
        } else {
            time.append(cHour);
            time.append(":");
            time.append(cMinute);
            time.append(" ");
            time.append("AM");
        }
        requestTime = String.valueOf(time);
        Toast.makeText(getApplicationContext(),"submit processed", Toast.LENGTH_SHORT).show();
        getlocationAttributes();
    }


        void makeRequest(){



        String REGISTER_URL="http://vipul.hol.es/clothes.php";

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        // progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(Clothes.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Clothes.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                 params.put("bContact",user);
                 params.put("bDonor",user_name);
                params.put("bDetails", String.valueOf(sb));
                params.put("bAddress",  String.valueOf(add));
                params.put("blatitude",  latitude);
                params.put("blongitude", longitude);
                params.put("bRequestdate",  requestDate);
                params.put("bRequestTime",  requestTime);
                params.put("bdate",bdate);
                params.put("btime",btime);
                params.put("bstatus","waiting to be accepted");

                //params.put("address_check",Integer.toString(address_check));
               /* params.put("bAddress",address);
                params.put("bHead",head_name);
                params.put("bPassword",password);
                // params.put("confirm_password",confirm_password);
                params.put("bAdditionalInfo",additional);
                params.put("btype","business");*/
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Clothes.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.cloth_type) {
            String item = adapterView.getItemAtPosition(i).toString();
            type[k] = item;
        }
        else if(adapterView.getId()==R.id.spin_state){
           bstate=adapterView.getItemAtPosition(i).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void getlocationAttributes() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://maps.googleapis.com/maps/api/geocode/json?address="+pincode_text,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("results");

                            //now looping through all the elements of the json array

                            latitude=jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
                           longitude=jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();

                            Toast.makeText(getApplicationContext(),latitude+"and"+longitude, Toast.LENGTH_SHORT).show();

                            makeRequest();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.submit_clothes){
           Toast.makeText(getApplicationContext(),"submit clicked", Toast.LENGTH_SHORT).show();
            submit();
       }
    }
}

