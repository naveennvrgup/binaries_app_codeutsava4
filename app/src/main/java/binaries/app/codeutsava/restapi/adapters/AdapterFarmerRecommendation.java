package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;

public class AdapterFarmerRecommendation extends RecyclerView.Adapter<AdapterFarmerRecommendation.MyViewHolder> {

    private List<String> data;
    private Activity activity;

    public AdapterFarmerRecommendation(List<String> data, Activity activity){
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_farm_recomm, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(data != null){
            holder.text.setText(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.recycler_farm_recommendation_text);
        }
    }
}
