package gbpec.comida.donor_module;

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
        public TextView food_details,status;

        public MyViewHolder(View view) {
            super(view);
            food_details = (TextView) view.findViewById(R.id.food_details);
            status = (TextView) view.findViewById(R.id.status);

        }
    }


    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donor_food_status, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.food_details.setText(food.getFood_details());
        holder.status.setText(food.getStatus());
    }

    @Override
    public int getItemCount() {
         return foodList.size();
    }
}
