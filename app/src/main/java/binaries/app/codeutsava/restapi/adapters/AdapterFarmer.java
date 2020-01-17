package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.model.buyer.FarmerResponse;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderPayload;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFarmer extends RecyclerView.Adapter<AdapterFarmer.ViewHolder> {
    FragmentManager fragmentManager;
    List<FarmerResponse> ldata;
    BuyerFoodgrainResponse foodgrain;
    Activity activity;
    int quantity, foodgrain_id;

    public AdapterFarmer(FragmentManager fragmentManager, int foodgrain_id,
                         List<FarmerResponse> ldata, Activity activity,
                         BuyerFoodgrainResponse foodgrain, int quantity) {
        this.fragmentManager = fragmentManager;
        this.ldata = ldata;
        this.activity = activity;
        this.foodgrain = foodgrain;
        this.quantity = quantity;
        this.foodgrain_id = foodgrain_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.recycler_farmer, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FarmerResponse data = ldata.get(position);

        holder.name.setText(data.farmer.name);
        holder.price.setText("Rs. " + data.price);
        holder.chooseBtn.setOnClickListener(v -> placeOrder(data));
    }

    private void placeOrder(FarmerResponse data) {
        PlaceOrderPayload payload = new PlaceOrderPayload();
        payload.farmer_contact = data.farmer.contact;
        payload.foodgrain_id = foodgrain_id;
        payload.quantity = quantity;


        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<PlaceOrderResponse> call = apiServices.placeOrderRequest(payload);

        call.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // TODO: Route back to home screen again
                }
            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
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
