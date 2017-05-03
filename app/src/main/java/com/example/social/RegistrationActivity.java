package com.example.social;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUserLogin;
    EditText etUserPassword;
    EditText etPasswordConfirm;
    EditText etFirstName;
    EditText etLastName;
    EditText etMiddleName;
    Button btCreateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUserLogin = (EditText) findViewById(R.id.etUserLogin);
        etUserPassword = (EditText) findViewById(R.id.etUserPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMiddleName = (EditText) findViewById(R.id.etMiddleName);

        btCreateAccount = (Button) findViewById(R.id.btCreateAccount);

        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btCreateAccountClick();
            }
        });

    }

    private void btCreateAccountClick() {

    }
}
