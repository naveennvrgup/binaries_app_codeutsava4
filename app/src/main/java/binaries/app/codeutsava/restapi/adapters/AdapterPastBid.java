package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
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
        if (bids != null) {
            FarmerActiveBidListResponse bid = bids.get(position);

            holder.deadline.setText(bid.deadline);
            holder.foodgrain.setText(bid.type.type);
            holder.quantity.setText(bid.quantity);
            holder.transno.setText(bid.transno);

            if (bid.isActive) {
                holder.isActive.setText("Active");
            } else {
                holder.isActive.setTextColor(activity.getResources().getColor(android.R.color.holo_red_dark));
                holder.isActive.setText("Inactive");
            }
        }
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
