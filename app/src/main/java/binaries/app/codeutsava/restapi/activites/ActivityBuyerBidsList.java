package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterPastBid;
import binaries.app.codeutsava.restapi.model.buyer.BidCreatePayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuyerBidsList extends AppCompatActivity {
    FloatingActionButton createBidBtn;
    RecyclerView recyclerView;
    AdapterPastBid adapterPastBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_bids_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dashboardBg));
        }

        recyclerView = findViewById(R.id.buyer_bids_recycler);
        createBidBtn = findViewById(R.id.createbidbutton);

        createBidBtn.setOnClickListener(v -> showdialog());

        findViewById(R.id.act_buy_bid_back).setOnClickListener(view -> {
            Intent intent = new Intent(ActivityBuyerBidsList.this, ActivityBuyer.class);
            startActivity(intent);
            finish();
        });

        getbids();
    }

    void showdialog() {
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
                                getbids();
                            }
                        }

                        @Override
                        public void onFailure(Call<FarmerActiveBidListResponse> call, Throwable t) {
                            Toast.makeText(ActivityBuyerBidsList.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    void getbids() {
        AppClient.getInstance().createService(APIServices.class)
                .getPastBidsList(
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN)
                )
                .enqueue(new Callback<List<FarmerActiveBidListResponse>>() {
                    @Override
                    public void onResponse(Call<List<FarmerActiveBidListResponse>> call, Response<List<FarmerActiveBidListResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            adapterPastBid = new AdapterPastBid(ActivityBuyerBidsList.this, response.body());
                            recyclerView.setLayoutManager(new GridLayoutManager(ActivityBuyerBidsList.this, 1));
                            recyclerView.setAdapter(adapterPastBid);
                            adapterPastBid.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FarmerActiveBidListResponse>> call, Throwable t) {
                        Toast.makeText(ActivityBuyerBidsList.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
