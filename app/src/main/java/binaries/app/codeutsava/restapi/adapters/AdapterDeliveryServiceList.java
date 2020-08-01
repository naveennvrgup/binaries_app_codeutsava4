package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyer;
import binaries.app.codeutsava.restapi.fragments.AfterDeliveryDialogBox;
//import binaries.app.codeutsava.restapi.fragments.CustomDialogClass;
import binaries.app.codeutsava.restapi.model.delivery.DeliveryServiceResponse;
import binaries.app.codeutsava.restapi.model.delivery.FindDeliveryServiceResponse;
import binaries.app.codeutsava.restapi.utils.Misc;

import static android.content.ContentValues.TAG;

public class AdapterDeliveryServiceList extends RecyclerView.Adapter<AdapterDeliveryServiceList.ViewHolder> {

    List<DeliveryServiceResponse>deliveryServiceList;
    Context context;
    Activity activity;
    FragmentManager fragmentManager;

    private String foodgrainName, farmerName;
    private int destinationId;
    private String choice;
    private int foodgrainId;
    private String farmerContact;
    private double quantity;
    private int produceId;
    private double sellingPrice;
    private Bundle args;

    private Button placeOrderButton;
    private Activity buyerActivity;
    private FragmentManager buyerFragmentManager;

    private int farmerId;

    public AdapterDeliveryServiceList(List<DeliveryServiceResponse> deliveryServiceList, Context context, Activity activity, FragmentManager fragmentManager, int destinationId, String choice, int farmerId, int foodgrainId, String farmerContact, double quantity, int produceId, String farmerName, String foodgrainName, double sellingPrice, Bundle warehouseBundle, Button btn, Activity buyerActivity, FragmentManager buyerFragmentManager) {
        this.deliveryServiceList = deliveryServiceList;
        this.context = context;
        this.activity = activity;
        this.farmerName = farmerName;
        this.fragmentManager = fragmentManager;
        this.destinationId = destinationId;
        this.choice = choice;
        this.farmerId = farmerId;
        this.foodgrainId = foodgrainId;
        this.foodgrainName = foodgrainName;
        this.farmerContact = farmerContact;
        this.quantity = quantity;
        this.produceId = produceId;
        this.sellingPrice = sellingPrice;
        this.args = warehouseBundle;
        this.buyerActivity = buyerActivity;
        this.buyerFragmentManager = buyerFragmentManager;
        this.placeOrderButton = btn;
    }

    @NonNull
    @Override
    public AdapterDeliveryServiceList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recyclerview_delivery_service_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDeliveryServiceList.ViewHolder holder, int position) {
        DeliveryServiceResponse currentDeliveryService = deliveryServiceList.get(position);
        holder.deliveryCompanyName.setText(currentDeliveryService.deliveryServiceName);
        holder.deliveryBasePrice.setText(Misc.getHTML("Base Price (₹): " + currentDeliveryService.basePrice + "/- per KM"));
        holder.deliveryTotalPrice.setText(Misc.getHTML("Total Delivery Price (₹): " + currentDeliveryService.totalPrice + "/-"));

        holder.parent.setOnClickListener(v -> {
            args.putSerializable("deliveryServiceName", currentDeliveryService.deliveryServiceName);
            args.putSerializable("deliveryCost", currentDeliveryService.totalPrice);

            AfterDeliveryDialogBox cdd = new AfterDeliveryDialogBox(activity, foodgrainId, foodgrainName, farmerContact, produceId, quantity, farmerName, sellingPrice, choice, farmerId, currentDeliveryService.deliveryServiceId, args, placeOrderButton, buyerActivity, buyerFragmentManager);
            cdd.show();
        });
    }

    @Override
    public int getItemCount() {
        return deliveryServiceList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deliveryCompanyName;
        TextView deliveryBasePrice;
        TextView deliveryTotalPrice;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deliveryCompanyName = itemView.findViewById(R.id.deliveryCompanyName);
            deliveryBasePrice = itemView.findViewById(R.id.deliveryBasePrice);
            deliveryTotalPrice = itemView.findViewById(R.id.deliveryTotalPrice);
            parent = itemView.findViewById(R.id.recyclerDeliveryServiceList);
        }
    }
}


