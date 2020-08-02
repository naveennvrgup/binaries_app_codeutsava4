package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyer;
import binaries.app.codeutsava.restapi.activites.ActivityFarmer;
import binaries.app.codeutsava.restapi.adapters.AdapterActiveBid;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionPayload;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDeliveryChoice extends DialogFragment implements View.OnClickListener {

    private Button yes, no;
    private Button placeOrderButton; //buyer
    private Activity activity;
    private int whid, produce_id, foodgrainId, farmerId;
    private double quantityInput, sellingPrice;
    private String foodgrainName, choice, farmerContact, farmerName;
    Bundle bundle;
    private Activity buyerActivity;
    private FragmentManager buyerFragmentManager;

    public FragmentDeliveryChoice(int whid, double quantityInput, int foodgrainId, int produce_id, String farmerContact, String farmerName, String foodgrainName, double sellingPrice, String choice, int farmerId, Bundle bundle, Button btn, Activity activity, FragmentManager fragmentManager) {
        this.whid = whid;
        this.produce_id = produce_id;
        this.foodgrainId = foodgrainId;
        this.activity = activity;
        this.quantityInput = quantityInput;
        this.sellingPrice = sellingPrice;
        this.foodgrainName = foodgrainName;
        this.choice = choice;
        this.farmerId = farmerId;
        this.farmerContact = farmerContact;
        this.farmerName = farmerName;
        this.bundle = bundle;
        this.placeOrderButton = btn;
        this.buyerActivity = activity;
        this.buyerFragmentManager = fragmentManager;
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
        View view = inflater.inflate(R.layout.delivery_choice_box, container, false);

        yes = view.findViewById(R.id.btnYesDelivery);
        no = view.findViewById(R.id.btnNoDelivery);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnYesDelivery:
                FragmentFindDeliveryServices fragmentFindDeliveryServices = new FragmentFindDeliveryServices(activity, whid, quantityInput, foodgrainId, produce_id, farmerContact, farmerName, foodgrainName, sellingPrice, choice, farmerId, bundle, placeOrderButton, buyerActivity, buyerFragmentManager);
                fragmentFindDeliveryServices.show(getActivity().getSupportFragmentManager(), "warehouseToDelivery");

                break;

            case R.id.btnNoDelivery:
                //do api call as per choice
                if(choice.equals("SD")) {
                    FarmerWarehouseTransactionPayload payload = new FarmerWarehouseTransactionPayload();
                    payload.setProduceid(produce_id);
                    payload.setQuantiy(quantityInput);
                    payload.setWarehouseid(whid);
                    AfterDeliveryApiCalls.completeWarehouseTransactionApi(getContext(), payload, false);

                    Intent i = new Intent(getActivity(), ActivityFarmer.class);
                    getActivity().startActivity(i);
                    getActivity().finish();
                }
                else  {
                    PlaceOrderPayload payload = new PlaceOrderPayload();
                    payload.farmer_contact = farmerContact;
                    payload.foodgrain_id = foodgrainId;
                    payload.quantity = (int)quantityInput;
                    payload.produce_id = produce_id;

                    AfterDeliveryApiCalls.completePlaceOrderTransaction(buyerActivity, buyerFragmentManager, placeOrderButton, false, payload);
//                    Intent i = new Intent(getActivity(), ActivityBuyer.class);
//                    getActivity().startActivity(i);
//                    getActivity().finish();

                    dismiss();
                }

                break;

            default:
                dismiss();

        }
    }
}
