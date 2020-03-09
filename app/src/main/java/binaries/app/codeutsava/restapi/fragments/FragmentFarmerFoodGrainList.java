package binaries.app.codeutsava.restapi.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerFoodgrainList;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class FragmentFarmerFoodGrainList extends DialogFragment {
    private RecyclerView recyclerView;
    private AdapterFarmerFoodgrainList mAdapter;
    private ProgressBar progressBar;

    public FragmentFarmerFoodGrainList() {
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
        View view = inflater.inflate(R.layout.fragment_farmer_food_grain_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_farmer_foodgrain_list);
        progressBar = view.findViewById(R.id.far_food_list_progress);

        view.findViewById(R.id.far_food_list_back).setOnClickListener(view1 -> dismiss());

        callAPI();
        return view;
    }

    private void callAPI() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);

        Call<List<BuyerFoodgrainResponse>> call = apiServices.getBuyerFoodgrainList(
                PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", AppConstants.TEMP_FARM_TOKEN));

        call.enqueue(new Callback<List<BuyerFoodgrainResponse>>() {
            @Override
            public void onResponse(Call<List<BuyerFoodgrainResponse>> call, Response<List<BuyerFoodgrainResponse>> response) {
                if(getActivity() != null && getActivity().getSupportFragmentManager() != null){
                    mAdapter = new AdapterFarmerFoodgrainList(getActivity(), response.body(), getActivity().getSupportFragmentManager());

                    progressBar.setVisibility(GONE);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BuyerFoodgrainResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
