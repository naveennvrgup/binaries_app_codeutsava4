package binaries.app.codeutsava.restapi.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.model.auth.LoginResponse;
import binaries.app.codeutsava.restapi.model.auth.LoginPayload;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    LoginPayload loginPayload;

    public FragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextUsername=view.findViewById(R.id.authUsernameEditText);
        editTextPassword=view.findViewById(R.id.authPasswordEditText);
        buttonLogin=view.findViewById(R.id.authLoginBtn);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginApiCall();
            }
        });

        return view;
    }

    private void loginApiCall(){
        loginPayload.setUsername(editTextUsername.getText().toString());
        loginPayload.setPassword(editTextUsername.getText().toString());

        APIServices apiServices= AppClient.getInstance().createService(APIServices.class);
        Call<LoginResponse> call = apiServices.sendLoginRequest(loginPayload);


        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

}
