package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;

public class AdapterFarmerBids extends RecyclerView.Adapter<AdapterFarmerBids.ViewHolder> {
    Activity activity;
    List<FarmerActiveBidListResponse>  bids;

    public AdapterFarmerBids(Activity activity, List<FarmerActiveBidListResponse> bids) {
        this.activity = activity;
        this.bids = bids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_farmer_bids_row, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FarmerActiveBidListResponse bid = bids.get(position);

        holder.foodgrain.setText(bid.type.type);
        holder.quantity.setText(bid.quantity);
        holder.deadline.setText(bid.deadline);
        holder.transno.setText(bid.transno);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View view = LayoutInflater.from(activity).inflate(R.layout.farmer_place_bid_dialog,null);
                builder.setView(view);

                TextView foodgrain,quantity,desc,deadline;
                TextInputEditText price;

                foodgrain = view.findViewById(R.id.fdialog_foodgrain);
                quantity= view.findViewById(R.id.fdialog_qauntity);
                desc= view.findViewById(R.id.fdialog_desc);
                deadline= view.findViewById(R.id.fdialog_deadline);
                price= view.findViewById(R.id.farmer_bid_edittext);

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppClient.getInstance().createService(APIServices.class)
                                .
                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }

    class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView foodgrain, quantity, transno, deadline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodgrain = itemView.findViewById(R.id.fbids_foodgrain);
            quantity= itemView.findViewById(R.id.fbits_quantity);
            transno= itemView.findViewById(R.id.fbids_trans);
            deadline= itemView.findViewById(R.id.fbits_deadline);
        }
    }
}
