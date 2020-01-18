package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityBuyer;
import binaries.app.codeutsava.restapi.activites.ActivityBuyerOrders;
import binaries.app.codeutsava.restapi.activites.ActivityFarmerOrders;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerFoodGrainList;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduce;

public class AdapterRecyclerViewBottomSheet extends RecyclerView.Adapter<AdapterRecyclerViewBottomSheet.MyViewHolder> {

    private List<Items> itemsList;
    private Activity activity;
    private Intent myIntent;

    public AdapterRecyclerViewBottomSheet(Activity activity, List<Items> itemsList) {
        this.activity = activity;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recyclerview_bottom_sheet, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (itemsList != null) {
            holder.bottomText.setText(itemsList.get(position).getItemName());
            holder.itemView.setOnClickListener(view -> {

                switch (itemsList.get(position).itemName) {

                    // FOR BUYER
                    case "Home":
                        if (!(activity instanceof ActivityBuyer)) {
                            myIntent = new Intent(activity, ActivityBuyer.class);
                            activity.startActivity(myIntent);
                            activity.finish();
                        }

                        break;

                    case "Orders":
                        if (!(activity instanceof ActivityBuyerOrders)) {
                            myIntent = new Intent(activity, ActivityBuyerOrders.class);
                            activity.startActivity(myIntent);
                            activity.finish();
                        }

                        break;

                    case "About Us":
                        break;

                    case "Log Out":
                        break;

                    // FARMER PRODUCE
                    case "Report Produce":
                        FragmentFarmerFoodGrainList fragmentFarmerFoodGrainList = new FragmentFarmerFoodGrainList();
                        fragmentFarmerFoodGrainList.show(((AppCompatActivity) activity).getSupportFragmentManager(), "farmerReportProduce");
                        break;

                    case "My Produce":
                        FragmentFarmerProduce frag = new FragmentFarmerProduce();
                        frag.show(((AppCompatActivity) activity).getSupportFragmentManager(), "farmerProduce");
                        break;

                    case "Received Orders":
                        if (!(activity instanceof ActivityFarmerOrders)) {
                            myIntent = new Intent(activity, ActivityFarmerOrders.class);
                            activity.startActivity(myIntent);
                        }

                        break;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemsList == null ? 0 : itemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bottomText;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bottomText = itemView.findViewById(R.id.recycler_bottom_sheet_text);
        }
    }

    public static class Items {
        private String itemName;

        public Items(String itemName) {
            this.itemName = itemName;
        }

        String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

    }
}
