package binaries.app.codeutsava.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import binaries.app.codeutsava.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAuthentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

//        FragmentLogin fragmentLogin=new FragmentLogin();
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.authFrameLayout,fragmentLogin)
//                .commit();


//        LoginPayload loginPayload=new LoginPayload();
//        loginPayload.setUsername("8940073123");
//        loginPayload.setPassword("jervismk2");
//
//        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
//        Call<LoginResponse> call = apiServices.sendLoginRequest(loginPayload);
//
//
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                Toast.makeText(getApplicationContext(), response.body().getKey(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
//            }
//        });

    }
}
