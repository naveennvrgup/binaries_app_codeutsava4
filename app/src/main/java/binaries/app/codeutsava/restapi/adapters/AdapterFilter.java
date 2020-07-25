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

import static android.view.View.GONE;

public class AdapterFilter extends RecyclerView.Adapter<AdapterFilter.MyViewHolder> {

    private Context context;
    private List<String> items = new ArrayList<>();
    private int rowIndex = 0;
    private OnFilterChangeListener onFilterChangeListener = null;

    public AdapterFilter(Context context) {
        this.context = context;
    }

    public void addFilters(List<String> filters){
        items.addAll(filters);
        notifyDataSetChanged();
    }

    public void setOnFilterChangeListener(OnFilterChangeListener onFilterChangeListener){
        this.onFilterChangeListener = onFilterChangeListener;
    }

    public String getDefaultFilter(){
        return (items != null) ? items.get(0) : null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_adapter_buyer_top, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.buyerMainCard.setOnClickListener(view -> {
            rowIndex = position;
            notifyDataSetChanged();

            if(onFilterChangeListener != null) onFilterChangeListener.notifyFilterStatus(holder.buyerText.getText().toString());
        });

        if(position != 0) holder.view.setVisibility(GONE);
        holder.buyerText.setText(items.get(position));


        if (rowIndex != position) {
            holder.buyerMainCard.setCardBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.buyerText.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.buyerText.setTextColor(context.getResources().getColor(android.R.color.white));

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

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView buyerMainCard;
        TextView buyerText;
        View view;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            buyerMainCard = itemView.findViewById(R.id.recycler_buyer_top_card);
            buyerText = itemView.findViewById(R.id.recycler_buyer_top_text);

            view = itemView.findViewById(R.id.recycler_buyer_view);
        }
    }

    public interface OnFilterChangeListener {
        void notifyFilterStatus(String newFilter);
    }
}
