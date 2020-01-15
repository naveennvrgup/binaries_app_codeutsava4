package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerBids;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerDetail;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduce;

public class ActivityFarmer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

//        FragmentFarmerDetail farmerDetail = new FragmentFarmerDetail();
//        FragmentFarmerBids farmerBids = new FragmentFarmerBids();
//        FragmentFarmerProduce frament = new FragmentFarmerProduce();
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.farmerFL, frament)
//                .commit();
    }


}
