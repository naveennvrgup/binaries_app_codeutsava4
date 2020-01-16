package binaries.app.codeutsava.restapi.fragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
public class FragmentFarmerDetail extends DialogFragment {
    TextView textViewName, textViewContact, textViewAddress, textViewCity, textViewState,
            textViewDob, textViewAdhaar;

    public FragmentFarmerDetail() {
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

        if(dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farmer_detail, container, false);

        textViewName = view.findViewById(R.id.FDname);
        textViewContact = view.findViewById(R.id.FDcontact);
        textViewAddress = view.findViewById(R.id.FDaddress);
        textViewCity = view.findViewById(R.id.FDcity);
        textViewState = view.findViewById(R.id.FDstate);
        textViewDob = view.findViewById(R.id.FDdob);
        textViewAdhaar = view.findViewById(R.id.FDadhaar);
        getFarmerDetail();


        return view;
    }

    public void getFarmerDetail() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<FarmerDetailResponse> call = apiServices.getFarmerDetail();

        call.enqueue(new Callback<FarmerDetailResponse>() {
            @Override
            public void onResponse(Call<FarmerDetailResponse> call, Response<FarmerDetailResponse> response) {
                textViewName.setText(response.body().getName());
                textViewContact.setText(response.body().getContact());
                textViewAddress.setText(response.body().getAddress());
                textViewCity.setText(response.body().getCity());
                textViewState.setText(response.body().getState());
                textViewDob.setText(response.body().getDob());
                textViewAdhaar.setText(response.body().getAdhaar());
                Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<FarmerDetailResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
