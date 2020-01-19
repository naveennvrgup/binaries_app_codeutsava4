package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterBuyerPlacedBid;
import binaries.app.codeutsava.restapi.model.buyer.PlaceBidResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuyerBidDetail extends AppCompatActivity {
    TextView foodgrain, desc, quantity, transno, deadline;
    RecyclerView recyclerView;
    AdapterBuyerPlacedBid adapterBuyerPlacedBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_bid_detail);

        foodgrain = findViewById(R.id.bdetail_foodgrain);
        desc = findViewById(R.id.bdetail_description);
        quantity = findViewById(R.id.bdetail_quantity);
        transno = findViewById(R.id.bdetail_transno);
        deadline = findViewById(R.id.bdetail_deadline);
        recyclerView = findViewById(R.id.bdetail_recycler);

        Bundle bundle = getIntent().getExtras();
        FarmerActiveBidListResponse bid = (FarmerActiveBidListResponse) bundle.getSerializable("bid");

        foodgrain.setText(bid.type.type);
        desc.setText(bid.description);
        quantity.setText(bid.quantity);
        transno.setText(bid.transno);
        deadline.setText(bid.deadline);

        findViewById(R.id.buy_bid_det_back).setOnClickListener(view -> {
            Intent intent = new Intent(ActivityBuyerBidDetail.this, ActivityBuyer.class);
            startActivity(intent);
            finish();
        });

        get_pbids(bid.id);
    }

    void get_pbids(String bid) {
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
                    }

                    @Override
                    public void onFailure(Call<List<PlaceBidResponse>> call, Throwable t) {
                        Toast.makeText(ActivityBuyerBidDetail.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
