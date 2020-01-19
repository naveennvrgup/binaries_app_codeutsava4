package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyerBidDetail;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;

public class AdapterPastBid extends RecyclerView.Adapter<AdapterPastBid.ViewHolder> {
    Activity activity;
    List<FarmerActiveBidListResponse> bids;

    public AdapterPastBid(Activity activity, List<FarmerActiveBidListResponse> bids) {
        this.activity = activity;
        this.bids = bids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recyler_past_bid, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FarmerActiveBidListResponse bid = bids.get(position);

        holder.deadline.setText(bid.deadline);
        holder.foodgrain.setText(bid.type.type);
        holder.quantity.setText(bid.quantity);
        holder.transno.setText(bid.transno);

        if(bid.isActive=="false"){
            holder.itemView.setBackgroundColor(
                    activity.getResources().getColor(R.color.colorAccent)
            );
            return;
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity,ActivityBuyerBidDetail.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("bid", bid);
            intent.putExtras(bundle);

            activity.getBaseContext().startActivity(intent);
            activity.finish();
        });
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView foodgrain, transno, quantity, deadline;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodgrain = itemView.findViewById(R.id.bpb_foodgrain);
            deadline = itemView.findViewById(R.id.bpb_deadline);
            quantity = itemView.findViewById(R.id.bpb_quantity);
            transno = itemView.findViewById(R.id.bpb_transno);
            img = itemView.findViewById(R.id.bpb_food_img);

        }
    }
}
