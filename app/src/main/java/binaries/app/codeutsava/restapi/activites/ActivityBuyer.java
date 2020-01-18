package binaries.app.codeutsava.restapi.activites;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterBuyerTop;
import binaries.app.codeutsava.restapi.adapters.AdapterFoodgrain;
import binaries.app.codeutsava.restapi.fragments.FragmentBuyerBottomSheet;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuyer extends AppCompatActivity {

    private RecyclerView recyclerViewTop, recyclerViewList;
    private AdapterFoodgrain adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dashboardBg));
        }

        initViews();
        callAPI();
    }

    private void initViews() {
        recyclerViewTop = findViewById(R.id.buyer_tab_strip);
        recyclerViewTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTop.setAdapter(new AdapterBuyerTop(this));
        recyclerViewTop.setNestedScrollingEnabled(true);
        recyclerViewTop.setHasFixedSize(true);
        recyclerViewTop.setAdapter(new AdapterBuyerTop(this));

        recyclerViewList = findViewById(R.id.recycler_buyer_list);
        recyclerViewList.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewList.setAdapter(new AdapterFoodgrain(this, null, null));

        findViewById(R.id.buyer_menu_icon).setOnClickListener(view -> {
            BottomSheetDialogFragment bottomSheetDialogFragment = new FragmentBuyerBottomSheet();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "buyerBottomSheet");
        });
    }


    public void callAPI() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);

        Call<List<BuyerFoodgrainResponse>> call = apiServices.getBuyerFoodgrainList(
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN));

        call.enqueue(new Callback<List<BuyerFoodgrainResponse>>() {
            @Override
            public void onResponse(Call<List<BuyerFoodgrainResponse>> call, Response<List<BuyerFoodgrainResponse>> response) {

                Log.d("DEBUG", "API CALL SUCCESS");

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        findViewById(R.id.act_buy_progress).setVisibility(View.GONE);

                        adapter = new AdapterFoodgrain(ActivityBuyer.this, response.body(), getSupportFragmentManager());
                        recyclerViewList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BuyerFoodgrainResponse>> call, Throwable t) {
                Toast.makeText(ActivityBuyer.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
