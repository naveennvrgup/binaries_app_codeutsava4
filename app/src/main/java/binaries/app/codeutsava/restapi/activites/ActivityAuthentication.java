package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentLogin;
import binaries.app.codeutsava.restapi.fragments.FragmentSignup;
import binaries.app.codeutsava.restapi.model.auth.LoginPayload;
import binaries.app.codeutsava.restapi.model.auth.LoginResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAuthentication extends AppCompatActivity {
    private ScrollView signInScrollView, signUpScrollView;
    private TextView textSignIn, textSignUp;
    private CardView authProceed;
    private Button authDropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        initViews();
        setUpOnClickListeners();
    }

    private void initViews() {
        signInScrollView = findViewById(R.id.auth_scroll_sign_in);
        signUpScrollView = findViewById(R.id.auth_scroll_sign_up);

        textSignIn = findViewById(R.id.auth_text_sign_in);
        textSignUp = findViewById(R.id.auth_text_sign_up);

        authProceed = findViewById(R.id.auth_proceed);

        authDropDown = findViewById(R.id.auth_menu_drop_down);
    }

    private void setUpOnClickListeners() {
        textSignIn.setOnClickListener(view -> {
            signInScrollView.setVisibility(View.VISIBLE);

            textSignIn.setTextColor(getResources().getColor(android.R.color.black));
            textSignUp.setTextColor(getResources().getColor(android.R.color.darker_gray));
            signUpScrollView.animate().alpha(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
            signInScrollView.animate().alpha(1.0f).setDuration(300).setInterpolator(new AccelerateInterpolator()).start();

            signUpScrollView.setVisibility(View.GONE);
        });

        textSignUp.setOnClickListener(view -> {
            signUpScrollView.setVisibility(View.VISIBLE);

            textSignIn.setTextColor(getResources().getColor(android.R.color.darker_gray));
            textSignUp.setTextColor(getResources().getColor(android.R.color.black));
            signUpScrollView.animate().alpha(1.0f).setDuration(300).setInterpolator(new AccelerateInterpolator()).start();
            signInScrollView.animate().alpha(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();

            signInScrollView.setVisibility(View.GONE);
        });

        authDropDown.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(ActivityAuthentication.this, authDropDown);
            popupMenu.getMenuInflater().inflate(R.menu.menu_auth_choice, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                authDropDown.setText(menuItem.getTitle());
                return true;
            });

            popupMenu.show();
        });
    }
}
