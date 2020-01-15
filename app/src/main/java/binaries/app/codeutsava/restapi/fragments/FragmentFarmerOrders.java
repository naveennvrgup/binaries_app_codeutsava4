package binaries.app.codeutsava.restapi.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import binaries.app.codeutsava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFarmerOrders extends Fragment {


    public FragmentFarmerOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farmer_orders, container, false);
    }

}
