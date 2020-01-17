package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentBuyerBottomSheet;
import binaries.app.codeutsava.restapi.fragments.FragmentBuyerHome;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerBottomSheet;

public class ActivityBuyer extends AppCompatActivity{

    ImageView menuButton;
    BottomSheetBehavior sheetBehavior;
    RelativeLayout bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        FragmentBuyerHome buyerHome = new FragmentBuyerHome();
        buyerHome.show(getSupportFragmentManager(),"buyerhome");


        menuButton=findViewById(R.id.buyer_menu_icon);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialogFragment bottomSheetDialogFragment=new FragmentBuyerBottomSheet();
                bottomSheetDialogFragment.show(getSupportFragmentManager(),"farmerBottomSheet");
            }
        });


    }


}
