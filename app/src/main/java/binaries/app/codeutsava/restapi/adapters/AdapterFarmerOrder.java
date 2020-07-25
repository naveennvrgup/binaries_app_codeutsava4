package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FarmerOrderAcceptDialog;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.model.farmer.ApproveOrderPayload;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import binaries.app.codeutsava.restapi.utils.Misc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFarmerOrder extends RecyclerView.Adapter<AdapterFarmerOrder.ViewHolder> {
    private List<BuyerOrderListResponse> orders = new ArrayList<>();
    private Activity activity;
    private OnResponseClickedListener listener;

    public AdapterFarmerOrder(Activity activity) {
        this.activity = activity;
    }

    public void setOnResponseClickedListener(OnResponseClickedListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterFarmerOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_farmer_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFarmerOrder.ViewHolder holder, int position) {
        BuyerOrderListResponse orderListResponse = orders.get(position);

        holder.buyer.setText(orderListResponse.buyer);
        holder.quantity.setText(Misc.getHTML("Qty: " + orderListResponse.quantity + "kgs."));
        holder.price.setText(Misc.getHTML("Price (₹): " + orderListResponse.price + "/-"));
        holder.foodgraintype.setText(orderListResponse.foodgraintype);
        holder.transno.setText(Misc.getHTML("TN: " + orderListResponse.transno));

        if (orderListResponse.approved) {
            holder.approved.setTextColor(activity.getResources().getColor(R.color.colorGreen));
            holder.approved.setText("Approved");
            holder.layout.setVisibility(View.GONE);

        } else {
            holder.approved.setTextColor(activity.getResources().getColor(R.color.colorYellow));
            holder.approved.setText("Awaiting Approval");
        }

        holder.approve.setOnClickListener(v -> approveApiCall(orderListResponse.id, position));
        holder.reject.setOnClickListener(v -> rejectApiCall(orderListResponse.id, position));
    }

    public void reflectFilterChange(List<BuyerOrderListResponse> norders, String filter) {
        orders.clear();

        if (filter.equals(AppConstants.FILTER_ALL))
            orders.addAll(norders);
        else
            for (BuyerOrderListResponse response : norders) {
                if (filter.equals(AppConstants.FILTER_APPROVED) && response.approved)
                    orders.add(response);

                if (filter.equals(AppConstants.FILTER_PENDING) && !response.approved)
                    orders.add(response);
            }

        notifyDataSetChanged();
    }

    private void approveApiCall(int id, int position) {
        if(listener != null) listener.onResponseClicked();

        FarmerOrderAcceptDialog dialog = new FarmerOrderAcceptDialog(activity, orders, id, position, AdapterFarmerOrder.this);

        dialog.setOnCustomClickListener(get_from -> {
            ApproveOrderPayload payload = new ApproveOrderPayload();
            payload.get_from = get_from;

            APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
            Call<Boolean> call = apiServices.approveOrder(
                    PreferenceManager.getDefaultSharedPreferences(activity).getString("token", AppConstants.TEMP_FARM_TOKEN), id, payload);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    orders.get(position).approved = true;
                    notifyItemChanged(position);
                    dialog.dismiss();

                    Toast.makeText(activity, "You Accepted the Order.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
        dialog.show();
    }

    private void rejectApiCall(int id, int position) {
        if(listener != null) listener.onResponseClicked();

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<Boolean> call = apiServices.rejectOrder(
                PreferenceManager.getDefaultSharedPreferences(activity).getString("token", AppConstants.TEMP_FARM_TOKEN), id);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(activity, "You Rejected the Order.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView buyer, quantity, price, foodgraintype, transno, approved;
        Button approve, reject;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            buyer = itemView.findViewById(R.id.fo_buyer);
            quantity = itemView.findViewById(R.id.fo_quantity);
            price = itemView.findViewById(R.id.fo_price);
            foodgraintype = itemView.findViewById(R.id.fo_graintype);
            transno = itemView.findViewById(R.id.fo_trans_no);
            approved = itemView.findViewById(R.id.fo_approved);
            approve = itemView.findViewById(R.id.fo_accept_btn);
            reject = itemView.findViewById(R.id.fo_reject_btn);
            layout = itemView.findViewById(R.id.recycler_farm_ord_lay);
        }
    }

    public interface OnResponseClickedListener {
        void onResponseClicked();
    }
}
