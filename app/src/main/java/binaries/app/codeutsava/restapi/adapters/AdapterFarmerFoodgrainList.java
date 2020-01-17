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
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerReportProduce;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;

public class AdapterFarmerFoodgrainList extends RecyclerView.Adapter<AdapterFarmerFoodgrainList.ViewHolder> {
    Activity activity;
    List<BuyerFoodgrainResponse> ldata;
    List<Drawable> imgs;
    FragmentManager fragmentManager;

    public AdapterFarmerFoodgrainList(Activity activity, List<BuyerFoodgrainResponse> ldata, FragmentManager fragmentManager) {
        this.activity = activity;
        this.ldata = ldata;
        this.fragmentManager = fragmentManager;
        imgs = new ArrayList<>();

    }

    @NonNull
    @Override
    public AdapterFarmerFoodgrainList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_buyer_foodgrain_row, parent, false);

        imgs.add(activity.getResources().getDrawable(R.drawable.f1));
        imgs.add(activity.getResources().getDrawable(R.drawable.f2));
        imgs.add(activity.getResources().getDrawable(R.drawable.f3));
        imgs.add(activity.getResources().getDrawable(R.drawable.f4));
        imgs.add(activity.getResources().getDrawable(R.drawable.f5));


        return new AdapterFarmerFoodgrainList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFarmerFoodgrainList.ViewHolder holder, int position) {
        BuyerFoodgrainResponse data = ldata.get(position);

        Random rand = new Random();

        holder.name.setText(data.type);
        holder.name.setText(data.type);
        Glide.with(activity)
                .load(imgs.get(rand.nextInt(imgs.size())))
                .into(holder.foogImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currFoodgrainDataid = ldata.get(position).id;

                Bundle args = new Bundle();
                args.putSerializable("id",currFoodgrainDataid);

                FragmentFarmerReportProduce farmerReportProduceFragment = new FragmentFarmerReportProduce();
                farmerReportProduceFragment.setArguments(args);

                farmerReportProduceFragment.show(fragmentManager,"some");
            }
        });
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