package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityFarmerOrders;
import binaries.app.codeutsava.restapi.fragments.FarmerOrderAcceptDialog;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.model.farmer.ApproveOrderPayload;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class AdapterFarmerOrder extends RecyclerView.Adapter<AdapterFarmerOrder.ViewHolder> {
    List<BuyerOrderListResponse> orders;
    Activity activity;
    FragmentManager fragmentManager;

    public AdapterFarmerOrder(List<BuyerOrderListResponse> orders, Activity activity, FragmentManager fragmentManager) {
        this.orders = orders;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public AdapterFarmerOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.recycler_farmer_order, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFarmerOrder.ViewHolder holder, int position) {
        BuyerOrderListResponse orderListResponse = orders.get(position);

        holder.buyer.setText(orderListResponse.buyer);
        holder.quantity.setText(String.valueOf(orderListResponse.quantity));
        holder.price.setText(String.valueOf(orderListResponse.price));
        holder.foodgraintype.setText(orderListResponse.foodgraintype);
        holder.transno.setText(orderListResponse.transno);
        holder.approved.setText(Boolean.toString(orderListResponse.approved));


        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveApiCall(orderListResponse.id,position);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectApiCall(orderListResponse.id,position);
            }
        });
    }

    void approveApiCall(int id, int position) {
        FarmerOrderAcceptDialog dialog = new FarmerOrderAcceptDialog(
                activity,orders,id,position,AdapterFarmerOrder.this);

        dialog.setOnCustomClickListener(new FarmerOrderAcceptDialog.OnCustomClickListener() {
            @Override
            public void onClick(String get_from) {
                ApproveOrderPayload payload = new ApproveOrderPayload();
                payload.get_from=get_from;

                APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
                Call<Boolean> call = apiServices.approveOrder(id,payload);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        orders.get(position).approved=true;
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(activity,t.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        dialog.show();
    }

    void rejectApiCall(int id, int position) {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<Boolean> call = apiServices.rejectOrder(id);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(activity, "rejected the order", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(activity, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView buyer, quantity, price, foodgraintype, transno, approved;
        Button approve, reject;

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

        }
    }
}
