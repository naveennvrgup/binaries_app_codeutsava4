package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.google.android.material.textfield.TextInputEditText;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityFarmer;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import binaries.app.codeutsava.restapi.utils.Misc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {
    public Bundle bundle;
    private Button yes, no;
    private int whid, produce_id;
    private TextView whPriceText, whDistanceText, whCentreText, whSectorText, whOwnerText, whAvailText, whNameText, whFacilities;
    private TextInputEditText produceQuantity;

    public CustomDialogClass(Activity a, Bundle bundle) {
        super(a);
        this.bundle = bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.warehouse_transaction_custom_dialog_box);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String whName = (String) bundle.getSerializable("whname");
        double whPrice = (Double) bundle.getSerializable("whprice");
        double whDistance = (Double) bundle.getSerializable("whdis");
        int whCentre = (int) bundle.getSerializable("whcentre");
        String whSector = (String) bundle.getSerializable("whSector");
        String whOwner = (String) bundle.getSerializable("whowner");
        double whAvail = (Double) bundle.getSerializable("whavl");
        String whDescription= (String) bundle.getSerializable("facilities");

        whid = (Integer) bundle.getSerializable("whid");
        produce_id = (Integer) bundle.getSerializable("produce_id");

        whPriceText = findViewById(R.id.warehouseDialogPrice);
        whDistanceText = findViewById(R.id.warehouseDialogDistance);
        whCentreText = findViewById(R.id.warehouseDialogCentre);
        whSectorText = findViewById(R.id.warehouseDialogSector);
        whOwnerText = findViewById(R.id.warehouseDialogOwner);
        whAvailText = findViewById(R.id.warehouseDialogFreeSpace);
        whNameText = findViewById(R.id.warehouseDialogName);
        produceQuantity = findViewById(R.id.storageTransactionQuantityInput);
        whFacilities = findViewById(R.id.warehouseDialogFacilities);

        whNameText.setText(whName);
        whPriceText.setText(Misc.getHTML("Price (₹): " + whPrice + "/-"));
        whDistanceText.setText(Misc.getHTML("Distance: " + whDistance + "kms."));
        whSectorText.setText(Misc.getHTML("Sector: " + whSector));
        whCentreText.setText(Misc.getHTML("Centre: " + whCentre));
        whOwnerText.setText(Misc.getHTML("Owner: " + whOwner));
        whAvailText.setText(Misc.getHTML("Available Space: " + whAvail + "kgs."));
        whFacilities.setText(Misc.getHTML("Facilities: \n" + whDescription));

        yes = findViewById(R.id.btnConfirmWarehouseTransaction);
        no = findViewById(R.id.btnCancelWarehouseTransaction);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirmWarehouseTransaction:
                FarmerWarehouseTransactionPayload payload = new FarmerWarehouseTransactionPayload();

                double quantityInput = Double.parseDouble(produceQuantity.getText().toString());
                payload.setProduceid(produce_id);
                payload.setQuantiy(quantityInput);
                payload.setWarehouseid(whid);

                APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
                Call<FarmerWarehouseTransactionResponse> call = apiServices.postStorageTransaction(
                        PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", AppConstants.TEMP_FARM_TOKEN), payload);

                call.enqueue(new Callback<FarmerWarehouseTransactionResponse>() {
                    @Override
                    public void onResponse(Call<FarmerWarehouseTransactionResponse> call, Response<FarmerWarehouseTransactionResponse> response) {
                        Toast.makeText(getContext(), "Warehouse transaction request sent successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<FarmerWarehouseTransactionResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                if(getOwnerActivity() != null){
                    Intent i = new Intent(getOwnerActivity(), ActivityFarmer.class);
                    getOwnerActivity().startActivity(i);
                    getOwnerActivity().finish();
                }

                break;

            case R.id.btnCancelWarehouseTransaction:
                dismiss();
                break;

            default:
                break;
        }

        dismiss();
    }
}