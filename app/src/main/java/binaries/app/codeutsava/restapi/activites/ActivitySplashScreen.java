package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import binaries.app.codeutsava.R;

public class ActivitySplashScreen extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("logged_in", false))
            goToDash(PreferenceManager.getDefaultSharedPreferences(this).getString("role", ""));

        else {
            startActivity(new Intent(ActivitySplashScreen.this, ActivityAuthentication.class));
            finish();
        }
    }

    protected void goToDash(String role) {
        switch (role) {
            case "FAR":
                Intent intent = new Intent(ActivitySplashScreen.this, ActivityFarmer.class);
                startActivity(intent);
                finish();
                break;

            case "BUY":
                intent = new Intent(ActivitySplashScreen.this, ActivityBuyer.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
