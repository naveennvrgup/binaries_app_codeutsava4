package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterBuyerOrder;
import binaries.app.codeutsava.restapi.fragments.FragmentBuyerBottomSheet;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuyerOrders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterBuyerOrder mAdapter;
    private ImageView menu;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_orders);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dashboardBg));
        }


        recyclerView = findViewById(R.id.bo_recycler);
        progressBar = findViewById(R.id.act_buy_order_progress);
        menu = findViewById(R.id.bo_buyer_menu_icon);

        menu.setOnClickListener(view -> {
            BottomSheetDialogFragment bottomSheetDialogFragment = new FragmentBuyerBottomSheet();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "buyerBottomSheet");
        });

        makeApiCall();
    }

    public void makeApiCall() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<BuyerOrderListResponse>> call = apiServices.getBuyerOrders(
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN));

        call.enqueue(new Callback<List<BuyerOrderListResponse>>() {
            @Override
            public void onResponse(Call<List<BuyerOrderListResponse>> call, Response<List<BuyerOrderListResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    progressBar.setVisibility(View.GONE);

                    mAdapter = new AdapterBuyerOrder(response.body(), ActivityBuyerOrders.this, getSupportFragmentManager());
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActivityBuyerOrders.this));

                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BuyerOrderListResponse>> call, Throwable t) {
                Toast.makeText(ActivityBuyerOrders.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
