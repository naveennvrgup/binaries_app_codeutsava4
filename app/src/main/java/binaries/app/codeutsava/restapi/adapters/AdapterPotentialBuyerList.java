package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduceDetail;
import binaries.app.codeutsava.restapi.model.farmer.PotentialBuyerResponse;

public class AdapterPotentialBuyerList extends RecyclerView.Adapter<AdapterPotentialBuyerList.ViewHolder> {
    private List<PotentialBuyerResponse> produces;
    private Activity activity;
    private FragmentManager fragManager;

    public void setFragManager(FragmentManager fragManager) {
        this.fragManager = fragManager;
    }

    public AdapterPotentialBuyerList(List<PotentialBuyerResponse> produces, Activity activity) {
        this.produces = produces;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterPotentialBuyerList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.potential_buyer_recyclerview, null);

        return new AdapterPotentialBuyerList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPotentialBuyerList.ViewHolder holder, int position) {
        if (produces != null) {
            PotentialBuyerResponse produce = produces.get(position);

            holder.bName.setText(produce.name);
            holder.bContact.setText("Contact: " + produce.contact);
        }
    }

    @Override
    public int getItemCount() {
        return produces == null ? 0 : produces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bName, bContact;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            bName = itemView.findViewById(R.id.potentialBuyerName);
            bContact = itemView.findViewById(R.id.potentialBuyerContact);
          
        }
    }
}
