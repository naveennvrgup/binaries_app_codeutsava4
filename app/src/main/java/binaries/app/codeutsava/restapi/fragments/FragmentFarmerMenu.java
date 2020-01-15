package binaries.app.codeutsava.restapi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerMenu;

public class FragmentFarmerMenu extends Fragment {
    RecyclerView recyclerView;
    AdapterFarmerMenu mAdapter;

    public  FragmentFarmerMenu(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_farmer, container, false);
        recyclerView = view.findViewById(R.id.farmerMenuRecyclerView);

        mAdapter = new AdapterFarmerMenu(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return view;
    }

}
