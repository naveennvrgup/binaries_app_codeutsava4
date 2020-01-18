package binaries.app.codeutsava.restapi.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;

public class FragmentFarmerProduceDetail extends DialogFragment {

    private TextView produceFoodgrain, producePrice, produceDate, produceGrade, produceQuantity;
    private Button findWarehouseButton;

    public FragmentFarmerProduceDetail() {
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
        /* Inflate the layout for this fragment */

        FarmerProduceResponse produce = (FarmerProduceResponse) getArguments().getSerializable("produce");

        View productDetailView = inflater.inflate(R.layout.fragment_fragment_farmer_produce_detail, container, false);
        producePrice = productDetailView.findViewById(R.id.producePrice);
        produceDate = productDetailView.findViewById(R.id.produceDate);
        produceGrade = productDetailView.findViewById(R.id.produceGrade);
        produceFoodgrain = productDetailView.findViewById(R.id.produceFoodgrainName);
        produceQuantity = productDetailView.findViewById(R.id.produceQuantity);
        findWarehouseButton = productDetailView.findViewById(R.id.findWarehousebutton);

        //setting the texts
        producePrice.setText("Price: " + produce.price);
        produceDate.setText("Date: " + produce.date);
        produceFoodgrain.setText("FoodGrain: " + produce.type.type);
        produceGrade.setText("Grade: " + produce.grade);
        produceQuantity.setText("Quantity: " + produce.quantity);

        findWarehouseButton.setOnClickListener(v -> {

            FragmentFarmerFindWarehouse findWarehousefragment = new FragmentFarmerFindWarehouse(Integer.parseInt(produce.id),Double.parseDouble(produce.quantity));
//
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .remove(getActivity().getSupportFragmentManager().beginTransaction().)

            findWarehousefragment.show(getActivity().getSupportFragmentManager(), "warehouse");
        });

        return productDetailView;
    }
}
