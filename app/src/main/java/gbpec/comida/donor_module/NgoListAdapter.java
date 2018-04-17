package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 13-04-2018.
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

public class NgoListAdapter extends RecyclerView.Adapter<NgoListAdapter.MyViewHolder>{
    private List<NgoData> ngoList;
    Context mcontext;
    String name[]=new String[100];
    String contact[]=new String[100];
    String type[]=new String[100];
    String head[]=new String[100];
    String email[]=new String[100];
    String address[]=new String[100];
    String pic[]=new String[200];

    int ngoCount=0;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView ngoName, ngoContact;
        public RelativeLayout ngo_item;
        public MyViewHolder(View view) {
            super(view);
            ngoName=(TextView)view.findViewById(R.id.ngo_name);
            ngoContact=(TextView)view.findViewById(R.id.ngo_contact);
            ngo_item=(RelativeLayout)view.findViewById(R.id.ngo_item);
        }
    }

    public NgoListAdapter(List<NgoData> ngoList,Context mcontext){this.ngoList=ngoList;this.mcontext=mcontext;}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ngo_list, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                put your code here for action on accept button
                // int position = v.getAd
//
                Context context = v.getContext();
              // Toast.makeText(context,ngoN)
//                Intent intent = new Intent(context, SingleItemView.class);
//                intent.putExtra("com.package.sample.ITEM_DATA", foodList.get(position));
//                context.startActivity(intent);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NgoListAdapter.MyViewHolder holder, final int position) {

        NgoData ngoData=ngoList.get(position);
        holder.ngoName.setText(ngoData.getNgoName());
        holder.ngoContact.setText(ngoData.getNgoContact());

        name[ngoCount]=ngoData.getNgoName();
        contact[ngoCount]=ngoData.getNgoContact();
        type[ngoCount]=ngoData.getType();
        head[ngoCount]=ngoData.getHead();
        email[ngoCount]=ngoData.getEmail();
        address[ngoCount]=ngoData.getAddres();
        pic[ngoCount]=ngoData.getPic();
        ngoCount++;
        holder.ngo_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Toast.makeText(mcontext, name[position]+contact[position]+type[position], Toast.LENGTH_SHORT).show();
                  Intent i=new Intent(mcontext,NgoInfo.class);
                  i.putExtra("name",name[position]);
                  i.putExtra("contact",contact[position]);
                  i.putExtra("type",type[position]);
                  i.putExtra("head",head[position]);
                  i.putExtra("email",email[position]);
                  i.putExtra("address",address[position]);
                  i.putExtra("pic",pic[position]);
                  mcontext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ngoList.size();
    }


}
