package binaries.app.codeutsava.restapi.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentLogin;
import binaries.app.codeutsava.restapi.fragments.FragmentSignup;
import binaries.app.codeutsava.restapi.model.auth.LoginPayload;
import binaries.app.codeutsava.restapi.model.auth.LoginResponse;
import binaries.app.codeutsava.restapi.model.auth.SignupPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDetailResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAuthentication extends AppCompatActivity {
    private ScrollView signInScrollView, signUpScrollView;
    private TextView textSignIn, textSignUp;
    private CardView authProceed;
    private Button authDropDown, authProceedButton;
    private boolean showSignup = true;
    EditText signupname;
    EditText signuppassword;
    EditText signupcontact;
    private Map<String,String> usertypechoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String token = preferences.getString("token",null);
        String userType = preferences.getString("userType",null);

        if(token!=null){
            AppClient.token = token;
            AppClient.token = "Token " + token;
            AppClient.userType = userType;
            gotocorrectActivity(userType);
        }

        initViews();
        setUpOnClickListeners();
    }

    private void initViews() {
        signInScrollView = findViewById(R.id.auth_scroll_sign_in);
        signUpScrollView = findViewById(R.id.auth_scroll_sign_up);

        textSignIn = findViewById(R.id.auth_text_sign_in);
        textSignUp = findViewById(R.id.auth_text_sign_up);

        authProceed = findViewById(R.id.auth_proceed);
        authProceedButton = findViewById(R.id.auth_proceed_btn);

        signupname = findViewById(R.id.signupname);
        signupcontact = findViewById(R.id.signupcontact);
        signuppassword = findViewById(R.id.signuppassword);

        usertypechoices = new HashMap<>();
        usertypechoices.put("Who are you?","FAR");
        usertypechoices.put("Farmer","BUY");
        usertypechoices.put("Buyer","WHO");
        usertypechoices.put("Warehouse Owner","NGO");
        usertypechoices.put("Administrator","ADM");
        usertypechoices.put("Delivery","DVR");

        authDropDown = findViewById(R.id.auth_menu_drop_down);

        authProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showSignup) {
                    dosignupcall();
                } else {
                    dologincall();
                }
            }
        });
    }

    void dosignupcall() {
        SignupPayload payload = new SignupPayload();

        payload.name = signupname.getText().toString();
        payload.password = signuppassword.getText().toString();
        payload.adhaar = signupcontact.getText().toString();
        payload.contact = signupcontact.getText().toString();
        payload.role = usertypechoices.get(authDropDown.getText().toString());


        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<SignupPayload> call = apiServices.sendSignupRequest(payload);

        call.enqueue(new Callback<SignupPayload>() {
            @Override
            public void onResponse(Call<SignupPayload> call, Response<SignupPayload> response) {

                if(response.body().contact!=null){
                    Toast.makeText(ActivityAuthentication.this,
                            "Signup success. Please login", Toast.LENGTH_LONG).show();

                    toLogin();
                }else{
                    Toast.makeText(ActivityAuthentication.this,"login failed",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignupPayload> call, Throwable t) {
                Toast.makeText(ActivityAuthentication.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    void gotocorrectActivity(String role){
        if(role=="FAR"){
            startActivity(new Intent(ActivityAuthentication.this, ActivityFarmer.class));
            finish();
        }else if(role=="BUY"){
            startActivity(new Intent(ActivityAuthentication.this, ActivityBuyer.class));
            finish();
        }
    }

    void routetodashboard(String token){
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.getFarmerDetail()
                .enqueue(new Callback<FarmerDetailResponse>() {
                    @Override
                    public void onResponse(Call<FarmerDetailResponse> call, Response<FarmerDetailResponse> response) {
                        if(response.isSuccessful() && response.body()!=null){

                            SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                            preferences.edit()
                                    .putString("token", "Token " + token)
                                    .putString("userType",response.body().getRole())
                                    .commit();
                            AppClient.token = "Token " + token;
                            AppClient.userType = response.body().getRole();

                            gotocorrectActivity(AppClient.userType);
                        }
                    }

                    @Override
                    public void onFailure(Call<FarmerDetailResponse> call, Throwable t) {
                        Toast.makeText(ActivityAuthentication.this,"something went wrong",Toast.LENGTH_LONG).show();
                    }
                });
    }

    void dologincall() {
        LoginPayload payload = new LoginPayload();

        payload.setUsername(signupcontact.getText().toString());
        payload.setPassword(signuppassword.getText().toString());


        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<LoginResponse> call = apiServices.sendLoginRequest(payload);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(ActivityAuthentication.this,
                            "login success", Toast.LENGTH_LONG).show();

                    routetodashboard(response.body().getKey());
                }else{
                    Toast.makeText(ActivityAuthentication.this,"login failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ActivityAuthentication.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void toLogin() {
        signInScrollView.setVisibility(View.VISIBLE);

        textSignIn.setTextColor(getResources().getColor(android.R.color.black));
        textSignUp.setTextColor(getResources().getColor(android.R.color.darker_gray));
        signUpScrollView.animate().alpha(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
        signInScrollView.animate().alpha(1.0f).setDuration(300).setInterpolator(new AccelerateInterpolator()).start();
        showSignup = false;

        signUpScrollView.setVisibility(View.GONE);
    }

    private void setUpOnClickListeners() {
        textSignIn.setOnClickListener(view -> {
            toLogin();
        });

        textSignUp.setOnClickListener(view -> {
            signUpScrollView.setVisibility(View.VISIBLE);

            textSignIn.setTextColor(getResources().getColor(android.R.color.darker_gray));
            textSignUp.setTextColor(getResources().getColor(android.R.color.black));
            signUpScrollView.animate().alpha(1.0f).setDuration(300).setInterpolator(new AccelerateInterpolator()).start();
            signInScrollView.animate().alpha(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
            showSignup = true;

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
