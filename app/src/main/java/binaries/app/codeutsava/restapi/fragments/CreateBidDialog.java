package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.buyer.BidCreatePayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBidDialog extends Dialog implements android.view.View.OnClickListener {
    Activity activity;
    EditText foodgrain, quantity, desc;
    Button createbidbtn;

    public CreateBidDialog(@NonNull Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_bid_dialog);

        foodgrain = findViewById(R.id.cb_foodgrain);
        quantity = findViewById(R.id.cb_quantity);
        desc = findViewById(R.id.cb_description);
        createbidbtn = findViewById(R.id.buyercreatebidbutton);

        createbidbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buyercreatebidbutton) {
            BidCreatePayload payload = new BidCreatePayload();
            payload.description = desc.getText().toString();
            payload.foodgrain = foodgrain.getText().toString();
            payload.quantity = quantity.getText().toString();

            APIServices services = AppClient.getInstance().createService(APIServices.class);
            Call<FarmerActiveBidListResponse> call = services.createBid(payload);

            call.enqueue(new Callback<FarmerActiveBidListResponse>() {
                @Override
                public void onResponse(Call<FarmerActiveBidListResponse> call, Response<FarmerActiveBidListResponse> response) {
                    Toast.makeText(activity, "success", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<FarmerActiveBidListResponse> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
