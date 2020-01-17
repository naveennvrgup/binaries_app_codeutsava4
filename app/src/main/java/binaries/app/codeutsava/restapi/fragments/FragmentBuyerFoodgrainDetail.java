package binaries.app.codeutsava.restapi.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
    RecyclerView recyclerView;
    AdapterFarmer mAdapter;
    TextView fg_name,fg_price;
    ImageView fg_img;
    BuyerFoodgrainResponse foodgrain;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_foodgrain_detail, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        fg_name=view.findViewById(R.id.name);
        fg_img=view.findViewById(R.id.food_img);
        fg_price=view.findViewById(R.id.price);

        foodgrain=(BuyerFoodgrainResponse) getArguments().getSerializable("foodgrain");
        fg_name.setText(foodgrain.type);
        fg_price.setText(foodgrain.price);
        Glide.with(getActivity())
                .load(R.drawable.f8)
                .into(fg_img);

        callAPI();
        return view;
    }


    public void callAPI() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);

        Call<List<FarmerResponse>> call = apiServices.getBuyerFarmerList(foodgrain.id);
        call.enqueue(new Callback<List<FarmerResponse>>() {
            @Override
            public void onResponse(Call<List<FarmerResponse>> call, Response<List<FarmerResponse>> response) {
                mAdapter = new AdapterFarmer(getActivity().getSupportFragmentManager(),
                        response.body(),getActivity(),foodgrain);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(
                        getActivity(),1,GridLayoutManager.VERTICAL,false));
                mAdapter.notifyDataSetChanged();

//                Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<FarmerResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
