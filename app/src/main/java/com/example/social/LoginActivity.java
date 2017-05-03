package com.example.social;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText etLogin;
    EditText etPassword;
    Button btLogin;
    Button btRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegistration = (Button) findViewById(R.id.btRegistration);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btLoginClick();
            }
        });

        btRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btRegistrationClick();
            }
        });
    }


    private void btLoginClick() {

    }

    private void btRegistrationClick() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
