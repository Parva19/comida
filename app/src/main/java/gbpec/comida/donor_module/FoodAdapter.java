package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 20-10-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import gbpec.comida.Ngo_profile;
import gbpec.comida.R;
import gbpec.comida.Rating;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{
    private final Context context;
    private List<Food> foodList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView food_details,status,time,date;
        public Button delivery,ngo;

        public MyViewHolder(View view) {
            super(view);
            food_details = (TextView) view.findViewById(R.id.food_details);
            status = (TextView) view.findViewById(R.id.food_status);
            time = (TextView) view.findViewById(R.id.validTime);
            date = (TextView) view.findViewById(R.id.validDate);
            delivery=(Button)view.findViewById(R.id.confirm_delivery);
            ngo=(Button)view.findViewById(R.id.ngo_details);


        }
    }


    public FoodAdapter(List<Food> foodList,Context context) {
        this.foodList = foodList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donor_food_status, parent, false);



        return new MyViewHolder(itemView);
    }
    //

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodList.get(position);
        if("Accepted".equals(food.getStatus()))
        {
            holder.delivery.setVisibility(View.VISIBLE);
            holder.ngo.setVisibility(View.VISIBLE);
        }

       final String food_id;
        food_id=food.getId();
        holder.food_details.setText(food.getFood_details());
        holder.status.setText(food.getStatus());
        holder.date.setText(food.getDate());
        holder.time.setText(food.getTime());

        holder.delivery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent= new Intent(context, Rating.class);
                intent.putExtra("food_id",food_id);
                context.startActivity(intent);

            }

        });
        holder.ngo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent= new Intent(context, Ngo_profile.class);
                intent.putExtra("food_id",food_id);
                context.startActivity(intent);

            }

        });

    }

    @Override
    public int getItemCount() {
         return foodList.size();
    }
}
