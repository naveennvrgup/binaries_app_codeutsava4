package binaries.app.codeutsava.restapi.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.utils.Misc;

public class FragmentBuyerOrderDetailView extends DialogFragment {

    private boolean delivered = false, ifDeliveryService = false;
    private Bundle bundle;
    private ProgressBar progressBar;
    private int transactionSaleId, logisticVerified = 0;


    public FragmentBuyerOrderDetailView(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView foodgrainNameText, sellerNameText, quantityText, priceText, approvedText, logisticText, deliveredText;
        View view = inflater.inflate(R.layout.fragment_buyer_order_detail_view, container, false);

        transactionSaleId = (Integer) bundle.getSerializable("transactionSaleId");
        String sellerName = (String) bundle.getSerializable("sellerName");
        String quantity = Integer.toString((Integer) bundle.getSerializable("quantity"));
        String price = Integer.toString((Integer) bundle.getSerializable("price"));
        String foodgrainName = (String) bundle.getSerializable("foodgrainName");
        Boolean approved = (Boolean) bundle.getSerializable("approved");

        progressBar = view.findViewById(R.id.buyer_order_detail_progress);

        foodgrainNameText = (TextView) view.findViewById(R.id.orderDetailFoodgrainName);
        sellerNameText = (TextView) view.findViewById(R.id.orderDetailSellerName);
        quantityText = (TextView) view.findViewById(R.id.orderDetailQuantity);
        priceText = (TextView) view.findViewById(R.id.orderDetailPrice);
        approvedText = (TextView) view.findViewById(R.id.orderDetailApproved);
        logisticText = (TextView) view.findViewById(R.id.orderDetailLogisticVerified);
        deliveredText = (TextView) view.findViewById(R.id.orderDetailDelivered);

        foodgrainNameText.setText(foodgrainName);
        sellerNameText.setText(sellerName);
        priceText.setText(Misc.getHTML("Cost (₹): " + price + "/-"));
        quantityText.setText(Misc.getHTML("Quantity: " + quantity + " kgs."));

        view.findViewById(R.id.frag_order_det_back).setOnClickListener(view1 -> dismiss());


        //do api call here

        if (approved) {
            approvedText.setText("Your order has been accepted by the farmer!");
        } else {
            approvedText.setText("Your order is not yet approved!");
        }

        if (ifDeliveryService) {
            if (logisticVerified == 1)
                logisticText.setText("Delivery verified and recieved by logistic partner");
            else if (logisticVerified == 2)
                logisticText.setText("Oops there was some issue with the order. Please order again from different seller");
            else
                logisticText.setText("Our delivery partner will shortly verify the delivery!");
        } else {
            if (approved) {
                logisticText.setText("Since you have not opted for home delivery, you can collect it on your own");
            } else {
                logisticText.setText("You can collect the delivery once your order is approved");
            }

            deliveredText.setVisibility(View.INVISIBLE);
        }

        int count = 0;
        if (approved) count++;
        if (delivered) count++;
        if (ifDeliveryService && logisticVerified != 0) count++;

        if (logisticVerified == 2)
            progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        else
            progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        progressBar.setProgress((100 * count) / 3);

        return view;
    }
}


//    private void callAPI() {
//        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
//        Call<List<FarmerResponse>> call = apiServices.getBuyerFarmerList(
//                PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", AppConstants.TEMP_FARM_TOKEN), foodgrain.id);
//
//        call.enqueue(new Callback<List<FarmerResponse>>() {
//            @Override
//            public void onResponse(Call<List<FarmerResponse>> call, Response<List<FarmerResponse>> response) {
//                if (response.isSuccessful() && response.body() != null && getActivity() != null) {
//
//                    progressBar.setVisibility(GONE);
//                    searchText.setVisibility(View.VISIBLE);
//
//                    List<FarmerResponse> filteredFarmers = new ArrayList<>();
//
//                    for (FarmerResponse farmer : response.body()) {
//                        if (farmer.quantity >= quantity)
//                            filteredFarmers.add(farmer);
//                    }
//
//                    mAdapter.setData(foodgrain.id, filteredFarmers, quantity);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<FarmerResponse>> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//}
