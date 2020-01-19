package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import binaries.app.codeutsava.R;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
