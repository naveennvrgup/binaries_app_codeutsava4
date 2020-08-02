package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyer;
import binaries.app.codeutsava.restapi.activites.ActivityFarmer;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderPayload;
import binaries.app.codeutsava.restapi.model.delivery.RequestDeliveryServicePayload;
import binaries.app.codeutsava.restapi.model.delivery.RequestDeliveryServiceResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionPayload;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import binaries.app.codeutsava.restapi.utils.Misc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static binaries.app.codeutsava.restapi.fragments.AfterDeliveryApiCalls.completeWarehouseTransactionApi;

public class AfterDeliveryDialogBox extends Dialog implements android.view.View.OnClickListener {
    public Bundle bundle;
    double quantityInput, sellingPrice, deliveryCost; //common
    TextView titleText, foodGrainText, quantityText, transactionPriceText, deliveryNameText, deliveryPriceText, totalPriceText;
    private Button yes, no;
    private int whid; //for SD
    private int produce_id, deliverServiceID; //common
    private int foodgrain_id, farmerId; //TD
    private String foodgrainName, farmer_name, farmer_contact;
    private String choice;
    private Button placeOrderButton;
    private Activity buyerActivity;
    private Activity a;
    private FragmentManager buyerFragmentManager;

    public AfterDeliveryDialogBox(Activity a, int foodgrain_id, String foodgrainName, String farmer_contact, int produce_id, double quantity, String farmer_name, double sellingPrice, String choice, int farmerId, int deliverServiceID, Bundle bundle, Button btn, Activity activity, FragmentManager fragmentManager) {
        super(a);
        this.a = a;
        this.quantityInput = quantity;
        this.farmer_contact = farmer_contact;
        this.farmer_name = farmer_name;
        this.foodgrain_id = foodgrain_id;
        this.produce_id = produce_id;
        this.foodgrainName = foodgrainName;
        this.bundle = bundle;
        this.sellingPrice = sellingPrice;
        this.choice = choice;
        this.farmerId = farmerId;
        this.deliverServiceID = deliverServiceID;
        this.placeOrderButton = btn;
        this.buyerActivity = activity;
        this.buyerFragmentManager = fragmentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.after_deliver_dialog_box);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        quantityInput = (Double) bundle.getSerializable("quantity");
        String deliverServiceName = (String) bundle.getSerializable("deliveryServiceName");
        deliveryCost = (Double) bundle.getSerializable("deliveryCost");

        transactionPriceText = findViewById(R.id.afterDeliverTransactionPrice);
        titleText = findViewById(R.id.destinationDialogName);
        quantityText = findViewById(R.id.afterDeliverFoodgrainQuantity);
        foodGrainText = findViewById(R.id.afterDeliverFoodgrainName);
        deliveryNameText = findViewById(R.id.afterDeliverName);
        deliveryPriceText = findViewById(R.id.afterDeliverPrice);
        totalPriceText = findViewById(R.id.afterDeliverTotalPrice);

        quantityText.setText(Misc.getHTML("Quantity: " + quantityInput + "kgs."));
        foodGrainText.setText(foodgrainName);
        deliveryNameText.setText("Delivery Partner: " + deliverServiceName);
        deliveryPriceText.setText(Misc.getHTML("Delivery Cost (₹): " + deliveryCost + "/-"));

        if (choice.equals("SD")) {
            String whName = (String) bundle.getSerializable("whName");
            double whPrice = (Double) bundle.getSerializable("whPrice");
            double totalCost = whPrice + deliveryCost;

            whid = (Integer) bundle.getSerializable("whid");
            produce_id = (Integer) bundle.getSerializable("produce_id");

            titleText.setText("Storing in Warehouse: " + whName);
            transactionPriceText.setText(Misc.getHTML("Warehouse Cost (₹): " + whPrice + "/-"));
            totalPriceText.setText(Misc.getHTML("Total Cost (₹): " + totalCost + "/-"));

        } else {
            titleText.setText("Buying from farmer: " + farmer_name);
            transactionPriceText.setText(Misc.getHTML("Price (₹): " + sellingPrice + "/-"));
            totalPriceText.setText(Misc.getHTML("Price (₹): " + sellingPrice + deliveryCost + "/-"));

        }
        yes = findViewById(R.id.btnConfirmDeliverTransaction);
        no = findViewById(R.id.btnCancelDeliverTransaction);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    public void onClick(View v) {
        Log.d(TAG, "choice: " + choice);
        switch (v.getId()) {
            case R.id.btnConfirmDeliverTransaction:

                if (choice.equals("SD")) {

                    RequestDeliveryServicePayload deliveryServicePayload = new RequestDeliveryServicePayload(choice, whid, deliveryCost, deliverServiceID);

                    //Submit delivery request call
                    APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
                    Call<RequestDeliveryServiceResponse> call = apiServices.submitDeliveryRequest(
                            PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", AppConstants.TEMP_FARM_TOKEN), deliveryServicePayload);

                    call.enqueue(new Callback<RequestDeliveryServiceResponse>() {
                        @Override
                        public void onResponse(Call<RequestDeliveryServiceResponse> call, Response<RequestDeliveryServiceResponse> response) {
                            FarmerWarehouseTransactionPayload payload = new FarmerWarehouseTransactionPayload();
                            payload.setProduceid(produce_id);
                            payload.setQuantiy(quantityInput);
                            payload.setWarehouseid(whid);
                            completeWarehouseTransactionApi(getContext(), payload, true);

                        }

                        @Override
                        public void onFailure(Call<RequestDeliveryServiceResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Failed to request delivery service", Toast.LENGTH_LONG).show();
                        }
                    });

                    Intent intent = new Intent(a, ActivityFarmer.class);
                    a.startActivity(intent);
                    a.finish();

                } else {

                    Log.d(TAG, "onClick: farmerId" + farmerId);
                    RequestDeliveryServicePayload deliveryServicePayload = new RequestDeliveryServicePayload(choice, farmerId, deliveryCost, deliverServiceID);

                    //Submit delivery request call
                    APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
                    Call<RequestDeliveryServiceResponse> call = apiServices.submitDeliveryRequest(
                            PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", AppConstants.TEMP_FARM_TOKEN), deliveryServicePayload);

                    call.enqueue(new Callback<RequestDeliveryServiceResponse>() {
                        @Override
                        public void onResponse(Call<RequestDeliveryServiceResponse> call, Response<RequestDeliveryServiceResponse> response) {
                            PlaceOrderPayload payload = new PlaceOrderPayload();
                            payload.farmer_contact = farmer_contact;
                            payload.foodgrain_id = foodgrain_id;
                            payload.quantity = (int) quantityInput;
                            payload.produce_id = produce_id;

                            AfterDeliveryApiCalls.completePlaceOrderTransaction(buyerActivity, buyerFragmentManager, placeOrderButton, true, payload);
                            if (getOwnerActivity() != null) {
                                Intent i = new Intent(getOwnerActivity(), ActivityBuyer.class);
                                getOwnerActivity().startActivity(i);
                                getOwnerActivity().finish();
                            } else {
                                dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<RequestDeliveryServiceResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Failed to request delivery service", Toast.LENGTH_LONG).show();
                        }
                    });

                    if (getOwnerActivity() != null) {
                        Intent i = new Intent(getOwnerActivity(), ActivityFarmer.class);
                        getOwnerActivity().startActivity(i);
                        getOwnerActivity().finish();
                    }
                }

                break;

            case R.id.btnCancelDeliverTransaction:
                dismiss();
                break;

            default:
                break;
        }

    }

}