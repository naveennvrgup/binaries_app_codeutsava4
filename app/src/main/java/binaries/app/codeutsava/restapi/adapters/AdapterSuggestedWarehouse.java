package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.CustomDialogClass;
import binaries.app.codeutsava.restapi.model.farmer.FarmerFindWarehouseResponse;

public class AdapterSuggestedWarehouse extends RecyclerView.Adapter<AdapterSuggestedWarehouse.ViewHolder> {
    FarmerFindWarehouseResponse farmerFindWarehouseResponse;
    List<FarmerFindWarehouseResponse.WarehouseResponse> warehouseList;
    Context context;
    Activity activity;
    FragmentManager fragmentManager;
    int produce_id;

    public AdapterSuggestedWarehouse(List<FarmerFindWarehouseResponse.WarehouseResponse> warehouseList, int produce_id, Context context, Activity activity, FragmentManager fragmentManager) {
        this.warehouseList = warehouseList;
        this.context = context;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.produce_id = produce_id;
    }

    @NonNull
    @Override
    public AdapterSuggestedWarehouse.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recyclerview_suggested_warehouse, parent, false);

        return new AdapterSuggestedWarehouse.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSuggestedWarehouse.ViewHolder holder, int position) {
        FarmerFindWarehouseResponse.WarehouseResponse currWarehouse = warehouseList.get(position);

        holder.warehouseName.setText(currWarehouse.whname);
        holder.warehouseCost.setText("Price: " + currWarehouse.price);
        holder.warehouseDist.setText("Distance: " + currWarehouse.distance);

        holder.parent.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putSerializable("whid", currWarehouse.whid);
            args.putSerializable("whname", currWarehouse.whname);
            args.putSerializable("whdis", currWarehouse.distance);
            args.putSerializable("whprice", currWarehouse.price);
            args.putSerializable("whcentre", currWarehouse.centre);
            args.putSerializable("whsector", currWarehouse.sector);
            args.putSerializable("whowner", currWarehouse.owner);
            args.putSerializable("whavl", currWarehouse.availstorage);
            args.putSerializable("produce_id", produce_id);

            CustomDialogClass cdd = new CustomDialogClass(activity, args);
            cdd.show();
        });

    }

    @Override
    public int getItemCount() {
        return warehouseList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView warehouseName;
        TextView warehouseCost;
        TextView warehouseDist;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            warehouseName = itemView.findViewById(R.id.wareHouseName);
            warehouseCost = itemView.findViewById(R.id.warehouseCost);
            warehouseDist = itemView.findViewById(R.id.warehouseDistance);
            parent = itemView.findViewById(R.id.recyclerSuggestedWarehouse);
        }
    }
}


