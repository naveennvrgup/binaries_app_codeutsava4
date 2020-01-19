package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerPlaceBidPayload;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFarmerBids extends RecyclerView.Adapter<AdapterFarmerBids.ViewHolder> {
    Activity activity;
    List<FarmerActiveBidListResponse> bids;

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
        holder.quantity.setText("Quantity: " + bid.quantity);
        holder.deadline.setText("Deadline: " + bid.deadline);
        holder.transno.setText("TN: " + bid.transno);

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View view = LayoutInflater.from(activity).inflate(R.layout.farmer_place_bid_dialog, null);
            builder.setView(view);

            TextView foodgrain, quantity, desc, deadline;
            TextInputEditText farmer_price, farmer_desc;

            foodgrain = view.findViewById(R.id.fdialog_foodgrain);
            quantity = view.findViewById(R.id.fdialog_qauntity);
            desc = view.findViewById(R.id.fdialog_desc);
            deadline = view.findViewById(R.id.fdialog_deadline);

            foodgrain.setText(bid.type.type);
            quantity.setText("Quantity: " + bid.quantity);
            desc.setText(bid.description);
            deadline.setText("Deadline: " + bid.deadline);

            farmer_price = view.findViewById(R.id.farmer_bid_edittext);
            farmer_desc = view.findViewById(R.id.farmer_bid_description_edit_text);

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.setPositiveButton("Place bid", (dialog, which) -> {
                FarmerPlaceBidPayload payload = new FarmerPlaceBidPayload();
                payload.bidno = bid.id;
                payload.description = farmer_desc.getText().toString();
                payload.price = farmer_price.getText().toString();

                AppClient.getInstance().createService(APIServices.class)
                        .farmerPlaceBid(PreferenceManager.getDefaultSharedPreferences(activity).getString("token", AppConstants.TEMP_FARM_TOKEN),
                                payload)

                        .enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if (response.isSuccessful()) {
                                    dialog.dismiss();
                                    holder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
                                    holder.itemView.setEnabled(false);
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            });

            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodgrain, quantity, transno, deadline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodgrain = itemView.findViewById(R.id.fbids_foodgrain);
            quantity = itemView.findViewById(R.id.fbits_quantity);
            transno = itemView.findViewById(R.id.fbids_trans);
            deadline = itemView.findViewById(R.id.fbits_deadline);
        }
    }
}
