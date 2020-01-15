package binaries.app.codeutsava.restapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;

public class AdapterActiveBid extends RecyclerView.Adapter<AdapterActiveBid.ViewHolder> {
    List<FarmerActiveBidListResponse> activeBids;
    Context context;

    public AdapterActiveBid(List<FarmerActiveBidListResponse> activeBidListResponses, Context context) {
        this.activeBids= activeBidListResponses;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterActiveBid.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.farmer_active_bid_row, parent, false);

        return new AdapterActiveBid.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActiveBid.ViewHolder holder, int position) {
        FarmerActiveBidListResponse bid=activeBids.get(position);

        holder.textViewBuyerDetails.setText(bid.buyer.name+"("+bid.buyer.city+","+bid.buyer.state+")");
        holder.textViewBidSummary.setText(bid.quantity+"kg of "+bid.type.type+" for Rs."+bid.nbids);
        holder.textViewBuyerContact.setText("Contact: "+bid.buyer.contact);
        holder.textViewDeadline.setText("Deadline: "+bid.deadline);
    }

    @Override
    public int getItemCount() {
        return activeBids.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
    TextView textViewBuyerDetails, textViewBidSummary, textViewBuyerContact, textViewDeadline;
    ImageView imageViewFoodGrain;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewBuyerDetails=itemView.findViewById(R.id.ABBuyerDetails);
            textViewBidSummary=itemView.findViewById(R.id.ABBidSummary);
            textViewBuyerContact=itemView.findViewById(R.id.ABBuyerContact);
            textViewDeadline=itemView.findViewById(R.id.ABDeadline);
            imageViewFoodGrain = itemView.findViewById(R.id.ABfoodgrainImg);
        }
    }
}
