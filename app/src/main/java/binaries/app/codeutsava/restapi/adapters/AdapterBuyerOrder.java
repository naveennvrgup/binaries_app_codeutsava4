package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;

public class AdapterBuyerOrder extends RecyclerView.Adapter<AdapterBuyerOrder.ViewHolder> {
    List<BuyerOrderListResponse> orders;
    Activity activity;
    FragmentManager fragmentManager;

    public AdapterBuyerOrder(List<BuyerOrderListResponse> orders, Activity activity, FragmentManager fragmentManager) {
        this.orders = orders;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public AdapterBuyerOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recyler_buyer_order_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBuyerOrder.ViewHolder holder, int position) {
        BuyerOrderListResponse orderListResponse = orders.get(position);

        holder.seller.setText(orderListResponse.seller);
        holder.quantity.setText("Quantity: " + orderListResponse.quantity);
        holder.price.setText("Price: " + orderListResponse.price);
        holder.foodgraintype.setText(orderListResponse.foodgraintype);

        if (orderListResponse.approved) {
            holder.approved.setTextColor(activity.getResources().getColor(R.color.colorGreen));
            holder.approved.setText("Transaction Approved.");
        } else {
            holder.approved.setTextColor(activity.getResources().getColor(R.color.colorYellow));
            holder.approved.setText("Awaiting Response");
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView seller, quantity, price, foodgraintype, approved;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            seller = itemView.findViewById(R.id.bo_farmer);
            quantity = itemView.findViewById(R.id.bo_quantity);
            price = itemView.findViewById(R.id.bo_price);
            foodgraintype = itemView.findViewById(R.id.bo_graintype);
            approved = itemView.findViewById(R.id.bo_approved);
        }
    }
}
