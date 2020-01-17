package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.CustomDialogClass;
import binaries.app.codeutsava.restapi.model.farmer.FarmerFindWarehouseResponse;

public class AdapterSuggestedWarehouse extends RecyclerView.Adapter<AdapterSuggestedWarehouse.ViewHolder> {
//    List<String> warehouseList;
    FarmerFindWarehouseResponse farmerFindWarehouseResponse;
    List<FarmerFindWarehouseResponse.WarehouseResponse> warehouseList;
    Context context;
    Activity activity;
    FragmentManager fragmentManager;

    public AdapterSuggestedWarehouse(List<FarmerFindWarehouseResponse.WarehouseResponse>warehouseList,
                                     Context context, Activity activity, FragmentManager fragmentManager) {
//        this.warehouseList = Arrays.asList("Profile", "Report Produce", "Find Warehouse", "Profile", "Report Produce");
        this.warehouseList = warehouseList;
        this.context = context;
        this.activity = activity;
        this.fragmentManager =fragmentManager;
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

        holder.warehouseName.setText(Integer.toString(currWarehouse.whid));
        holder.warehouseCost.setText(Double.toString(currWarehouse.price));

        holder.parent.setOnClickListener(v -> {
//            FarmerFindWarehouseResponse.WarehouseResponse currWarehouse = warehouseList.get(position);

            Bundle args = new Bundle();
            args.putSerializable("whid", currWarehouse.whid);
            args.putSerializable("whname", currWarehouse.whname);
            args.putSerializable("whdis",currWarehouse.distance);
            args.putSerializable("whprice",currWarehouse.price);
            args.putSerializable("whcentre",currWarehouse.centre);
            args.putSerializable("whsector",currWarehouse.sector);
            args.putSerializable("whowner",currWarehouse.owner);
            args.putSerializable("whavl",currWarehouse.availstorage);


//            View view = LayoutInflater.from(activity).inflate(R.layout.warehouse_transaction_custom_dialog_box,null,false);
//            AlertDialog dialog = new AlertDialog.Builder(activity)
//                    .setView(view)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).create();
//            dialog.show();

            CustomDialogClass cdd=new CustomDialogClass(activity,args);
            cdd.show();
//
//            FragmentFarmerProduceDetail produceDetail = new FragmentFarmerProduceDetail();
//            produceDetail.setArguments(args);
//            produceDetail.show(fragManager, "produce");
        });

    }

    @Override
    public int getItemCount() {
        return warehouseList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView warehouseName;
        TextView warehouseCost;
        CardView parent;

//        ImageView optionIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            warehouseName = itemView.findViewById(R.id.wareHouseName);
//            optionIcon = itemView.findViewById(R.id.wareHouseIcon);
            warehouseCost = itemView.findViewById(R.id.warehouseCost);
            parent = itemView.findViewById(R.id.recyclerSuggestedWarehouse);
        }


    }
}


