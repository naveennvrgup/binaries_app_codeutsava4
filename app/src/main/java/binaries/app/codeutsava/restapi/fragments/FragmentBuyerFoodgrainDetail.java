package binaries.app.codeutsava.restapi.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmer;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.model.buyer.FarmerResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBuyerFoodgrainDetail extends DialogFragment {
    private RecyclerView recyclerView;
    private AdapterFarmer mAdapter;
    private TextView fg_name, fg_price;
    private ImageView fg_img;
    private TextInputEditText editTextQuantity;
    private CardView setQuantityBtn;
    private BuyerFoodgrainResponse foodgrain;
    private ProgressBar progressBar;
    private int quantity;

    public FragmentBuyerFoodgrainDetail() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_buyer_foodgrain_detail, container, false);
        recyclerView = view.findViewById(R.id.buyer_food_det_recycler_view);

        fg_name = view.findViewById(R.id.name);
        fg_img = view.findViewById(R.id.food_img);
        fg_price = view.findViewById(R.id.price);
        editTextQuantity = view.findViewById(R.id.quantity_input);
        setQuantityBtn = view.findViewById(R.id.setQuantitybtn);
        progressBar = view.findViewById(R.id.buyer_foodgrain_det_progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        setQuantityBtn.setOnClickListener(v -> {
            quantity = Integer.parseInt(editTextQuantity.getText().toString());
            progressBar.setVisibility(View.VISIBLE);
            callAPI();
        });


        foodgrain = (BuyerFoodgrainResponse) getArguments().getSerializable("foodgrain");

        if (foodgrain != null && getActivity() != null) {
            fg_name.setText(foodgrain.type);
            fg_price.setText(foodgrain.price);

            Glide.with(getActivity()).load(R.drawable.f8).into(fg_img);
        }

        return view;
    }


    private void callAPI() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<FarmerResponse>> call = apiServices.getBuyerFarmerList(foodgrain.id);

        call.enqueue(new Callback<List<FarmerResponse>>() {
            @Override
            public void onResponse(Call<List<FarmerResponse>> call, Response<List<FarmerResponse>> response) {
                if (response.isSuccessful() && response.body() != null && getActivity() != null) {

                    progressBar.setVisibility(View.GONE);

                    List<FarmerResponse> filteredFarmers = new ArrayList<>();

                    for (FarmerResponse farmer : response.body()) {
                        if (farmer.quantity >= quantity)
                            filteredFarmers.add(farmer);
                    }

                    mAdapter = new AdapterFarmer(getActivity().getSupportFragmentManager(), foodgrain.id, filteredFarmers, getActivity(), foodgrain, quantity);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<FarmerResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
