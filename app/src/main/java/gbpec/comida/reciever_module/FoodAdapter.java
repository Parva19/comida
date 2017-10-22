package gbpec.comida.reciever_module;

/**
 * Created by Parva Singhal on 20-10-2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gbpec.comida.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{
    private List<Food> foodList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView donor,contact, details;

        public MyViewHolder(View view) {
            super(view);
            donor = (TextView) view.findViewById(R.id.donor);
            contact = (TextView) view.findViewById(R.id.contact);
            details = (TextView) view.findViewById(R.id.details);
        }
    }


    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.donor.setText(food.getDonor());
        holder.contact.setText(food.getContact());
        holder.details.setText(food.getDetails());

    }

    @Override
    public int getItemCount() {
         return foodList.size();
    }
}
