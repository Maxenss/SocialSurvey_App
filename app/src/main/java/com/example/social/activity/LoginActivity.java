package com.example.social.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.LoginAPIWorker;

public class LoginActivity extends AppCompatActivity {

    EditText etLogin;
    EditText etPassword;
    Button btLogin;
    Button btRegistration;

    String userLogin;
    String userPassword;

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
        if (!etLogin.getText().toString().isEmpty())
            userLogin = etLogin.getText().toString();
        else {
            Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etPassword.getText().toString().isEmpty())
            userPassword = etPassword.getText().toString();
        else {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (LoginAPIWorker.signInMethod(userLogin, userPassword)) {
                Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
        }

        // startActivity(new Intent(this, ChoiceProfileActivity.class));
    }

    private void btRegistrationClick() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
