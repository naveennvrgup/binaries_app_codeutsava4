package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerBids;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFarmerBids extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterFarmerBids adapterFarmerBids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_bids);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dashboardBg));
        }

        recyclerView = findViewById(R.id.farmer_bids_recycler);

        findViewById(R.id.act_farm_bid_back).setOnClickListener(view -> {
            Intent intent = new Intent(ActivityFarmerBids.this, ActivityFarmer.class);
            startActivity(intent);
            finish();
        });

        getbids();
    }

    void getbids() {
        AppClient.getInstance().createService(APIServices.class)
                .getFarmerActiveBidList(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN))
                .enqueue(new Callback<List<FarmerActiveBidListResponse>>() {
                    @Override
                    public void onResponse(Call<List<FarmerActiveBidListResponse>> call, Response<List<FarmerActiveBidListResponse>> response) {
                        if (response.isSuccessful()) {
                            adapterFarmerBids = new AdapterFarmerBids(ActivityFarmerBids.this, response.body());
                            recyclerView.setAdapter(adapterFarmerBids);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFarmerBids.this));
                            adapterFarmerBids.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FarmerActiveBidListResponse>> call, Throwable t) {
                        Toast.makeText(ActivityFarmerBids.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
