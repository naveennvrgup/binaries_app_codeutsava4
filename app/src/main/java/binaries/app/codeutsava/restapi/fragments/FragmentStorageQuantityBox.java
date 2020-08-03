package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityFarmer;
import binaries.app.codeutsava.restapi.adapters.AdapterPotentialBuyerList;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionPayload;
import binaries.app.codeutsava.restapi.model.farmer.PotentialBuyerResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.Misc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStorageQuantityBox extends DialogFragment implements View.OnClickListener {
    private Bundle bundle;
    private Button yes, no;
    private String whName, foodgrainName;
    private double whPrice, produceQuantity;
    private int whid, produce_id;
    private Activity activity;
    private TextView whPriceText, whDistanceText, whCentreText, whSectorText, whOwnerText, whAvailText, whNameText;
    private TextInputEditText produceQuantityText;

    public FragmentStorageQuantityBox(Activity activity, Bundle bundle, double quantity, String foodgrainName ) {
        this.bundle = bundle;
        this.activity = activity;
        this.produceQuantity = quantity;
        this.foodgrainName = foodgrainName;
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
        View view = inflater.inflate(R.layout.warehouse_transaction_custom_dialog_box, container, false);

        view.setBackground(new ColorDrawable(Color.TRANSPARENT));

        whName = (String) bundle.getSerializable("whname");
        whPrice = (Double) bundle.getSerializable("whprice");
        double whDistance = (Double) bundle.getSerializable("whdis");
        int whCentre = (int) bundle.getSerializable("whcentre");
        String whSector = (String) bundle.getSerializable("whSector");
        String whOwner = (String) bundle.getSerializable("whowner");
        double whAvail = (Double) bundle.getSerializable("whavl");

        whid = (Integer) bundle.getSerializable("whid");
        produce_id = (Integer) bundle.getSerializable("produce_id");

        whPriceText = view.findViewById(R.id.warehouseDialogPrice);
        whDistanceText = view.findViewById(R.id.warehouseDialogDistance);
        whCentreText = view.findViewById(R.id.warehouseDialogCentre);
        whSectorText = view.findViewById(R.id.warehouseDialogSector);
        whOwnerText = view.findViewById(R.id.warehouseDialogOwner);
        whAvailText = view.findViewById(R.id.warehouseDialogFreeSpace);
        whNameText = view.findViewById(R.id.warehouseDialogName);
        produceQuantityText = view.findViewById(R.id.storageTransactionQuantityInput);

        whNameText.setText(whName);
        whPriceText.setText(Misc.getHTML("Price (₹): " + whPrice + "/-"));
        whDistanceText.setText(Misc.getHTML("Distance: " + whDistance + "kms."));
//        whSectorText.setText(Misc.getHTML("Sector: " + whSector));
        whCentreText.setText(Misc.getHTML("Centre: " + whCentre));
        whCentreText.setVisibility(View.INVISIBLE);
        whSectorText.setVisibility(View.INVISIBLE);
        whOwnerText.setText(Misc.getHTML("Owner: " + whOwner));
        whAvailText.setText(Misc.getHTML("Available Space: " + whAvail + "kgs."));

        yes = view.findViewById(R.id.btnConfirmWarehouseTransaction);
        no = view.findViewById(R.id.btnCancelWarehouseTransaction);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirmWarehouseTransaction:
                FarmerWarehouseTransactionPayload payload = new FarmerWarehouseTransactionPayload();

                double quantityInput = Double.parseDouble(produceQuantityText.getText().toString());

                if(quantityInput<produceQuantity) {
                    payload.setProduceid(produce_id);
                    payload.setQuantiy(quantityInput);
                    payload.setWarehouseid(whid);

                    if (getActivity() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("whName", whName);
                        bundle.putSerializable("whPrice", whPrice);
                        bundle.putSerializable("whid", whid);
                        bundle.putSerializable("produce_id", produce_id);
                        bundle.putSerializable("quantity", quantityInput);

                        FragmentDeliveryChoice fragmentDeliveryChoice = new FragmentDeliveryChoice(whid, quantityInput, 1, produce_id, "", "", foodgrainName, 0, "SD", 0, bundle, null, getActivity(), null);
                        fragmentDeliveryChoice.show(getActivity().getSupportFragmentManager(), "toDeliveryServiceList");
                    }
                }
                else {
                    Toast.makeText(getActivity(), "You either entered quantity more than produce or you entered incorrect value!", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.btnCancelWarehouseTransaction:
                dismiss();
                break;

            default:
                break;
        }

        dismiss();
    }
}
