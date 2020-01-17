package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import binaries.app.codeutsava.R;

public class AdapterRecyclerViewBottomSheet extends RecyclerView.Adapter<AdapterRecyclerViewBottomSheet.MyViewHolder> {

    private List<Items> itemsList;
    private Activity activity;

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
            Glide.with(activity).load(itemsList.get(position).getItemImageID()).into(holder.bottomImage);
            holder.bottomText.setText(itemsList.get(position).getItemName());
        }
    }

    @Override
    public int getItemCount() {
        return itemsList == null ? 0 : itemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bottomText;
        ImageView bottomImage;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bottomImage = itemView.findViewById(R.id.recycler_bottom_sheet_image);
            bottomText = itemView.findViewById(R.id.recycler_bottom_sheet_text);
        }
    }

    public static class Items {
        private String itemName;
        private int itemImageID;

        public Items(String itemName, int itemImageID) {
            this.itemName = itemName;
            this.itemImageID = itemImageID;
        }

        String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        int getItemImageID() {
            return itemImageID;
        }

        public void setItemImageID(int itemImageID) {
            this.itemImageID = itemImageID;
        }
    }
}
