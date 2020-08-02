package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyer;
import binaries.app.codeutsava.restapi.adapters.AdapterDeliveryServiceList;
import binaries.app.codeutsava.restapi.adapters.AdapterSuggestedWarehouse;
import binaries.app.codeutsava.restapi.model.delivery.FindDeliveryServicePayload;
import binaries.app.codeutsava.restapi.model.delivery.FindDeliveryServiceResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class FragmentFindDeliveryServices extends DialogFragment {
    private RecyclerView recyclerView;
    private AdapterDeliveryServiceList mAdapter;
    private ProgressBar progressBar;
    private TextView noDeliverServiceText;
    private int destinationId;
    private int foodgrainId, produceId, farmerId;
    private  String farmerContact, farmer_name, foodgrainName, choice;
    private double sellingPrice,  quantity, produceQuantity;
    private Bundle bundle;

    private Button placeOrderButton;
    private Activity buyerActivity;
    private FragmentManager buyerFragmentManager;


    public FragmentFindDeliveryServices(int destinationId, double quantity, int foodgrainId, int produceId, String farmerContact, String farmer_name, String foodgrainName, double sellingPrice, String choice, int farmerId, Bundle bundle, Button btn, Activity activity, FragmentManager fragmentManager) {
        this.destinationId = destinationId;
        this.quantity = quantity;
        this.foodgrainId = foodgrainId;
        this.produceId = produceId;
        this.farmerContact = farmerContact;
        this.farmer_name = farmer_name;
        this.foodgrainName = foodgrainName;
        this.sellingPrice = sellingPrice;
        this.bundle = bundle;
        this.choice = choice;
        this.farmerId = farmerId;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_delivery_services, container, false);

        recyclerView = view.findViewById(R.id.deiveryServiceListRecyclerView);
        progressBar = view.findViewById(R.id.frag_find_delivery_service_progress);
        noDeliverServiceText = view.findViewById(R.id.no_delivery_service_found_text);

        view.findViewById(R.id.find_delivery_back).setOnClickListener(view1 -> dismiss());

        getDeliveryList();
        return view;
    }

    private void getDeliveryList() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);

        if(choice.equals("TD")) {
            destinationId = farmerId;
        }

        FindDeliveryServicePayload payload = new FindDeliveryServicePayload(destinationId, choice, farmerContact);

        Call<FindDeliveryServiceResponse> call = apiServices.getDeliveryServiceList(
                PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", AppConstants.TEMP_FARM_TOKEN), payload );

        call.enqueue(new Callback<FindDeliveryServiceResponse>() {
            @Override
            public void onResponse(Call<FindDeliveryServiceResponse> call, Response<FindDeliveryServiceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(GONE);

                    mAdapter = new AdapterDeliveryServiceList(response.body().deliveryServiceList,
                            getActivity(),
                            getActivity(),
                            getFragmentManager(),
                            destinationId,
                            choice,
                            response.body().destinationId,
                            foodgrainId,
                            farmerContact,
                            quantity,
                            produceId,
                            farmer_name,
                            foodgrainName,
                            sellingPrice,
                            bundle,
                            placeOrderButton,
                            buyerActivity,
                            buyerFragmentManager);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

                if (!response.isSuccessful() || response.body() == null || response.body().deliveryServiceList.isEmpty())
                    noDeliverServiceText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<FindDeliveryServiceResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                noDeliverServiceText.setVisibility(View.VISIBLE);
            }
        });
    }
}
