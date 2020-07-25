package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.activites.ActivityAuthentication;
import binaries.app.codeutsava.restapi.activites.ActivityBuyer;
import binaries.app.codeutsava.restapi.activites.ActivityBuyerBidsList;
import binaries.app.codeutsava.restapi.activites.ActivityBuyerOrders;
import binaries.app.codeutsava.restapi.activites.ActivityFarmerBids;
import binaries.app.codeutsava.restapi.activites.ActivityFarmerOrders;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerFoodGrainList;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduce;
import binaries.app.codeutsava.restapi.fragments.FragmentGetStoredWarehouse;

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
        View view = LayoutInflater.from(activity).inflate(R.layout.recyclerview_bottom_sheet, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
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

                    case "My Bulk Orders":
                        if (!(activity instanceof ActivityBuyerBidsList)) {
                            myIntent = new Intent(activity, ActivityBuyerBidsList.class);
                            activity.startActivity(myIntent);
                        }
                        break;

                    case "About Us":
                        break;

                    case "Log Out":
                        PreferenceManager.getDefaultSharedPreferences(activity)
                                .edit()
                                .clear()
                                .putBoolean("logged_in", false)
                                .apply();

                        myIntent = new Intent(activity, ActivityAuthentication.class);
                        activity.startActivity(myIntent);
                        break;

                    // FARMER BOTTOM SHEET
                    case "My Produce":
                        FragmentFarmerProduce frag = new FragmentFarmerProduce();
                        frag.setEnterTransition(new Slide(Gravity.END));
                        frag.setExitTransition(new Slide(Gravity.START));
                        frag.show(((AppCompatActivity) activity).getSupportFragmentManager(), "farmerProduce");
                        break;

                    case "Report Produce":
                        FragmentFarmerFoodGrainList fragmentFarmerFoodGrainList = new FragmentFarmerFoodGrainList();
                        fragmentFarmerFoodGrainList.setEnterTransition(new Slide(Gravity.RIGHT));
                        fragmentFarmerFoodGrainList.setExitTransition(new Slide(Gravity.LEFT));
                        fragmentFarmerFoodGrainList.show(((AppCompatActivity) activity).getSupportFragmentManager(), "farmerReportProduce");
                        break;

                    case "Received Orders":
                        if (!(activity instanceof ActivityFarmerOrders)) {
                            myIntent = new Intent(activity, ActivityFarmerOrders.class);
                            activity.startActivity(myIntent);
                        }

                        break;

                    case "My Warehouse Stores":
                        FragmentGetStoredWarehouse fragmentGetStoredWarehouse = new FragmentGetStoredWarehouse();
                        fragmentGetStoredWarehouse.setEnterTransition(new Slide(Gravity.END));
                        fragmentGetStoredWarehouse.setExitTransition(new Slide(Gravity.START));
                        fragmentGetStoredWarehouse.show(((AppCompatActivity) activity).getSupportFragmentManager(), "getStoredWarehouse");
                        break;

                    case "Bulk Orders":
                        if (!(activity instanceof ActivityFarmerBids)) {
                            myIntent = new Intent(activity, ActivityFarmerBids.class);
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

    static class MyViewHolder extends RecyclerView.ViewHolder {

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
