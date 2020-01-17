package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterBuyerOrder;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuyerOrders extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterBuyerOrder mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_orders);

        recyclerView = findViewById(R.id.bo_recycler);

        makeApiCall();
    }

    public void makeApiCall() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<BuyerOrderListResponse>> call = apiServices.getBuyerOrders();

        call.enqueue(new Callback<List<BuyerOrderListResponse>>() {
            @Override
            public void onResponse(Call<List<BuyerOrderListResponse>> call, Response<List<BuyerOrderListResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mAdapter = new AdapterBuyerOrder(response.body(), ActivityBuyerOrders.this, getSupportFragmentManager());
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActivityBuyerOrders.this));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BuyerOrderListResponse>> call, Throwable t) {
                Toast.makeText(ActivityBuyerOrders.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
