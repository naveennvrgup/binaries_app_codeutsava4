package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;

public class AdapterFoodgrain extends RecyclerView.Adapter<AdapterFoodgrain.ViewHolder> {
    Activity activity;
    List<BuyerFoodgrainResponse> ldata;
    List<Drawable> imgs;

    public AdapterFoodgrain(Activity activity, List<BuyerFoodgrainResponse> ldata) {
        this.activity = activity;
        this.ldata = ldata;

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

        return new AdapterFoodgrain.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFoodgrain.ViewHolder holder, int position) {
        BuyerFoodgrainResponse data = ldata.get(position);

        Random rand = new Random();

        holder.name.setText(data.type);
        holder.name.setText(data.type);
        Glide.with(activity)
                .load(imgs.get(rand.nextInt(imgs.size())))
                .into(holder.foogImg);
    }

    @Override
    public int getItemCount() {
        return ldata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;
        public ImageView foogImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.BDfoodname);
            price = itemView.findViewById(R.id.BDfoodprice);
            foogImg = itemView.findViewById(R.id.BDimageView);

        }
    }
}