package gbpec.comida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FoodItems extends AppCompatActivity {
private LinearLayout layout,layout2;
    private EditText item,quantity,newaddress;
    public static EditText items[] = new EditText[100];
    public static EditText quantities[] = new EditText[100];
    public static TextView serial[]=new TextView[100];
    private int k=0,text=2;
DatePicker simpleDatePicker;
    private int pickt1_h,pickt1_m,pickt2_h,pickt2_m,validt_h,validt_m,address_check;
    private String address,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);
        //layout=(LinearLayout)findViewById(R.id.itemview);

        Intent i=new Intent();
        i=getIntent();
        user=i.getStringExtra("user");
        Toast.makeText(getApplicationContext(), user, Toast.LENGTH_LONG).show();



        layout2=(LinearLayout)findViewById(R.id.view2);
        layout2.setOrientation(LinearLayout.VERTICAL);
        item=(EditText)findViewById(R.id.itemname);
        quantity=(EditText)findViewById(R.id.quantity);
//pick up time values
        TimePicker simpleTimePicker1=(TimePicker) findViewById(R.id.simpleTimePicker1);
        simpleTimePicker1.setIs24HourView(true);

        TimePicker simpleTimePicker2=(TimePicker) findViewById(R.id.simpleTimePicker2);
        simpleTimePicker2.setIs24HourView(true);

        simpleTimePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                pickt1_h=hourOfDay;
                pickt1_m=minute;
            }
        });

        simpleTimePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                 pickt2_h=hourOfDay;
                pickt2_m=minute;
            }
        });
//pick up time end

        //Valid upto details
         simpleDatePicker=(DatePicker)findViewById(R.id.simpleDatePicker);
        TimePicker simpleTimePicker3=(TimePicker) findViewById(R.id.simpleTimePicker3);
        simpleTimePicker3.setIs24HourView(true);
        simpleTimePicker3.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                validt_h=hourOfDay;
                validt_m=minute;
            }
        });
//Valid upto details time limit end

        newaddress = (EditText) findViewById(R.id.address_new);
    }
//Radio button for address
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.business_address:
                if (checked)
                    // Pirates are the best
                    newaddress.setVisibility(View.GONE);
                address="Business Address";
                address_check=0;
                    break;
            case R.id.new_address:
                if (checked) {
                    // Ninjas rule
                    address = "New address";
                    newaddress.setVisibility(View.VISIBLE);
                    address_check=1;
                    break;
                }
        }
    }
    //Radio button end
    public void addmore(View v){
        layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,       ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,0,10,0);
        layout.setLayoutParams(params);

        items[k] = new EditText(FoodItems.this);
        items[k].setHint("Food item");
        items[k].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
       // items[k].setLayoutParams(params);

        quantities[k] = new EditText(FoodItems.this);
        quantities[k].setHint("Quantity");
        quantities[k].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
       // quantities[k].setLayoutParams(params);

        String a=Integer.toString(text);
        TextView t=new TextView(FoodItems.this);
       // serial[k].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
       t.setId(k);
       t.setTextSize(20);
       t.setText(a+") ");
       LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,       LinearLayout.LayoutParams.WRAP_CONTENT);
       params2.setMargins(10,0,10,0);
       t.setLayoutParams(params2);

        text=Integer.parseInt(a);
        text++;

        layout.addView(t);
        layout.addView(items[k]);
        layout.addView(quantities[k]);
        //layout.removeAllViews();
        layout2.addView(layout);
        k++;
    }
    public void submit(View v){
//converting food items in single string

        final StringBuilder sb=new StringBuilder(1000);
        sb.append("1) ");
        sb.append(item.getText().toString()+"-");
        sb.append(quantity.getText().toString()+"&");
        for(int i=0;i<k;i++){
            sb.append(i+2+") ");
            sb.append(items[i].getText().toString()+"-");
            sb.append(quantities[i].getText().toString()+"&");
        }

        //end string builder
        //valid dya and vaid_month will be the upto  date for collection of food
        final int valid_day=simpleDatePicker.getDayOfMonth();
        int valid_mo=simpleDatePicker.getMonth();
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        final String valid_month=MONTHS[valid_mo];
        //valid day end

        //checking wether it is new address or not
        if(address_check==1){
            address=newaddress.getText().toString();
        }
        //

        String REGISTER_URL="http://vipul.hol.es/fooditems.php";

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                       // progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(FoodItems.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                      //  progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(FoodItems.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                //params.put("mnumber",bnumber);
                params.put("fuser",user);
                params.put("fDetails", String.valueOf(sb));
                params.put("fRequestDate", valid_month+"/"+valid_day);//valid date
                params.put("fRequestTime",validt_h+":"+validt_m);//valid time
                params.put("fPickupTime",pickt1_h+":"+pickt1_m+"\nto\n"+pickt2_h+":"+pickt2_m);
                params.put("fPickupAddress",address);
                params.put("address_check",Integer.toString(address_check));
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
        RequestQueue requestQueue = Volley.newRequestQueue(FoodItems.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
       // Toast.makeText(getApplicationContext(), "Address::"+address+Integer.toString(address_check), Toast.LENGTH_LONG).show();


  /*      Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Pick up time::"+pickt1_h+":"+pickt1_m+"\nto\n"+pickt2_h+":"+pickt2_m, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Valid upto::"+valid_month+"/"+valid_day+"--"+validt_h+":"+validt_m, Toast.LENGTH_LONG).show();

*/
    }
}
