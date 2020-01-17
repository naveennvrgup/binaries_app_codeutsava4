package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.model.buyer.FarmerResponse;

public class AdapterFarmer extends RecyclerView.Adapter<AdapterFarmer.ViewHolder> {
    FragmentManager fragmentManager;
    List<FarmerResponse> ldata;
    BuyerFoodgrainResponse foodgrain;
    Activity activity;

    public AdapterFarmer(FragmentManager fragmentManager, List<FarmerResponse> ldata, Activity activity, BuyerFoodgrainResponse foodgrain) {
        this.fragmentManager = fragmentManager;
        this.ldata = ldata;
        this.activity = activity;
        this.foodgrain=foodgrain;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.recycler_farmer,parent,false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FarmerResponse data=ldata.get(position);

        holder.name.setText(data.farmer.name);
        holder.quanity.setText(String.valueOf(data.quantity));


        holder.chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder(data);
            }
        });

    }

    public void placeOrder(FarmerResponse data){

    }


    @Override
    public int getItemCount() {

        return ldata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView name,quanity;
        Button chooseBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar= itemView.findViewById(R.id.avatar);
            name= itemView.findViewById(R.id.farmer_name);
            quanity= itemView.findViewById(R.id.quantity);
            chooseBtn= itemView.findViewById(R.id.choose_button);
        }
    }
}
