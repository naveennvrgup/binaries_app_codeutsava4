package binaries.app.codeutsava.restapi.activites;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentBuyerBottomSheet;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerBottomSheet;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduce;

public class ActivityFarmer extends AppCompatActivity {

    ImageView menuButton;
    BottomSheetBehavior sheetBehavior;
    RelativeLayout bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dashboardBg));
        }

        initViews();
    }

    private void initViews() {
        bottomSheet = findViewById(R.id.farmerBottomSheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        menuButton = findViewById(R.id.farmer_menu_icon);

        menuButton.setOnClickListener(v -> {
            BottomSheetDialogFragment bottomSheetDialogFragment = new FragmentFarmerBottomSheet();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "farmerBottomSheet");
        });
    }
}
