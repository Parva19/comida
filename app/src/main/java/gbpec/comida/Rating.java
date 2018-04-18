package gbpec.comida;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import gbpec.comida.donor_module.Donor_NavigationMainActivity;

public class Rating extends Activity {

    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    public String food_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_layout);
        Bundle bundle = getIntent().getExtras();
        food_id = bundle.getString("food_id");

        addListenerOnRatingBar();//
        addListenerOnButton();

    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {


            }
        });
    }

    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

makeRequest();
                Intent intent= new Intent(Rating.this, Donor_NavigationMainActivity.class);
                startActivity(intent);




            }

        });
    }
        void makeRequest() {

            String URL = "http://vipul.hol.es/rating.php?rating="+ratingBar.getRating()+"&food_id="+food_id;

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //   loading.dismiss();
                    //    Toast.makeText(getContext(),"thhis--"+response,Toast.LENGTH_LONG).show();
                    //WORKING CORRECTLY NAD GETTING DATA


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity().getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    });
            //  Toast.makeText(getContext(),"execute",Toast.LENGTH_LONG).show();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

    }
}
