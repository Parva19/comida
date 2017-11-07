package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 08-11-2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gbpec.comida.R;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<HistoryFields> historyList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView foodDetails,status,valid_date,reciever,valid_time;

        public MyViewHolder(View view) {
            super(view);
            foodDetails = (TextView) view.findViewById(R.id.foodDetails);
            status = (TextView) view.findViewById(R.id.status);
            valid_date=(TextView) view.findViewById(R.id.validDate);
            valid_time=(TextView) view.findViewById(R.id.validTime);
            reciever=(TextView) view.findViewById(R.id.reciever);

        }
    }

    public HistoryAdapter(List<HistoryFields> historyList){this.historyList=historyList;}


    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historystats, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, int position) {
       HistoryFields history = historyList.get(position);

        holder.foodDetails.setText(history.getFoodDetails());
        holder.status.setText(history.getStatus());
        holder.valid_date.setText(history.getValid_date());
        holder.valid_time.setText(history.getValid_time());
        holder.reciever.setText(history.getReciever());

    }

    @Override
    public int getItemCount() {
        return  historyList.size();
    }


}
