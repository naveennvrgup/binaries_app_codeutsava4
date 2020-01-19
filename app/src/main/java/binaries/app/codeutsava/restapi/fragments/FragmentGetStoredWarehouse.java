package binaries.app.codeutsava.restapi.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerGetStoredWarehouse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerStorageTransactionResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGetStoredWarehouse extends DialogFragment {
    RecyclerView recyclerView;
    AdapterFarmerGetStoredWarehouse mAdapter;

    public FragmentGetStoredWarehouse() {
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
        View view = inflater.inflate(R.layout.fragment_farmer_get_stored_warehouse, container, false);
        recyclerView = view.findViewById(R.id.farmerStorageTransactionListRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        view.findViewById(R.id.frag_far_prod_back).setOnClickListener(view1 -> dismiss());

        getFarmerStorageTransaction();

        return view;
    }

    private void getFarmerStorageTransaction() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<FarmerStorageTransactionResponse>> call = apiServices.getFarmerStorageTransaction(
                PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", AppConstants.TEMP_FARM_TOKEN)
        );

        call.enqueue(new Callback<List<FarmerStorageTransactionResponse>>() {
            @Override
            public void onResponse(Call<List<FarmerStorageTransactionResponse>> call, Response<List<FarmerStorageTransactionResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mAdapter = new AdapterFarmerGetStoredWarehouse(response.body(), getActivity());
                    mAdapter.setFragManager(getFragmentManager());

                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<FarmerStorageTransactionResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
