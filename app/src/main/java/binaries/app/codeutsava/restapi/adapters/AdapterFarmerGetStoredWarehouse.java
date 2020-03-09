package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentPotentialBuyerList;
import binaries.app.codeutsava.restapi.model.farmer.FarmerStorageTransactionResponse;

public class AdapterFarmerGetStoredWarehouse extends RecyclerView.Adapter<AdapterFarmerGetStoredWarehouse.ViewHolder> {
    private List<FarmerStorageTransactionResponse> produces;
    private Activity activity;
    private FragmentManager fragManager;
    private boolean waste = false;
    private String[] names = {"Manure Waste", "E-Waste", "Other Waste"};


    public void setFragManager(FragmentManager fragManager) {
        this.fragManager = fragManager;
    }

    public AdapterFarmerGetStoredWarehouse(List<FarmerStorageTransactionResponse> produces, Activity activity) {
        this.produces = produces;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterFarmerGetStoredWarehouse.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.farmer_stored_warehouse_recyclerview, null);

        return new AdapterFarmerGetStoredWarehouse.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFarmerGetStoredWarehouse.ViewHolder holder, int position) {
        if (produces != null) {
            FarmerStorageTransactionResponse produce = produces.get(position);

            holder.whName.setText(produce.whName);
            holder.whQuantity.setText(Html.fromHtml("<b>Qty: </b>" + produce.quantity + "kgs."));
            holder.whFoodgrain.setText(produce.foodgrain);
            holder.whDate.setText(Html.fromHtml("<b>Date: </b>" + produce.date));
            holder.whCost.setText(Html.fromHtml("<b>₹: </b>" + produce.cost));

            if (waste) {
                holder.image.setVisibility(View.GONE);
                holder.whName.setText(names[position % 3]);
                holder.whFoodgrain.setVisibility(View.GONE);
                holder.whDate.setVisibility(View.GONE);
                holder.whCost.setVisibility(View.GONE);
                holder.whDeadline.setVisibility(View.GONE);

                holder.itemView.setOnClickListener(view -> {
                    FragmentPotentialBuyerList buyerSHGList = new FragmentPotentialBuyerList();

                    buyerSHGList.setIsWaste(waste);
                    buyerSHGList.show(fragManager, "SHGList");
                });
            }

            long diffDays = getDateDifference(produce.date);

            if (diffDays + 60 >= produce.fgDeadline) {
                Log.v("case1", String.valueOf(diffDays));

                holder.whDeadline.setText("Grain may perish in " + (produce.fgDeadline - diffDays) + " days");
                holder.whDeadline.setTextColor(activity.getResources().getColor(R.color.colorRed));


                holder.itemView.setOnClickListener(v -> {
                    FarmerStorageTransactionResponse currProduceData = produces.get(position);

                    Bundle args = new Bundle();
                    args.putSerializable("foodgrain", currProduceData.foodgrain);

                    FragmentPotentialBuyerList buyerList = new FragmentPotentialBuyerList();
                    buyerList.setArguments(args);
                    buyerList.show(fragManager, "....");
                });

            } else {
                Log.v("case2", "case2");
                holder.whDeadline.setVisibility(View.GONE);
            }

        } else {
            Log.v("case2", "case2");
            holder.whDeadline.setVisibility(View.GONE);
        }
    }

    public void setIsWaste(boolean waste) {
        this.waste = waste;
    }

    private long getDateDifference(String startDate) {
        Date endDate = Calendar.getInstance().getTime();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            return TimeUnit.MILLISECONDS.toDays(endDate.getTime() - dateFormat.parse(startDate).getTime());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return produces == null ? 0 : produces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView whName, whQuantity, whCost, whDate, whFoodgrain, whDeadline;
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            whName = itemView.findViewById(R.id.fstoredWareHouseName);
            whCost = itemView.findViewById(R.id.fstoredWarehouseCost);
            whFoodgrain = itemView.findViewById(R.id.fstoredWarehouseFoodgrain);
            whDate = itemView.findViewById(R.id.fstoredWarehouseDate);
            whQuantity = itemView.findViewById(R.id.fstoredWarehouseQuantity);
            whDeadline = itemView.findViewById(R.id.fstoredWareHouseDeadline);
            image = itemView.findViewById(R.id.farm_store_ware_icon);
        }
    }
}
