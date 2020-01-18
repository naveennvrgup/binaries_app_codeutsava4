package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dashboardBg));
        }

        findViewById(R.id.farm_ord_back).setOnClickListener(view -> {
            Intent myIntent = new Intent(ActivityFarmerOrders.this, ActivityFarmer.class);
            startActivity(myIntent);
            finish();
        });

        fetchOrders();
    }

    void fetchOrders() {
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
                }
            }

            @Override
            public void onFailure(Call<List<BuyerOrderListResponse>> call, Throwable t) {
                Toast.makeText(ActivityFarmerOrders.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
