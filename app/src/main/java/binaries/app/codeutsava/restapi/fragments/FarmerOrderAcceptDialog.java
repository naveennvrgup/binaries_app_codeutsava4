package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerOrder;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;

public class FarmerOrderAcceptDialog extends Dialog {

    private Button fromproduce, fromwarehouse;
    AdapterFarmerOrder adapterFarmerOrder;
    OnCustomClickListener onCustomClickListener;

    public void setOnCustomClickListener(OnCustomClickListener onCustomClickListener) {
        this.onCustomClickListener = onCustomClickListener;
    }

    public interface OnCustomClickListener {
        void onClick(String get_from);
    }

    public FarmerOrderAcceptDialog(Activity a, List<BuyerOrderListResponse> orders, int order_id, int position, AdapterFarmerOrder adapterFarmerOrder) {
        super(a);
        this.adapterFarmerOrder = adapterFarmerOrder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.farmer_accept_dialog);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        fromproduce = findViewById(R.id.ao_fromproduce);
        fromwarehouse = findViewById(R.id.ao_fromwarehouse);
        fromproduce.setOnClickListener(v -> onCustomClickListener.onClick("produce"));
        fromwarehouse.setOnClickListener(v -> onCustomClickListener.onClick("warehouse"));
    }
}