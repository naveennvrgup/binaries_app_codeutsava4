package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFilter;
import binaries.app.codeutsava.restapi.adapters.AdapterPastBid;
import binaries.app.codeutsava.restapi.model.buyer.BidCreatePayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuyerBidsList extends BaseActivity {
    Button createBidBtn;
    RecyclerView recyclerView, filterRecyclerView;
    AdapterPastBid adapterPastBid;
    AdapterFilter adapterFilter;
    List<FarmerActiveBidListResponse> responses = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_buyer_bids_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.buyer_bids_recycler);
        createBidBtn = findViewById(R.id.createbidbutton);

        createBidBtn.setOnClickListener(v -> showDialog());

        findViewById(R.id.act_buy_bid_back).setOnClickListener(view -> {
            Intent intent = new Intent(ActivityBuyerBidsList.this, ActivityBuyer.class);
            startActivity(intent);
            finish();
        });

        // main recycler view instantiation
        adapterPastBid = new AdapterPastBid(this);
        recyclerView.setLayoutManager(new GridLayoutManager(ActivityBuyerBidsList.this, 1));
        recyclerView.setAdapter(adapterPastBid);

        // filter recycler view instantiation
        filterRecyclerView = findViewById(R.id.buy_bid_list_filter);

        List<String> filters = new ArrayList<>();
        filters.add(AppConstants.FILTER_ACTIVE);
        filters.add(AppConstants.FILTER_INACTIVE);

        adapterFilter = new AdapterFilter(this);
        adapterFilter.addFilters(filters);
        adapterFilter.setOnFilterChangeListener(newFilter -> adapterPastBid.reflectFilterChange(responses, newFilter));

        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filterRecyclerView.setAdapter(adapterFilter);

        getBids();
    }

    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.create_bid_dialog, null);

        EditText foodgrain, quantity, desc;
        builder.setView(view);

        foodgrain = view.findViewById(R.id.cb_foodgrain);
        quantity = view.findViewById(R.id.cb_quantity);
        desc = view.findViewById(R.id.cb_description);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            BidCreatePayload payload = new BidCreatePayload();
            payload.foodgrain = foodgrain.getText().toString();
            payload.quantity = quantity.getText().toString();
            payload.description = desc.getText().toString();

            AppClient.getInstance().createService(APIServices.class)
                    .createBid(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN),
                            payload)
                    .enqueue(new Callback<FarmerActiveBidListResponse>() {
                        @Override
                        public void onResponse(Call<FarmerActiveBidListResponse> call, Response<FarmerActiveBidListResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                dialog.dismiss();
                                getBids();
                            }

                            findViewById(R.id.farm_bids_prog).setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<FarmerActiveBidListResponse> call, Throwable t) {
                            Toast.makeText(ActivityBuyerBidsList.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            findViewById(R.id.farm_bids_prog).setVisibility(View.GONE);
                        }
                    });
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    void getBids() {
        AppClient.getInstance().createService(APIServices.class).getPastBidsList(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN))
                .enqueue(new Callback<List<FarmerActiveBidListResponse>>() {
                    @Override
                    public void onResponse(Call<List<FarmerActiveBidListResponse>> call, Response<List<FarmerActiveBidListResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            responses.clear();
                            responses.addAll(response.body());

                            adapterPastBid.reflectFilterChange(responses, adapterFilter.getDefaultFilter());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FarmerActiveBidListResponse>> call, Throwable t) {
                        Toast.makeText(ActivityBuyerBidsList.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
