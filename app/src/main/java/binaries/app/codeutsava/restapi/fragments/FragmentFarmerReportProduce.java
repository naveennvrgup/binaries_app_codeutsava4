package binaries.app.codeutsava.restapi.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterProduce;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;
import binaries.app.codeutsava.restapi.model.farmer.ReportProducePayload;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class  FragmentFarmerReportProduce extends DialogFragment {
    TextView reportProduceSubmit;
    TextInputEditText reportProducePrice, reportProduceQuantity, reportProduceGrade;

    public FragmentFarmerReportProduce() {

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_farmer_report_produce, container, false);

        int foodgrainId = (Integer) getArguments().getSerializable("id");

        reportProduceGrade = view.findViewById(R.id.reportProduceGrade);
        reportProducePrice = view.findViewById(R.id.reportProducePrice);
        reportProduceQuantity = view.findViewById(R.id.reportProduceQuantity);
        reportProduceSubmit = view.findViewById(R.id.reportProduceSubmit);

        view.findViewById(R.id.farmer_report_prod_back).setOnClickListener(view1 -> dismiss());

        reportProduceSubmit.setOnClickListener(v -> {

            ReportProducePayload reportProducePayload = new ReportProducePayload();
            reportProducePayload.setFid(foodgrainId);
            reportProducePayload.setGrade(reportProduceGrade.getText().toString());
            reportProducePayload.setPrice(Double.parseDouble(reportProducePrice.getText().toString()));
            reportProducePayload.setQuantity(Double.parseDouble(reportProduceQuantity.getText().toString()));
            APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
            Call<FarmerProduceResponse> call = apiServices.postFarmerProduce(reportProducePayload);

            call.enqueue(new Callback<FarmerProduceResponse>() {
                @Override
                public void onResponse(Call<FarmerProduceResponse> call, Response<FarmerProduceResponse> response) {
                    Bundle args = new Bundle();
                    FarmerProduceResponse produce = response.body();
                    args.putSerializable("produce", produce);

                    FragmentFarmerProduceDetail produceDetail = new FragmentFarmerProduceDetail();
                    produceDetail.setArguments(args);

                    produceDetail.show(getFragmentManager(), "produce");

                }

                @Override
                public void onFailure(Call<FarmerProduceResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        });

        return view;
    }
}