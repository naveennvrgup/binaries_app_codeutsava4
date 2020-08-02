package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterBuyerOrder;
import binaries.app.codeutsava.restapi.adapters.AdapterFilter;
import binaries.app.codeutsava.restapi.fragments.FragmentBuyerBottomSheet;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuyerOrders extends BaseActivity {
    private RecyclerView recyclerView, filterRecyclerView;
    private AdapterBuyerOrder mAdapter;
    private AdapterFilter adapterFilter;
    private ProgressBar progressBar;
    private List<BuyerOrderListResponse> responseList = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private boolean called = false;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_buyer_orders;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.bo_recycler);
        progressBar = findViewById(R.id.act_buy_order_progress);
        findViewById(R.id.bo_buyer_back_icon).setOnClickListener(v -> {
            Intent intent = new Intent(ActivityBuyerOrders.this, ActivityBuyer.class);
            startActivity(intent);
            finish();
        });

        mAdapter = new AdapterBuyerOrder(this, getSupportFragmentManager());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityBuyerOrders.this));

        refreshLayout = findViewById(R.id.buy_ord_refresh);
        refreshLayout.setOnRefreshListener(() -> {
            if(!called){
                makeApiCall();
            }
        });

        filterRecyclerView = findViewById(R.id.buy_ord_filter);
        adapterFilter = new AdapterFilter(this);
        List<String> filters = new ArrayList<>();

        filters.add(AppConstants.FILTER_ALL);
        filters.add(AppConstants.FILTER_APPROVED);
        filters.add(AppConstants.FILTER_PENDING);

        adapterFilter.addFilters(filters);
        adapterFilter.setOnFilterChangeListener(newFilter -> mAdapter.reflectFilterChange(responseList, newFilter));

        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filterRecyclerView.setAdapter(adapterFilter);

        makeApiCall();
    }

    public void makeApiCall() {
        called = true;

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<BuyerOrderListResponse>> call = apiServices.getBuyerOrders(
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN));

        call.enqueue(new Callback<List<BuyerOrderListResponse>>() {
            @Override
            public void onResponse(Call<List<BuyerOrderListResponse>> call, Response<List<BuyerOrderListResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);

                    responseList.clear();
                    responseList.addAll(response.body());

                    mAdapter.reflectFilterChange(responseList, adapterFilter.getDefaultFilter());
                }

                if(refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);

                called = false;
            }

            @Override
            public void onFailure(Call<List<BuyerOrderListResponse>> call, Throwable t) {
                Toast.makeText(ActivityBuyerOrders.this, t.getMessage(), Toast.LENGTH_LONG).show();
                called = false;

                if(refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityBuyerOrders.this, ActivityBuyer.class);
        startActivity(intent);
        finish();
    }
}
