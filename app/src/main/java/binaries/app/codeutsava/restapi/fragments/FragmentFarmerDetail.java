package binaries.app.codeutsava.restapi.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDetailResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFarmerDetail extends Fragment {


    public FragmentFarmerDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getFarmerDetail();


        return inflater.inflate(R.layout.fragment_farmer_detail, container, false);
    }

    public void getFarmerDetail() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<FarmerDetailResponse> call = apiServices.getFarmerDetail();

        call.enqueue(new Callback<FarmerDetailResponse>() {
            @Override
            public void onResponse(Call<FarmerDetailResponse> call, Response<FarmerDetailResponse> response) {
                Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<FarmerDetailResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
