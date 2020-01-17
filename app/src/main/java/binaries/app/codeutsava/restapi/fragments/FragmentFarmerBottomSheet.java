package binaries.app.codeutsava.restapi.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import binaries.app.codeutsava.R;

public class FragmentFarmerBottomSheet extends BottomSheetDialogFragment {

    //Bottom Sheet Callback
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.farmer_sheet_menu,null, false);

        LinearLayout farmerReportProduce = view.findViewById(R.id.farmerReportProduce);

        farmerReportProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                FragmentFarmerFoodGrainList farmerFoodGrainList = new FragmentFarmerFoodGrainList();

//                FragmentFarmerReportProduce farmerReportProduceFragment = new FragmentFarmerReportProduce();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.farmerFragments, farmerReportProduceFragment)
//                        .addToBackStack("farmerReportProduceFragment")
//                        .commit();

                farmerFoodGrainList.show(getActivity().getSupportFragmentManager(), "farmer_produce");

            }
        });

        LinearLayout gotoProduceList = view.findViewById(R.id.farmergotoProduceList);

        gotoProduceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//
//                FragmentFarmerProduce produceList = new FragmentFarmerProduce();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.farmerFragments, produceList)
//                        .addToBackStack("none")
//                        .commit();

                FragmentFarmerProduce produce = new FragmentFarmerProduce();
                produce.show(getActivity().getSupportFragmentManager(), "farmer_produce");
            }
        });

        dialog.setContentView(view);


        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        //Set callback
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }



    }
}
