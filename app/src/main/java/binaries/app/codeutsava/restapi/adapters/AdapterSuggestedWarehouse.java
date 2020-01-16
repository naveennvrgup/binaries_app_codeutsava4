package binaries.app.codeutsava.restapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import binaries.app.codeutsava.R;

public class AdapterSuggestedWarehouse extends RecyclerView.Adapter<AdapterSuggestedWarehouse.ViewHolder> {
    List<String> warehouseList;
    Context context;

    public AdapterSuggestedWarehouse(Context context) {
        this.warehouseList = Arrays.asList("Profile", "Report Produce", "Find Warehouse", "Profile", "Report Produce");
        this.context = context;
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
        String currWarehouse = warehouseList.get(position);

        holder.warehouseName.setText(currWarehouse);

    }

    @Override
    public int getItemCount() {
        return warehouseList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView warehouseName;
        TextView warehouseCost;
//        ImageView optionIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            warehouseName = itemView.findViewById(R.id.wareHouseName);
//            optionIcon = itemView.findViewById(R.id.wareHouseIcon);
            warehouseCost = itemView.findViewById(R.id.warehouseCost);
        }


    }
}


