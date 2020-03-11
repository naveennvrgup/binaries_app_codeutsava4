package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterBuyerPlacedBid;
import binaries.app.codeutsava.restapi.model.buyer.PlaceBidResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import binaries.app.codeutsava.restapi.utils.Misc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuyerBidDetail extends BaseActivity {
    TextView foodgrain, desc, quantity, transno, deadline, listText;
    RecyclerView recyclerView;
    AdapterBuyerPlacedBid adapterBuyerPlacedBid;
    SwipeRefreshLayout refreshLayout;
    boolean called = false;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_buyer_bid_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        foodgrain = findViewById(R.id.bdetail_foodgrain);
        desc = findViewById(R.id.bdetail_description);
        quantity = findViewById(R.id.bdetail_quantity);
        transno = findViewById(R.id.bdetail_transno);
        deadline = findViewById(R.id.bdetail_deadline);
        listText = findViewById(R.id.buy_bid_det_list_text);
        recyclerView = findViewById(R.id.bdetail_recycler);

        Bundle bundle = getIntent().getExtras();
        FarmerActiveBidListResponse bid = (FarmerActiveBidListResponse) bundle.getSerializable("bid");

        if(bid != null){
            foodgrain.setText(bid.type.type);
            desc.setText(Misc.getHTML("Description: \n" + "         " + bid.description));
            quantity.setText(Misc.getHTML("Qty: " + bid.quantity));
            transno.setText(Misc.getHTML("TN: " + bid.transno));
            deadline.setText(Misc.getHTML("Deadline: " + bid.deadline));

            get_pbids(bid.id);
        }

        findViewById(R.id.buy_bid_det_back).setOnClickListener(view -> {
            Intent intent = new Intent(ActivityBuyerBidDetail.this, ActivityBuyer.class);
            startActivity(intent);
            finish();
        });

        refreshLayout = findViewById(R.id.buyer_bid_ref);

        refreshLayout.setOnRefreshListener(() -> {
            if(bid != null && !called)
                get_pbids(bid.id);
        });
    }

    void get_pbids(String bid) {
        called = true;

        AppClient.getInstance().createService(APIServices.class)
                .getFarmerResponseBideList(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN), bid)
                .enqueue(new Callback<List<PlaceBidResponse>>() {
                    @Override
                    public void onResponse(Call<List<PlaceBidResponse>> call, Response<List<PlaceBidResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            adapterBuyerPlacedBid = new AdapterBuyerPlacedBid(ActivityBuyerBidDetail.this, response.body());
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                            recyclerView.setAdapter(adapterBuyerPlacedBid);
                            adapterBuyerPlacedBid.notifyDataSetChanged();
                        }

                        if(!response.isSuccessful() || response.body() == null || response.body().isEmpty()){
                            listText.setText("No Farmers Available for this order.");
                            listText.setTextSize(18);
                        }

                        findViewById(R.id.act_buy_bid_prog).setVisibility(View.GONE);
                        called = false;

                        if(refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<PlaceBidResponse>> call, Throwable t) {
                        Toast.makeText(ActivityBuyerBidDetail.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        listText.setText("No Farmers Available for this order.");
                        listText.setTextSize(18);

                        called = false;
                        findViewById(R.id.act_buy_bid_prog).setVisibility(View.GONE);
                        if(refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
                    }
                });
    }
}
