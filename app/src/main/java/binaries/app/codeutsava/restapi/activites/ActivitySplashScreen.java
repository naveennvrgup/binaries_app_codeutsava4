package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import binaries.app.codeutsava.R;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        startActivity(new Intent(ActivitySplashScreen.this, ActivityAuthentication.class));
        startActivity(new Intent(ActivitySplashScreen.this, ActivityAuthentication.class));
    }
}
