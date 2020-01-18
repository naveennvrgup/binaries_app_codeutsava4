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

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.buyer.BidCreatePayload;
import binaries.app.codeutsava.restapi.model.buyer.BidResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBidDialog extends Dialog {
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
        quantity =  findViewById(R.id.cb_quantity);
        desc = findViewById(R.id.cb_description);
        createbidbtn = findViewById(R.id.createbidbutton);

        createbidbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apicall();
            }
        });
    }

    void apicall(){
        BidCreatePayload payload=new BidCreatePayload();
        payload.description = desc.getText().toString();
        payload.foodgrain= foodgrain.getText().toString();
        payload.quantity= Integer.parseInt(quantity.getText().toString());


        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.createBid(payload)
                .enqueue(new Callback<BidResponse>() {
                    @Override
                    public void onResponse(Call<BidResponse> call, Response<BidResponse> response) {
                        Toast.makeText(activity, "success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<BidResponse> call, Throwable t) {
                        Toast.makeText(activity, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
