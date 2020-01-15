package binaries.app.codeutsava.restapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;


public class AdapterFarmerMenu extends RecyclerView.Adapter<AdapterFarmerMenu.ViewHolder> {
    List<String> farmerMenuOptions;
    Context context;

    public AdapterFarmerMenu(Context context){
        this.farmerMenuOptions = Arrays.asList("Profile","Report Produce","Find Warehouse","Profile","Report Produce");
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterFarmerMenu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recyclerview_generic_view, parent, false);

        return  new AdapterFarmerMenu.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFarmerMenu.ViewHolder holder, int position) {
        String currOptionName = farmerMenuOptions.get(position);

        holder.optionName.setText(currOptionName);

    }


    @Override
    public int getItemCount() {
        return farmerMenuOptions.size();
    }


    class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView optionName;
//        ImageView optionIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            optionName = itemView.findViewById(R.id.optionName);
//            optionIcon = itemView.findViewById(R.id.optionIcon);
        }
    }




}


