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

import java.util.List;

import gbpec.comida.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{
    private List<Food> foodList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView donor,contact, details,pickupTime,validDate,validTime,address1;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.donor.setText(food.getDonor());
        holder.contact.setText(food.getContact());
        holder.details.setText(food.getDetails());
        holder.pickupTime.setText(food.getPickupTime());
        holder.validDate.setText(food.getValidDate());
        holder.validTime.setText(food.getValidTime());
//        holder.address1.setText(food.getAddress());
    }

    @Override
    public int getItemCount() {
         return foodList.size();
    }
}
