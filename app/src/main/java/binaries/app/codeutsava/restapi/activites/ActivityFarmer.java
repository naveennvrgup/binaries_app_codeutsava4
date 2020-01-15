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
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerMenu;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduce;

public class ActivityFarmer extends AppCompatActivity {

    ImageView menuButton;
    boolean menuVisible=false;
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
        FragmentFarmerMenu farmerMenu = new FragmentFarmerMenu();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.farmerDashboard, farmerMenu)
                .commit();

        menuButton = (ImageView)findViewById(R.id.menu_icon);
        farmermenuLayout = (LinearLayout) findViewById(R.id.bottomFarmerMenu);
        farmerMenuRecycler = (RecyclerView) findViewById(R.id.farmerMenuRecyclerView);
        farmerMenuUserData = (LinearLayout) findViewById(R.id.farmerMenuUserData);

        menuButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String bl;
                if(menuVisible){
                    menuVisible=false;
                    bl = "false";
                }
                else{
                    menuVisible=true;
                    bl= "true";
                }
                Toast.makeText(getActivity(), "This is my Toast message! "+bl,
                        Toast.LENGTH_LONG).show();
            }
        });


        if(menuVisible){
            farmermenuLayout.setVisibility(View.VISIBLE);
            farmerMenuRecycler.setVisibility(View.VISIBLE);
            farmerMenuUserData.setVisibility(View.VISIBLE);

        }
        else{
            farmermenuLayout.setVisibility(View.GONE);
            farmerMenuRecycler.setVisibility(View.GONE);
            farmerMenuUserData.setVisibility(View.GONE);
        }

    }

    private Context getActivity() {
        return  ActivityFarmer.this;
    }

}
