package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityFarmer;
import binaries.app.codeutsava.restapi.activites.ActivitySplashScreen;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Bundle bundle;
    public Dialog d;
    public Button yes, no;
    public int whid, produce_id;
    TextView whPriceText, whDistanceText, whCentreText, whSectorText, whOwnerText, whAvailText, whNameText;
    EditText produceQuantity;

    public CustomDialogClass(Activity a, Bundle bundle) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.bundle = bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        String whName = (String) bundle.getSerializable("whname");
        double whPrice = (Double) bundle.getSerializable("whprice");
        double whDistance = (Double) bundle.getSerializable("whdis");
        int whCentre = (int) bundle.getSerializable("whcentre");
        String whSector = (String) bundle.getSerializable("whSector");
        String whOwner = (String) bundle.getSerializable("whowner");
        double whAvail = (Double) bundle.getSerializable("whavl");
        whid = (Integer) bundle.getSerializable("whid");
        produce_id = (Integer) bundle.getSerializable("produce_id");

        setContentView(R.layout.warehouse_transaction_custom_dialog_box);

        whPriceText = (TextView) findViewById(R.id.warehouseDialogPrice);
        whDistanceText = (TextView) findViewById(R.id.warehouseDialogDistance);
        whCentreText = (TextView) findViewById(R.id.warehouseDialogCentre);
        whSectorText = (TextView) findViewById(R.id.warehouseDialogSector);
        whOwnerText = (TextView) findViewById(R.id.warehouseDialogOwner);
        whAvailText = (TextView) findViewById(R.id.warehouseDialogFreeSpace);
        whNameText = (TextView) findViewById(R.id.warehouseDialogName);
        produceQuantity = (EditText) findViewById(R.id.storageTransactionQuantityInput);

        whNameText.setText("Warehouse Name: "+whName);
        whPriceText.setText("Price: "+Double.toString(whPrice));
        whDistanceText.setText("Distance: "+Double.toString(whDistance));
        whSectorText.setText("Sector: "+whSector);
        whCentreText.setText("Centre:"+Integer.toString(whCentre));
        whOwnerText.setText("Owner: "+whOwner);
        whAvailText.setText("Available Space: "+whAvail);

        yes = (Button) findViewById(R.id.btnConfirmWarehouseTransaction);
        no = (Button) findViewById(R.id.btnCancelWarehouseTransaction);
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
                Call<FarmerWarehouseTransactionResponse> call = apiServices.postStorageTransaction(payload);
                call.enqueue(new Callback<FarmerWarehouseTransactionResponse>() {
                    @Override
                    public void onResponse(Call<FarmerWarehouseTransactionResponse> call, Response<FarmerWarehouseTransactionResponse> response) {
                        Toast.makeText(getContext(),"Warehouse transaction request sent successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<FarmerWarehouseTransactionResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

//                Toast.makeText(getContext(),"Warehouse transaction request sent successfully", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(ActivitySplashScreen.this, ActivityFarmer.class));

                Intent i = new Intent(c, ActivityFarmer.class);
                c.startActivity(i);
                c.finish();
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