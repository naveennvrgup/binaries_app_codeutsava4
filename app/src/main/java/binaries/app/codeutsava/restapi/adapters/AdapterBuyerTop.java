package binaries.app.codeutsava.restapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;

public class AdapterBuyerTop extends RecyclerView.Adapter<AdapterBuyerTop.MyViewHolder> {

    private Context context;
    private List<String> items = new ArrayList<>();
    private int rowIndex = -1;

    public AdapterBuyerTop(Context context) {
        this.context = context;

        items.add("Seasonal");
        items.add("Regional");
        items.add("Seasonal");
        items.add("Regional");
        items.add("Seasonal");
        items.add("Regional");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_adapter_buyer_top, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.buyerMainCard.setOnClickListener(view -> {
            rowIndex = position;
            notifyDataSetChanged();
        });

        if (rowIndex != position) {
            holder.buyerMainCard.setCardBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.buyerText.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.buyerText.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));

        } else {
            holder.buyerMainCard.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.buyerText.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.buyerText.setTextColor(context.getResources().getColor(R.color.dashboardBg));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView buyerMainCard;
        TextView buyerText;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            buyerMainCard = itemView.findViewById(R.id.recycler_buyer_top_card);
            buyerText = itemView.findViewById(R.id.recycler_buyer_top_text);
        }
    }
}
