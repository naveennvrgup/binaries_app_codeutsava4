package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import  binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerOrder;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFarmerOrders extends AppCompatActivity {
    AdapterFarmerOrder mAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_orders);
        recyclerView = findViewById(R.id.FO_recycler);

        fetchOrders();
    }

    void fetchOrders(){
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<BuyerOrderListResponse>> call = apiServices.getFarmerOrders();

        call.enqueue(new Callback<List<BuyerOrderListResponse>>() {
            @Override
            public void onResponse(Call<List<BuyerOrderListResponse>> call, Response<List<BuyerOrderListResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mAdapter = new AdapterFarmerOrder(response.body(), ActivityFarmerOrders.this, getSupportFragmentManager());
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFarmerOrders.this));
                    mAdapter.notifyDataSetChanged();

//                    Toast.makeText(ActivityFarmerOrders.this, response.body().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BuyerOrderListResponse>> call, Throwable t) {
                Toast.makeText(ActivityFarmerOrders.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
