package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import binaries.app.codeutsava.restapi.activites.ActivityBuyerOrders;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderPayload;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterDeliveryApiCalls {
    private static String successMessage;

    public static void completeWarehouseTransactionApi(Context context, FarmerWarehouseTransactionPayload payload, boolean ifDelivery) {

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<FarmerWarehouseTransactionResponse> call = apiServices.postStorageTransaction(
                PreferenceManager.getDefaultSharedPreferences(context).getString("token", AppConstants.TEMP_FARM_TOKEN), payload);

        successMessage = "Warehouse transaction and Delivery request sent successfully";
        if(!ifDelivery) {
            successMessage = "Warehouse transaction request sent successfully";
        }

        call.enqueue(new Callback<FarmerWarehouseTransactionResponse>() {

            @Override
            public void onResponse(Call<FarmerWarehouseTransactionResponse> call, Response<FarmerWarehouseTransactionResponse> response) {
                Toast.makeText(context, successMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<FarmerWarehouseTransactionResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void completePlaceOrderTransaction(Activity activity, FragmentManager fragmentManager, Button buy, boolean ifDelivery, PlaceOrderPayload payload) {

        successMessage = "Order placed and Delivery request sent successfully!";
        if(!ifDelivery) {
            successMessage = "Order placed successfully!";
        }


        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<PlaceOrderResponse> call = apiServices.placeOrderRequest(
                PreferenceManager.getDefaultSharedPreferences(activity).getString("token", AppConstants.TEMP_FARM_TOKEN), payload);

        call.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(activity, successMessage, Toast.LENGTH_LONG).show();

                    fragmentManager.popBackStack();
                    fragmentManager.popBackStack();

                    activity.startActivity(new Intent(activity, ActivityBuyerOrders.class));
                    activity.finish();
                }
            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();

                buy.setEnabled(true);
                buy.animate()
                        .alpha(1.0f)
                        .setDuration(200)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .start();
            }
        });
    }
}
