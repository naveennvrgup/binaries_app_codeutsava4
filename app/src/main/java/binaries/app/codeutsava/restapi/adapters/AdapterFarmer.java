package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyerOrders;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.model.buyer.FarmerResponse;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderPayload;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFarmer extends RecyclerView.Adapter<AdapterFarmer.ViewHolder> {
    private FragmentManager fragmentManager;
    private List<FarmerResponse> ldata;
    private BuyerFoodgrainResponse foodgrain;
    private Activity activity;
    private int quantity, foodgrain_id;

    public AdapterFarmer(FragmentManager fragmentManager, Activity activity, BuyerFoodgrainResponse foodgrain, int quantity) {
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.foodgrain = foodgrain;
        this.quantity = quantity;
    }

    public void setData(int foodgrain_id, List<FarmerResponse> ldata, int quantity) {
        this.foodgrain_id = foodgrain_id;
        this.ldata = ldata;
        this.quantity = quantity;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_farmer, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (ldata != null) {
            FarmerResponse data = ldata.get(position);

            holder.name.setText(data.farmer.name);
            holder.price.setText("Rs. " + data.price);
            holder.chooseBtn.setOnClickListener(v -> {
                holder.chooseBtn.setEnabled(false);
                holder.chooseBtn
                        .animate()
                        .alpha(0.0f)
                        .setDuration(200)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .start();

                placeOrder(data, data.produce_id, holder.chooseBtn);
            });
        }
    }

    private void placeOrder(FarmerResponse data, int produce_id, Button buy) {
        PlaceOrderPayload payload = new PlaceOrderPayload();
        payload.farmer_contact = data.farmer.contact;
        payload.foodgrain_id = foodgrain_id;
        payload.quantity = quantity;
        payload.produce_id = produce_id;

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<PlaceOrderResponse> call = apiServices.placeOrderRequest(
                PreferenceManager.getDefaultSharedPreferences(activity).getString("token", AppConstants.TEMP_FARM_TOKEN), payload);

        call.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentManager.popBackStack();
                    fragmentManager.popBackStack();

                    activity.startActivity(new Intent(activity, ActivityBuyerOrders.class));
                    activity.finish();
                }
            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();

                buy.setEnabled(true);
                buy.animate()
                        .alpha(1.0f)
                        .setDuration(200)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .start();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ldata == null ? 0 : ldata.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, price;
        Button chooseBtn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.farmer_name);
            price = itemView.findViewById(R.id.price);
            chooseBtn = itemView.findViewById(R.id.choose_button);
        }
    }
}
