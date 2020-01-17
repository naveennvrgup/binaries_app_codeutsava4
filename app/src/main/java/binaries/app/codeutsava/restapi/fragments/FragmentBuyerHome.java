package binaries.app.codeutsava.restapi.fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFoodgrain;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBuyerHome extends DialogFragment {
    RecyclerView recyclerView;
    AdapterFoodgrain mAdapter;

    public FragmentBuyerHome() {
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
        View view = inflater.inflate(R.layout.fragment_buyer_home, container, false);
        recyclerView = view.findViewById(R.id.BHRecyclerView);


        callAPI();
        return view;
    }


    public void callAPI() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);

        Call<List<BuyerFoodgrainResponse>> call = apiServices.getBuyerFoodgrainList();
        call.enqueue(new Callback<List<BuyerFoodgrainResponse>>() {
            @Override
            public void onResponse(Call<List<BuyerFoodgrainResponse>> call, Response<List<BuyerFoodgrainResponse>> response) {
                mAdapter = new AdapterFoodgrain(getActivity(), response.body());
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(
                        getActivity(),2,GridLayoutManager.VERTICAL,false));
                mAdapter.notifyDataSetChanged();

//                Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<BuyerFoodgrainResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}