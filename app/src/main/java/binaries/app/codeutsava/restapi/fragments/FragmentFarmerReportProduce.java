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
    EditText reportProducePrice, reportProduceQuantity, reportProduceGrade;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_farmer_report_produce, container, false);

        reportProduceGrade = (EditText) view.findViewById(R.id.reportProduceGrade);
        reportProducePrice = (EditText) view.findViewById(R.id.reportProducePrice);
        reportProduceQuantity = (EditText) view.findViewById(R.id.reportProduceQuantity);
        reportProduceSubmit = (TextView) view.findViewById(R.id.reportProduceSubmit);

        reportProduceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportProducePayload reportProducePayload = new ReportProducePayload();
                reportProducePayload.setFid(1);
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

            }
        });






        return view;

    }
}