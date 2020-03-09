package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentBuyerFoodgrainDetail;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;

public class AdapterFoodgrain extends RecyclerView.Adapter<AdapterFoodgrain.ViewHolder> {
    private Activity activity;
    private List<BuyerFoodgrainResponse> ldata;
    private List<Drawable> imgs;
    private FragmentManager fragmentManager;

    public AdapterFoodgrain(Activity activity, List<BuyerFoodgrainResponse> ldata, FragmentManager fragmentManager) {
        this.activity = activity;
        this.ldata = ldata;
        this.fragmentManager = fragmentManager;
        imgs = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterFoodgrain.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_buyer_foodgrain_row, parent, false);

        imgs.add(activity.getResources().getDrawable(R.drawable.f1));
        imgs.add(activity.getResources().getDrawable(R.drawable.f2));
        imgs.add(activity.getResources().getDrawable(R.drawable.f3));
        imgs.add(activity.getResources().getDrawable(R.drawable.f4));
        imgs.add(activity.getResources().getDrawable(R.drawable.f5));
        imgs.add(activity.getResources().getDrawable(R.drawable.f6));
        imgs.add(activity.getResources().getDrawable(R.drawable.f7));
        imgs.add(activity.getResources().getDrawable(R.drawable.f8));
        imgs.add(activity.getResources().getDrawable(R.drawable.f9));
        imgs.add(activity.getResources().getDrawable(R.drawable.f10));
        imgs.add(activity.getResources().getDrawable(R.drawable.f11));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFoodgrain.ViewHolder holder, int position) {
        BuyerFoodgrainResponse data = null;

        if (ldata != null) {
            data = ldata.get(position);
            Random rand = new Random();

            holder.name.setText(data.type);
            Glide.with(activity)
                    .load(imgs.get(rand.nextInt(imgs.size())))
                    .into(holder.foodImg);
        }

        BuyerFoodgrainResponse finalData = data;

        holder.itemView.setOnClickListener(v -> {
            FragmentBuyerFoodgrainDetail detail = new FragmentBuyerFoodgrainDetail();

            if (ldata != null) {
                Bundle args = new Bundle();
                args.putSerializable("foodgrain", finalData);
                detail.setArguments(args);
            }

            detail.show(fragmentManager, "some");
        });
    }

    @Override
    public int getItemCount() {
        return (ldata == null) ? 0 : ldata.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView foodImg;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.BDfoodname);
            foodImg = itemView.findViewById(R.id.BDimageView);
        }
    }
}