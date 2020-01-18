package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterPastBid;
import binaries.app.codeutsava.restapi.fragments.CreateBidDialog;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
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

        recyclerView = findViewById(R.id.buyer_bids_recycler);
        createBidBtn = findViewById(R.id.createbidbutton);

        createBidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CreateBidDialog dialog = new CreateBidDialog(ActivityBuyerBidsList.this, ActivityBuyerBidsList.this);
//                dialog.show();
//                getbids();

                showdialog();
            }
        });

        getbids();
    }

    void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.create_bid_dialog);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    void getbids() {

        AppClient.getInstance().createService(APIServices.class)
                .getPastBidsList()
                .enqueue(new Callback<List<FarmerActiveBidListResponse>>() {
                    @Override
                    public void onResponse(Call<List<FarmerActiveBidListResponse>> call, Response<List<FarmerActiveBidListResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            adapterPastBid = new AdapterPastBid(ActivityBuyerBidsList.this, response.body());
                            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityBuyerBidsList.this));
                            recyclerView.setAdapter(adapterPastBid);
                            adapterPastBid.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FarmerActiveBidListResponse>> call, Throwable t) {
                        Toast.makeText(ActivityBuyerBidsList.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
