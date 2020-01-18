package binaries.app.codeutsava.restapi.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterSuggestedWarehouse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerFindWarehouseResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class FragmentFarmerFindWarehouse extends DialogFragment {
    private RecyclerView recyclerView;
    private AdapterSuggestedWarehouse mAdapter;
    private ProgressBar progressBar;
    private int produce_id, quantity;

    FragmentFarmerFindWarehouse(int produce_id, double quantity) {
        this.produce_id = produce_id;
        this.quantity = (int) quantity + 1;
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
        View view = inflater.inflate(R.layout.fragment_farmer_find_warehouse, container, false);

        recyclerView = view.findViewById(R.id.warehouseResultRecyclerView);
        progressBar = view.findViewById(R.id.frag_far_find_ware_progress);

        view.findViewById(R.id.find_ware_back).setOnClickListener(view1 -> dismiss());

        getWarehousePrediction();
        return view;
    }

    private void getWarehousePrediction() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);

        Call<FarmerFindWarehouseResponse> call = apiServices.getFarmerFindWarehouseList(Integer.toString(produce_id), Integer.toString(quantity));
        call.enqueue(new Callback<FarmerFindWarehouseResponse>() {
            @Override
            public void onResponse(Call<FarmerFindWarehouseResponse> call, Response<FarmerFindWarehouseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(GONE);

                    mAdapter = new AdapterSuggestedWarehouse(response.body().data, produce_id, getActivity(), getActivity(), getFragmentManager());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FarmerFindWarehouseResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


}

