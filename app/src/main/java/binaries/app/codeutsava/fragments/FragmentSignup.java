package binaries.app.codeutsava.restapi.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import binaries.app.codeutsava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSignup extends Fragment {
    Spinner spinnerUserType;
    EditText editTextUsername, editTextPassword, editTextContact, editTextAddress,
            editTextCity, editTextState, editTextAdhaar, editTextDOB;
    Button buttonLogin, buttonToLoginFrag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        spinnerUserType= view.findViewById(R.id.signupUserTypeSpinner);
        editTextUsername= view.findViewById(R.id.signupUsernameEditText);
        editTextPassword= view.findViewById(R.id.signupPasswordEditText);
        editTextContact= view.findViewById(R.id.signupContactEditText);
        editTextAddress= view.findViewById(R.id.signupAddressEditText);
        editTextCity= view.findViewById(R.id.signupCityEditText);
        editTextState= view.findViewById(R.id.signupStateEditText);
        editTextAdhaar= view.findViewById(R.id.signupAdhaarEditText);
        editTextDOB= view.findViewById(R.id.signupDOB);
        buttonLogin= view.findViewById(R.id.signupBtn);
        buttonToLoginFrag= view.findViewById(R.id.signupToLoginFragBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);

        buttonToLoginFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentLogin fragmentLogin=new FragmentLogin();

                fragmentManager.beginTransaction()
                        .add(R.id.authFrameLayout, fragmentLogin)
                        .commit();

                FragmentSignup fragmentSignup=(FragmentSignup)fragmentManager.findFragmentById(R.id.authFrameLayout);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(fragmentSignup)
                        .commit();
            }
        });

        return view;
    }

}
