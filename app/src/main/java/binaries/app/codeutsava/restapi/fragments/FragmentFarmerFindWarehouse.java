package binaries.app.codeutsava.restapi.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import binaries.app.codeutsava.R;

import binaries.app.codeutsava.restapi.adapters.AdapterSuggestedWarehouse;

public  class  FragmentFarmerFindWarehouse extends DialogFragment {
    RecyclerView recyclerView;
    AdapterSuggestedWarehouse mAdapter;

    public FragmentFarmerFindWarehouse(){

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

    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_farmer_find_warehouse, container, false);
        recyclerView = view.findViewById(R.id.warehouseResultRecyclerView);

        mAdapter = new AdapterSuggestedWarehouse(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter((mAdapter));
        mAdapter.notifyDataSetChanged();
        return view;

    }
}






//public class FragmentFarmerMenu extends Fragment {
//    RecyclerView recyclerView;
//    AdapterFarmerMenu mAdapter;
//
//    public  FragmentFarmerMenu(){
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.activity_farmer, container, false);
//        recyclerView = view.findViewById(R.id.farmerMenuRecyclerView);
//
//        mAdapter = new AdapterFarmerMenu(getContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
//        return view;
//    }
//
//}