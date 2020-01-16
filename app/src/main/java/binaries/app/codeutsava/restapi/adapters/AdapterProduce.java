package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduceDetail;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;

public class AdapterProduce extends RecyclerView.Adapter<AdapterProduce.ViewHolder> {
    List<FarmerProduceResponse> produces;
    Activity activity;
    FragmentManager fragManager;

    public void setFragManager(FragmentManager fragManager) {
        this.fragManager = fragManager;
    }

    public AdapterProduce(List<FarmerProduceResponse> produces, Activity activity) {
        this.produces = produces;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterProduce.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.farmer_produce_row, null);

        return new AdapterProduce.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduce.ViewHolder holder, int position) {
        FarmerProduceResponse produce = produces.get(position);

        holder.demo.setText(produce.toString());
        holder.parent.setOnClickListener(v -> {
            FarmerProduceResponse currProduceData = produces.get(position);

            Bundle args = new Bundle();
            args.putSerializable("produce", currProduceData);

            FragmentFarmerProduceDetail produceDetail = new FragmentFarmerProduceDetail();
            produceDetail.setArguments(args);
            produceDetail.show(fragManager, "produce");
        });
    }

    @Override
    public int getItemCount() {
        return produces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView demo;
        RelativeLayout parent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            demo = itemView.findViewById(R.id.farmer_produce_demo);
            parent = itemView.findViewById(R.id.farmer_produce_main_lay);
        }
    }
}
