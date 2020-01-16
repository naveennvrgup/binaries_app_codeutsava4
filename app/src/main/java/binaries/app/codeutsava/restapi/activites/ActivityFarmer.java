package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerBids;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerDetail;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerFindWarehouse;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerMenu;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduce;

public class ActivityFarmer extends AppCompatActivity {

    ImageView menuButton;
    boolean menuVisible = false;
    LinearLayout farmermenuLayout;
    LinearLayout farmerMenuUserData;
    RecyclerView farmerMenuRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

//        FragmentFarmerDetail farmerDetail = new FragmentFarmerDetail();
//        FragmentFarmerBids farmerBids = new FragmentFarmerBids();
//        FragmentFarmerProduce frament = new FragmentFarmerProduce();
        FragmentFarmerFindWarehouse farmerFindWarehouse = new FragmentFarmerFindWarehouse();
//        FragmentFarmerMenu farmerMenu = new FragmentFarmerMenu();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.farmerDashboard, farmerFindWarehouse)
                .commit();



    }
}
