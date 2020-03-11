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

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyerBidDetail;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import binaries.app.codeutsava.restapi.utils.Misc;

public class AdapterPastBid extends RecyclerView.Adapter<AdapterPastBid.ViewHolder> {
    Activity activity;
    List<FarmerActiveBidListResponse> bids = new ArrayList<>();

    public AdapterPastBid(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recyler_past_bid, parent, false);
        return new ViewHolder(view);
    }

    public void reflectFilterChange(List<FarmerActiveBidListResponse> responseList, String newFilter) {
        bids.clear();

        if (newFilter.equals(AppConstants.FILTER_ALL))
            bids.addAll(responseList);
        else
            for (FarmerActiveBidListResponse response : responseList) {
                if (newFilter.equals(AppConstants.FILTER_ACTIVE) && response.isActive)
                    bids.add(response);

                if (newFilter.equals(AppConstants.FILTER_INACTIVE) && !response.isActive)
                    bids.add(response);
            }

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (bids != null) {
            FarmerActiveBidListResponse bid = bids.get(position);

            holder.deadline.setText(Misc.getHTML("Deadline: " + bid.deadline));
            holder.foodgrain.setText(bid.type.type);
            holder.quantity.setText(Misc.getHTML("Qty: " + bid.quantity + "kgs."));
            holder.transno.setText(Misc.getHTML("TN: " + bid.transno));

            if (bid.isActive) {
                holder.isActive.setText("Active");
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(activity, ActivityBuyerBidDetail.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bid", bid);
                    intent.putExtras(bundle);

                    activity.startActivity(intent);
                    activity.finish();
                });

            } else {
                holder.isActive.setTextColor(activity.getResources().getColor(android.R.color.holo_red_dark));
                holder.isActive.setText("Inactive");
            }
        }

    }

    @Override
    public int getItemCount() {
        return bids == null ? 0 : bids.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodgrain, transno, quantity, deadline, isActive;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodgrain = itemView.findViewById(R.id.bpb_foodgrain);
            deadline = itemView.findViewById(R.id.bpb_deadline);
            quantity = itemView.findViewById(R.id.bpb_quantity);
            transno = itemView.findViewById(R.id.bpb_transno);
            img = itemView.findViewById(R.id.bpb_food_img);
            isActive = itemView.findViewById(R.id.bpb_isactive);
        }
    }
}
