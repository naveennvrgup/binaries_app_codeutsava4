package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerOrder;
import binaries.app.codeutsava.restapi.adapters.AdapterFilter;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFarmerOrders extends BaseActivity{
    AdapterFarmerOrder mAdapter;
    RecyclerView recyclerView, filterRecyclerView;
    TextView recOrdEmptyText;
    List<BuyerOrderListResponse> orders = new ArrayList<>();
    AdapterFilter adapterFilter;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_farmer_orders;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.FO_recycler);
        filterRecyclerView = findViewById(R.id.farm_rec_ord_filter);

        findViewById(R.id.farm_ord_back).setOnClickListener(view -> {
            Intent myIntent = new Intent(ActivityFarmerOrders.this, ActivityFarmer.class);
            startActivity(myIntent);
            finish();
        });

        // filter recycler view instantiation
        adapterFilter = new AdapterFilter(this);
        List<String> filters = new ArrayList<>();
        filters.add(AppConstants.FILTER_PENDING);
        filters.add(AppConstants.FILTER_APPROVED);

        adapterFilter.addFilters(filters);
        adapterFilter.setOnFilterChangeListener(newFilter -> mAdapter.reflectFilterChange(orders, newFilter));

        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filterRecyclerView.setAdapter(adapterFilter);

        // main recycler view instantiation
        mAdapter = new AdapterFarmerOrder(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFarmerOrders.this));

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
                    orders.clear();
                    orders.addAll(response.body());

                    mAdapter.reflectFilterChange(orders, adapterFilter.getDefaultFilter());
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
