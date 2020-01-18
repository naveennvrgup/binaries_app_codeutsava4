package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import  binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.CreateBidDialog;

public class ActivityBuyerBidsList extends AppCompatActivity {
    FloatingActionButton createBidBtn;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_bids_list);

        recyclerView = findViewById(R.id.buyer_bids_recycler);
        createBidBtn = findViewById(R.id.createbidbutton);

        createBidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateBidDialog dialog = new CreateBidDialog(ActivityBuyerBidsList.this,ActivityBuyerBidsList.this);
                dialog.show();
            }
        });
    }
}
