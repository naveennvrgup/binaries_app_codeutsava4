package binaries.app.codeutsava.restapi.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterRecyclerViewBottomSheet;

public class FragmentBuyerBottomSheet extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private List<AdapterRecyclerViewBottomSheet.Items> itemsList = new ArrayList<>();

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

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.buyer_sheet_menu, null, false);

        dialog.setContentView(view);

        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        //Set callback
        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        itemsList.add(new AdapterRecyclerViewBottomSheet.Items("Home"));
        itemsList.add(new AdapterRecyclerViewBottomSheet.Items("My Bids"));
        itemsList.add(new AdapterRecyclerViewBottomSheet.Items("Orders"));
        itemsList.add(new AdapterRecyclerViewBottomSheet.Items("About Us"));
        itemsList.add(new AdapterRecyclerViewBottomSheet.Items("Log Out"));

        recyclerView = dialog.findViewById(R.id.recycler_buyer_bottom_sheet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(new AdapterRecyclerViewBottomSheet(getActivity(), itemsList));
    }
}
