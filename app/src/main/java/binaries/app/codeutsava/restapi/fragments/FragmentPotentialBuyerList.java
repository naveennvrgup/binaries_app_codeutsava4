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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterPotentialBuyerList;
import binaries.app.codeutsava.restapi.model.farmer.PotentialBuyerResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPotentialBuyerList extends DialogFragment {
    RecyclerView recyclerView;
    AdapterPotentialBuyerList mAdapter;
    public String foodgrain;

    public FragmentPotentialBuyerList() {
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
        View view = inflater.inflate(R.layout.fragment_farmer_produce, container, false);
        recyclerView = view.findViewById(R.id.potentialBuyerListRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        foodgrain = getArguments().getSerializable("foodgrain").toString();

        view.findViewById(R.id.frag_far_prod_back).setOnClickListener(view1 -> dismiss());

        getPotentialBuyerList();

        return view;
    }

    private void getPotentialBuyerList() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<PotentialBuyerResponse>> call = apiServices.getPotentialBuyerList(foodgrain);

        call.enqueue(new Callback<List<PotentialBuyerResponse>>() {
            @Override
            public void onResponse(Call<List<PotentialBuyerResponse>> call, Response<List<PotentialBuyerResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mAdapter = new AdapterPotentialBuyerList(response.body(), getActivity());
                    mAdapter.setFragManager(getFragmentManager());

                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PotentialBuyerResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
