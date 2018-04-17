package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 17-04-2018.
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

import java.util.List;

import gbpec.comida.R;

public class DonorHistoryAdapter extends RecyclerView.Adapter<DonorHistoryAdapter.MyViewHolder>{
    private List<DonorHistoryData> historyList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fDetail, fRequestDate,fRequestTime,fstatus;
        public MyViewHolder(View itemView) {
            super(itemView);
            fDetail=(TextView)itemView.findViewById(R.id.fdetail);
            fRequestDate=(TextView)itemView.findViewById(R.id.fRequestDate);
            fRequestTime=(TextView)itemView.findViewById(R.id.fRequestTime);
            fstatus=(TextView)itemView.findViewById(R.id.fstatus);
        }
    }

    public DonorHistoryAdapter(List<DonorHistoryData> historyList){this.historyList=historyList;}

    @Override
    public DonorHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donor_history_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DonorHistoryAdapter.MyViewHolder holder, int position) {

        DonorHistoryData history=historyList.get(position);
        holder.fDetail.setText(history.getfDetails());
        holder.fRequestDate.setText(history.getfRequestDate());
        holder.fRequestTime.setText(history.getfRequestTime());
        holder.fstatus.setText(history.getfStatus());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }


}
