package gbpec.comida;

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

public class Education extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
LinearLayout book_view,layout,layout1;
EditText quantity,book_desc,street,city,pincode,landmark,available_date,available_time,quantities[]=new EditText[100],desc[]=new EditText[100];
Spinner book_type;
Spinner[] types=new Spinner[100];
TextView booknum;
Button submit,more_items;
String type[]=new String[100],bstreet,bpin,blandmark,bcity,bdate,btime;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        book_view=(LinearLayout)findViewById(R.id.book_view);
        book_view.setOrientation(LinearLayout.VERTICAL);

        book_type=(Spinner)findViewById(R.id.book_type);
        quantity=(EditText)findViewById(R.id.quantity);
        book_desc=(EditText)findViewById(R.id.book_desc);
        street=(EditText)findViewById(R.id.street);
        city=(EditText)findViewById(R.id.city);
        pincode=(EditText)findViewById(R.id.pincode);
        landmark=(EditText)findViewById(R.id.landmark);
        available_date=(EditText)findViewById(R.id.available_date);
        available_time=(EditText)findViewById(R.id.available_time);

        booknum=(TextView)findViewById(R.id.booknum);

        submit=(Button)findViewById(R.id.submit);
        more_items=(Button)findViewById(R.id.more_items);

        //SPINNER CODE
        book_type.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.book_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        book_type.setAdapter(adapter);
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
                new DatePickerDialog(Education.this, date, myCalendar
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
                mTimePicker = new TimePickerDialog(Education.this, new TimePickerDialog.OnTimeSetListener() {
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


        types[k] = new Spinner(Education.this);
        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams( 190, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
        params1.setMargins(15,0,0,0);
        types[k].setLayoutParams(params1);
        types[k].setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.book_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types[k].setAdapter(adapter);

        quantities[k] = new EditText(Education.this);
        quantities[k].setHint("Quantity");
        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
        params2.setMargins(15,0,10,0);
        // quantities[k].setWidth(100);
        quantities[k].setLayoutParams(params2);
        // quantities[k].setLayoutParams(params);

        desc[k] = new EditText(Education.this);
        desc[k].setHint("Books Description");
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
        book_view.addView(layout1);

        k++;
    }

    public void submit(View v){
       /* String typeo ="";
        for(int i=0;i<=k;i++){
            typeo=typeo + type[i]+" ";
        }
        Toast.makeText(Education.this,typeo,Toast.LENGTH_LONG).show();*/

        final StringBuilder sb=new StringBuilder(1000);
        sb.append("1) ");
        sb.append(type[0]+"-");
        sb.append(quantity.getText().toString()+"::");
        sb.append(book_desc.getText().toString()+"&");
        for(int i=0;i<k;i++){
            sb.append(i+2+") ");
            sb.append(type[i+1]+"-");
            sb.append(quantities[i].getText().toString()+"::");
            sb.append(desc[i].getText().toString()+"&");
        }

        bstreet=street.getText().toString();
        bcity=city.getText().toString();
        bpin=pincode.getText().toString();
        blandmark=landmark.getText().toString();
        bdate=available_date.getText().toString();
        btime=available_time.getText().toString();


        String REGISTER_URL="http://vipul.hol.es/books.php";

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        // progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(Education.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Education.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                //params.put("mnumber",bnumber);
               // params.put("fuser",user);
               // params.put("fname",user_name);
                params.put("bDetails", String.valueOf(sb));
                params.put("bstreet", bstreet);//valid date
                params.put("bcity",bcity);//valid time
                params.put("blandmark",blandmark);
                params.put("bpin",bpin);
                params.put("bdate",bdate);
                params.put("btime",btime);
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
        RequestQueue requestQueue = Volley.newRequestQueue(Education.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        type[k]=item;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
