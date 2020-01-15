package binaries.app.codeutsava.restapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;

public class AdapterProduce extends RecyclerView.Adapter<AdapterProduce.ViewHolder> {
    List<FarmerProduceResponse> produces;
    Context context;

    public AdapterProduce(List<FarmerProduceResponse>  produces, Context context) {
        this.produces = produces;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterProduce.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.farmer_produce_row, parent, false);

        return new AdapterProduce.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduce.ViewHolder holder, int position) {
        FarmerProduceResponse produce= produces.get(position);

        holder.demo.setText(produce.toString());
    }

    @Override
    public int getItemCount() {
        return produces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
    TextView demo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            demo=itemView.findViewById(R.id.farmer_produce_demo);

        }
    }
}
