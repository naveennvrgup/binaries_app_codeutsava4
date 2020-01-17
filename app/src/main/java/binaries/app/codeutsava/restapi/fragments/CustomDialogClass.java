package binaries.app.codeutsava.restapi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import binaries.app.codeutsava.R;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Bundle bundle;
    public Dialog d;
    public Button yes, no;
    TextView whPriceText, whDistanceText, whCentreText, whSectorText, whOwnerText, whAvailText, whNameText;

    public CustomDialogClass(Activity a, Bundle bundle) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.bundle = bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

//        args.putSerializable("whid", currWarehouse.whid);
//        args.putSerializable("whdis",currWarehouse.distance);
//        args.putSerializable("whprice",currWarehouse.price);
//        args.putSerializable("whcentre",currWarehouse.centre);
//        args.putSerializable("whsector",currWarehouse.sector);
//        args.putSerializable("whowner",currWarehouse.owner);
//        args.putSerializable("whavl",currWarehouse.availstorage);

        String whName = (String) bundle.getSerializable("whname");
        double whPrice = (Double) bundle.getSerializable("whprice");
        double whDistance = (Double) bundle.getSerializable("whdis");
        int whCentre = (int) bundle.getSerializable("whcentre");
        String whSector = (String) bundle.getSerializable("whSector");
        String whOwner = (String) bundle.getSerializable("whowner");
        double whAvail = (Double) bundle.getSerializable("whavl");



        setContentView(R.layout.warehouse_transaction_custom_dialog_box);

        whPriceText = (TextView) findViewById(R.id.warehouseDialogPrice);
        whDistanceText = (TextView) findViewById(R.id.warehouseDialogDistance);
        whCentreText = (TextView) findViewById(R.id.warehouseDialogCentre);
        whSectorText = (TextView) findViewById(R.id.warehouseDialogSector);
        whOwnerText = (TextView) findViewById(R.id.warehouseDialogOwner);
        whAvailText = (TextView) findViewById(R.id.warehouseDialogFreeSpace);
        whNameText = (TextView) findViewById(R.id.warehouseDialogName);

        whNameText.setText("Warehouse Name: "+whName);
        whPriceText.setText("Price: "+Double.toString(whPrice));
        whDistanceText.setText("Distance: "+Double.toString(whDistance));
        whSectorText.setText("Sector: "+whSector);
        whCentreText.setText("Centre:"+Integer.toString(whCentre));
        whOwnerText.setText("Owner: "+whOwner);
        whAvailText.setText("Available Space: "+whAvail);

        yes = (Button) findViewById(R.id.btnConfirmWarehouseTransaction);
        no = (Button) findViewById(R.id.btnCancelWarehouseTransaction);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirmWarehouseTransaction:
                c.finish();
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