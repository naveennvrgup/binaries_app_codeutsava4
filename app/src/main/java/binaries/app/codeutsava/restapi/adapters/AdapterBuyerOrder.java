package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import binaries.app.codeutsava.restapi.utils.Misc;

public class AdapterBuyerOrder extends RecyclerView.Adapter<AdapterBuyerOrder.ViewHolder> {
    private Activity activity;
    private List<BuyerOrderListResponse> orders = new ArrayList<>();

    public AdapterBuyerOrder(Activity activity) {
        this.activity = activity;
    }

    public void reflectFilterChange(List<BuyerOrderListResponse> responses, String newFilter){
        orders.clear();

        for(BuyerOrderListResponse response : responses){
            if(newFilter.equals(AppConstants.FILTER_APPROVED) && response.approved)
                orders.add(response);

            if(newFilter.equals(AppConstants.FILTER_PENDING) && !response.approved)
                orders.add(response);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterBuyerOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recyler_buyer_order_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBuyerOrder.ViewHolder holder, int position) {
        if(orders != null && !orders.isEmpty()){
            BuyerOrderListResponse orderListResponse = orders.get(position);

            holder.seller.setText(orderListResponse.seller);
            holder.quantity.setText(Misc.getHTML("Qty: " + orderListResponse.quantity));
            holder.price.setText(Misc.getHTML("Price (₹): " + orderListResponse.price));
            holder.foodgraintype.setText(orderListResponse.foodgraintype);

            if (orderListResponse.approved) {
                holder.approved.setTextColor(activity.getResources().getColor(R.color.colorGreen));
                holder.approved.setText("Approved.");
            } else {
                holder.approved.setTextColor(activity.getResources().getColor(R.color.colorYellow));
                holder.approved.setText("Awaiting Response");
            }
        }
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 :  orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
