package gbpec.comida.reciever_module;

/**
 * Created by Parva Singhal on 20-10-2017.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gbpec.comida.FoodItems;
import gbpec.comida.R;
import gbpec.comida.SessionManager;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{
    private List<Food> foodList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView donor,contact, details,pickupTime,validDate,validTime,address1,buttontext;
        RelativeLayout accept_button;

        public MyViewHolder(View view) {
            super(view);
            donor = (TextView) view.findViewById(R.id.donor);
            contact = (TextView) view.findViewById(R.id.contact);
            details = (TextView) view.findViewById(R.id.details);
            pickupTime = (TextView) view.findViewById(R.id.pickupTime);
            validDate = (TextView) view.findViewById(R.id.validDate);
            validTime = (TextView) view.findViewById(R.id.validTime);
            accept_button=(RelativeLayout)view.findViewById(R.id.accept_button);
            address1=(TextView)view.findViewById(R.id.address);
            buttontext=(TextView)view.findViewById(R.id.button_text);
        }
    }


    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                put your code here for action on accept button
//                int position = getAdapterPosition();
//
//                Context context = v.getContext();
//                Intent intent = new Intent(context, SingleItemView.class);
//                intent.putExtra("com.package.sample.ITEM_DATA", foodList.get(position));
//                context.startActivity(intent);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       final Food food = foodList.get(position);
        holder.donor.setText(food.getDonor());
        holder.contact.setText(food.getContact());
        holder.details.setText(food.getDetails());
        holder.pickupTime.setText(food.getPickupTime());
        holder.validDate.setText(food.getValidDate());
        holder.validTime.setText(food.getValidTime());
        holder.accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id=food.getFood_id();
                SessionManager session = new SessionManager(holder.accept_button.getContext());
                HashMap<String, String> user1 = session.getUserDetails();
                final String user_name = user1.get(SessionManager.USER_NAME);
                String REGISTER_URL = "http://vipul.hol.es/acceptfood.php";

                // Creating string request with post method.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {


                                Toast.makeText(holder.accept_button.getContext(), ServerResponse, Toast.LENGTH_LONG).show();
                                holder.buttontext.setText("Done");
                                holder.accept_button.setClickable(false);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {


                                Toast.makeText(holder.accept_button.getContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {

                        // Creating Map String Params.
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("DonorContact",food.getContact());
                        params.put("fId",id);
                        params.put("fRecieverId", "10");
                        params.put("fDetails", food.getDetails());
                        params.put("fReciever",user_name);
                        params.put("fStatus","Accepted");
                        return params;
                    }

                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(holder.accept_button.getContext());

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);
            }
        });
//        holder.address1.setText(food.getAddress());
    }

    @Override
    public int getItemCount() {
         return foodList.size();
    }
}
