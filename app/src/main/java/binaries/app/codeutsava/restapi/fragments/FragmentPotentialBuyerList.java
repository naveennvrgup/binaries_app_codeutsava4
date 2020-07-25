package binaries.app.codeutsava.restapi.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
    private RecyclerView recyclerView;
    private AdapterPotentialBuyerList mAdapter;
    public String foodgrain;
    private ProgressBar progressBar;
    private TextView noBuyerText;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_potential_buyer, container, false);

        noBuyerText = view.findViewById(R.id.pot_buy_not_text);
        progressBar = view.findViewById(R.id.pot_buy_prog);

        recyclerView = view.findViewById(R.id.potentialBuyerListRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        foodgrain = getArguments().getSerializable("foodgrain").toString();

        view.findViewById(R.id.frag_pot_back).setOnClickListener(view1 -> dismiss());
        ((TextView) view.findViewById(R.id.frag_pot_buy_top)).setText("Potential Buyers");

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

                if(!response.isSuccessful() || response.body() == null || response.body().isEmpty())
                    noBuyerText.setVisibility(View.VISIBLE);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<PotentialBuyerResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                noBuyerText.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
