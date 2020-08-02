package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.AfterDeliveryDialogBox;
import binaries.app.codeutsava.restapi.fragments.FragmentBuyerOrderDetailView;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import binaries.app.codeutsava.restapi.utils.Misc;

import static android.content.ContentValues.TAG;

public class AdapterBuyerOrder extends RecyclerView.Adapter<AdapterBuyerOrder.ViewHolder> {
    private Activity activity;
    private FragmentManager fragmentManager;
    private List<BuyerOrderListResponse> orders = new ArrayList<>();

    public AdapterBuyerOrder(Activity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    public void reflectFilterChange(List<BuyerOrderListResponse> responses, String newFilter) {
        orders.clear();

        if (newFilter.equals(AppConstants.FILTER_ALL))
            orders.addAll(responses);
        else
            for (BuyerOrderListResponse response : responses) {
                if (newFilter.equals(AppConstants.FILTER_APPROVED) && response.approved)
                    orders.add(response);

                if (newFilter.equals(AppConstants.FILTER_PENDING) && !response.approved)
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
        if (orders != null && !orders.isEmpty()) {
            BuyerOrderListResponse orderListResponse = orders.get(position);

            Bundle args = new Bundle();

            holder.transNo.setText(Misc.getHTML("TN: " + orderListResponse.transno));
            holder.transNo.setVisibility(View.INVISIBLE);
            holder.seller.setText(orderListResponse.seller);
            holder.quantity.setText(Misc.getHTML("Qty: " + orderListResponse.quantity + "kgs."));
            holder.price.setText(Misc.getHTML("Price (₹): " + orderListResponse.price + "/-"));
            holder.foodgraintype.setText(orderListResponse.foodgraintype);

            args.putSerializable("transactionSaleId", orderListResponse.id);
            args.putSerializable("sellerName", orderListResponse.seller);
            args.putSerializable("quantity", orderListResponse.quantity);
            args.putSerializable("price", orderListResponse.price);
            args.putSerializable("foodgrainName", orderListResponse.foodgraintype);

            if (orderListResponse.approved) {
                holder.approved.setTextColor(activity.getResources().getColor(R.color.colorGreen));
                holder.approved.setText("Approved.");
                args.putSerializable("approved", true);
            } else {
                holder.approved.setTextColor(activity.getResources().getColor(R.color.colorYellow));
                holder.approved.setText("Awaiting Response");
                args.putSerializable("approved", false);
            }

            //orderListResponse.id
            holder.parent.setOnClickListener(v -> {
                FragmentBuyerOrderDetailView fragmentBuyerOrderDetailView = new FragmentBuyerOrderDetailView(args);
                fragmentBuyerOrderDetailView.show(fragmentManager, "toOrderDetailView");
            });
        }
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView seller, quantity, price, foodgraintype, approved, transNo;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            transNo = itemView.findViewById(R.id.bo_trans_no);
            seller = itemView.findViewById(R.id.bo_farmer);
            quantity = itemView.findViewById(R.id.bo_quantity);
            price = itemView.findViewById(R.id.bo_price);
            foodgraintype = itemView.findViewById(R.id.bo_graintype);
            approved = itemView.findViewById(R.id.bo_approved);
            parent = itemView.findViewById(R.id.buyerOrderCardView);
        }
    }
}
