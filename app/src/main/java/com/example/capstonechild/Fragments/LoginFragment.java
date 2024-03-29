package com.example.capstonechild.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstonechild.Activities.MainActivity;
import com.example.capstonechild.R;


public class LoginFragment extends Fragment {

    public TextView signUpBtn, forgotPw;
    public Button loginBtn;
    public EditText email, password;

    public String emailString, passwordString;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginBtn = view.findViewById(R.id.signInBtn);
        signUpBtn = view.findViewById(R.id.signUpBtn);

        email = view.findViewById(R.id.etEmail);
        password = view.findViewById(R.id.etPassword);
        forgotPw = view.findViewById(R.id.forgotPasswordBtn);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.alert_label_editor, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        forgotPw.setOnClickListener(forgotPwView -> alertDialog.show());

        SharedPreferences shared = this.requireActivity().getSharedPreferences("Login", MODE_PRIVATE);
        String emailText = (shared.getString("email", ""));
        String passwordText = (shared.getString("password", ""));

        loginBtn.setOnClickListener(viewLoginBtn -> {
            emailString = email.getText().toString();
            passwordString = password.getText().toString();

            if (emailString.equals("") && passwordString.equals("")) {
                Toast.makeText(this.requireContext(), "Please Enter valid email and password!", Toast.LENGTH_SHORT).show();
                if ((emailString.equals(""))) {
                    if (email.getText().toString().trim().equalsIgnoreCase("")) {
                        email.setError("This field can not be blank");
                    }
                }
                if ((passwordString.equals(""))) {
                    if (password.getText().toString().trim().equalsIgnoreCase("")) {
                        password.setError("This field can not be blank");
                    }
                }
            } else {
                if (emailString.equals(emailText) && passwordString.equals(passwordText))
                    startAnotherActivity(MainActivity.class);
                else
                    Toast.makeText(this.requireContext(), "Email and password doesn't match!", Toast.LENGTH_SHORT).show();
            }
        });

        signUpBtn.setOnClickListener(viewSignupBtn -> {
            RegistrationFragment registrationFragment = new RegistrationFragment();
            replaceFragment(registrationFragment);
        });
        return view;
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fooContainer, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void startAnotherActivity(Class className) {
        Intent myIntent = new Intent(this.requireActivity(), className);
        startActivity(myIntent);
    }

}