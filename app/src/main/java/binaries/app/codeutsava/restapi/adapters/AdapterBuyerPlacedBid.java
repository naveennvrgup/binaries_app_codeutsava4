package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyerOrders;
import binaries.app.codeutsava.restapi.model.buyer.PlaceBidResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterBuyerPlacedBid extends RecyclerView.Adapter<AdapterBuyerPlacedBid.ViewHolder> {
    Activity activity;
    List<PlaceBidResponse> pbids;

    public AdapterBuyerPlacedBid(Activity activity, List<PlaceBidResponse> pbids) {
        this.activity = activity;
        this.pbids = pbids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_buyer_placed_bid, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaceBidResponse pbid = pbids.get(position);

        holder.description.setText(pbid.description);
        holder.farmer.setText(pbid.farmer);
        holder.price.setText(pbid.price);
        holder.bid.setText(pbid.bid);

        holder.approveorderbtn.setOnClickListener(v -> AppClient.getInstance().createService(APIServices.class)
                .approveBid(PreferenceManager.getDefaultSharedPreferences(activity).getString("token", AppConstants.TEMP_FARM_TOKEN),
                        pbid.bid)

                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(activity, "sucesss", Toast.LENGTH_LONG).show();
                            activity.startActivity(new Intent(
                                    activity,
                                    ActivityBuyerOrders.class
                            ));
                            activity.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));
    }

    @Override
    public int getItemCount() {
        return pbids.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bid, farmer, price, description;
        Button approveorderbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bid = itemView.findViewById(R.id.bpbid_bid);
            farmer = itemView.findViewById(R.id.bpbid_farmer);
            price = itemView.findViewById(R.id.bpbid_price);
            description = itemView.findViewById(R.id.bpbid_description);
            approveorderbtn = itemView.findViewById(R.id.bpbid_approve_pbid_btn);
        }
    }
}
