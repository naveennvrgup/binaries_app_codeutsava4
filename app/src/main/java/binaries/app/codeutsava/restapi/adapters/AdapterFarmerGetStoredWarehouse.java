package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduceDetail;
import binaries.app.codeutsava.restapi.fragments.FragmentPotentialBuyerList;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;

import binaries.app.codeutsava.restapi.model.farmer.FarmerStorageTransactionResponse;

public class AdapterFarmerGetStoredWarehouse extends RecyclerView.Adapter<AdapterFarmerGetStoredWarehouse.ViewHolder> {
    private List<FarmerStorageTransactionResponse> produces;
    private Activity activity;
    private FragmentManager fragManager;

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
            holder.whQuantity.setText("Quantity: " + produce.quantity);
            holder.whFoodgrain.setText(produce.foodgrain);
            holder.whDate.setText("Date: " + produce.date);
            holder.whCost.setText("Price: " + produce.cost);

            long diffDays = getDateDifference(produce.date);
            Log.v("prod", Integer.toString(produce.fgDeadline));
            Log.v("diff", Long.toString(diffDays));
            //deadline before 60 days

            if (diffDays + 60 >= produce.fgDeadline) {
                Log.v("case1", "case1");
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




            }
            else {
                Log.v("case2","case2");
                holder.whDeadline.setVisibility(View.GONE);
            }




            } else {
                Log.v("case2", "case2");
                holder.whDeadline.setVisibility(View.GONE);
            }

        }




    private long getDateDifference(String startDate) {
        Date endDate = Calendar.getInstance().getTime();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-DD-MM");
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            whName = itemView.findViewById(R.id.fstoredWareHouseName);
            whCost = itemView.findViewById(R.id.fstoredWarehouseCost);
            whFoodgrain = itemView.findViewById(R.id.fstoredWarehouseFoodgrain);
            whDate = itemView.findViewById(R.id.fstoredWarehouseDate);
            whQuantity = itemView.findViewById(R.id.fstoredWarehouseQuantity);
            whDeadline = itemView.findViewById(R.id.fstoredWareHouseDeadline);
        }
    }
}
