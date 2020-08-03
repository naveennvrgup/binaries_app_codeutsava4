package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import binaries.app.codeutsava.R;

public class AdapterTutorial extends RecyclerView.Adapter<AdapterTutorial.TutViewHolder> {

    Activity activity;
    OnClickCallback callback;
    String[] items = new String[]{
            "Overall App Tutorial",
            "My Produce",
            "Report Produce",
            "Received Orders",
            "My Warehouse Stores",
            "Bulk Orders",
    };

    String[] urls = new String[]{
            "https://www.youtube.com/watch?v=kY7KsSKGoYA",
            "https://www.youtube.com/watch?v=yB3AktoIiWs",
            "https://www.youtube.com/watch?v=QsqjybJ3Q04",
            "https://www.youtube.com/watch?v=vryE9DaPlws",
            "https://www.youtube.com/watch?v=xn-TxwtVALQ",
            "https://www.youtube.com/watch?v=ifb7I9ba07M",
    };

    public AdapterTutorial(Activity activity) {
        this.activity = activity;
    }

    public void setListener(OnClickCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public TutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_tutorial_row, parent, false);
        return new TutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutViewHolder holder, int position) {
        holder.textView.setText(items[position]);
        String url = urls[position];
        holder.itemView.setOnClickListener(view -> {
            if (callback != null) callback.onClick(url);
        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    static class TutViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TutViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tut_text);
        }
    }

    public interface OnClickCallback {
        void onClick(String url);
    }
}
