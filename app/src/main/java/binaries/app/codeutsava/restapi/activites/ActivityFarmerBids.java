package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerBids;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
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

        recyclerView = findViewById(R.id.buyer_bids_recycler);

        getbids();
    }

    void getbids(){
        AppClient.getInstance().createService(APIServices.class)
                .getFarmerActiveBidList()
                .enqueue(new Callback<List<FarmerActiveBidListResponse>>() {
                    @Override
                    public void onResponse(Call<List<FarmerActiveBidListResponse>> call, Response<List<FarmerActiveBidListResponse>> response) {
                        if(response.isSuccessful()){
                            adapterFarmerBids =  new AdapterFarmerBids(ActivityFarmerBids.this,response.body());
                            recyclerView.setAdapter(adapterFarmerBids);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFarmerBids.this));
                            adapterFarmerBids.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FarmerActiveBidListResponse>> call, Throwable t) {
                        Toast.makeText(ActivityFarmerBids.this,t.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}
