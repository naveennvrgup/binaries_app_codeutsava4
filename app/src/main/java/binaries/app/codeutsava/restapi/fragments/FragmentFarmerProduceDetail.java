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

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;
import binaries.app.codeutsava.restapi.utils.Misc;

public class FragmentFarmerProduceDetail extends DialogFragment {

    private TextView produceFoodgrain, producePrice, produceDate, produceGrade, produceQuantity, produceLife;
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
        FarmerProduceResponse produce = (FarmerProduceResponse) getArguments().getSerializable("produce");
        View productDetailView = inflater.inflate(R.layout.fragment_farmer_produce_detail, container, false);

        producePrice = productDetailView.findViewById(R.id.producePrice);
        produceDate = productDetailView.findViewById(R.id.produceDate);
        produceGrade = productDetailView.findViewById(R.id.produceGrade);
        produceFoodgrain = productDetailView.findViewById(R.id.produceFoodgrainName);
        produceQuantity = productDetailView.findViewById(R.id.produceQuantity);
        produceLife = productDetailView.findViewById(R.id.produceLife);
        findWarehouseButton = productDetailView.findViewById(R.id.findWarehousebutton);

        if (produce != null) {
            // setting the texts
            producePrice.setText(produce.price + "/-");
            produceDate.setText(produce.date);
            produceFoodgrain.setText(Misc.getUpperForm(produce.type.type));
            produceGrade.setText(produce.grade);
            produceQuantity.setText(produce.quantity + " kgs.");
            produceLife.setText(produce.type.life + "days");
        }

        productDetailView.findViewById(R.id.frag_far_prod_det_back).setOnClickListener(view -> dismiss());
        productDetailView.findViewById(R.id.gotoMainButton).setOnClickListener(view -> dismiss());

        findWarehouseButton.setOnClickListener(v -> {
            FragmentFarmerFindWarehouse findWareHouseFragment = new FragmentFarmerFindWarehouse(Integer.parseInt(produce.id), Double.parseDouble(produce.quantity));
            findWareHouseFragment.show(getActivity().getSupportFragmentManager(), "warehouse");
        });

        return productDetailView;
    }
}
