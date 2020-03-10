package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerOrder;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFarmerOrders extends BaseActivity {
    AdapterFarmerOrder mAdapter;
    RecyclerView recyclerView;
    TextView recOrdEmptyText;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_farmer_orders;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.FO_recycler);

        findViewById(R.id.farm_ord_back).setOnClickListener(view -> {
            Intent myIntent = new Intent(ActivityFarmerOrders.this, ActivityFarmer.class);
            startActivity(myIntent);
            finish();
        });

        recOrdEmptyText = findViewById(R.id.farm_ord_empty_text);

        fetchOrders();
    }

    void fetchOrders() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);

        Call<List<BuyerOrderListResponse>> call = apiServices.getFarmerOrders(
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN));

        call.enqueue(new Callback<List<BuyerOrderListResponse>>() {
            @Override
            public void onResponse(Call<List<BuyerOrderListResponse>> call, Response<List<BuyerOrderListResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mAdapter = new AdapterFarmerOrder(response.body(), ActivityFarmerOrders.this, getSupportFragmentManager());

                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFarmerOrders.this));
                    mAdapter.notifyDataSetChanged();
                }

                if(!response.isSuccessful() || response.body() == null || response.body().isEmpty())
                    recOrdEmptyText.setVisibility(View.VISIBLE);

                findViewById(R.id.farm_ord_prog).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<BuyerOrderListResponse>> call, Throwable t) {
                Toast.makeText(ActivityFarmerOrders.this, t.getMessage(), Toast.LENGTH_LONG).show();
                recOrdEmptyText.setVisibility(View.VISIBLE);
                findViewById(R.id.farm_ord_prog).setVisibility(View.GONE);
            }
        });
    }
}
