package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduceDetail;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;

public class AdapterProduce extends RecyclerView.Adapter<AdapterProduce.ViewHolder> {
    private List<FarmerProduceResponse> produces;
    private Activity activity;
    private FragmentManager fragManager;

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
        View view = LayoutInflater.from(activity).inflate(R.layout.farmer_produce_row, parent, false);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduce.ViewHolder holder, int position) {
        if (produces != null) {
            FarmerProduceResponse produce = produces.get(position);

            holder.fgName.setText(produce.type.type);
            holder.fgGrade.setText(Html.fromHtml("<b>Grade: </b>" + produce.grade));
            holder.fgPrice.setText(Html.fromHtml("<b>₹:  </b>" + produce.price));
            holder.fgDate.setText(Html.fromHtml("<b>Date: </b>" + produce.date));

            // TODO: Add dynamic image loading from APIs URLs

            holder.itemView.setOnClickListener(v -> {
                FarmerProduceResponse currProduceData = produces.get(position);

                Bundle args = new Bundle();
                args.putSerializable("produce", currProduceData);

                FragmentFarmerProduceDetail produceDetail = new FragmentFarmerProduceDetail();
                produceDetail.setEnterTransition(new Slide(Gravity.END));
                produceDetail.setExitTransition(new Slide(Gravity.START));
                produceDetail.setArguments(args);
                produceDetail.show(fragManager, "....");
            });
        }
    }

    @Override
    public int getItemCount() {
        return produces == null ? 0 : produces.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fgName, fgPrice, fgGrade, fgDate;
        ImageView fgImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            fgName = itemView.findViewById(R.id.food_prod_row_name);
            fgPrice = itemView.findViewById(R.id.food_prod_row_price);
            fgGrade = itemView.findViewById(R.id.food_prod_row_grade);
            fgDate = itemView.findViewById(R.id.food_prod_row_date);
            fgImage = itemView.findViewById(R.id.food_prod_row_img);
        }
    }
}
