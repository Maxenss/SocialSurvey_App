package com.example.social.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.APIWorker;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUserLogin;
    EditText etUserPassword;
    EditText etPasswordConfirm;
    EditText etFirstName;
    EditText etLastName;
    EditText etMiddleName;
    Button btCreateAccount;

    LinearLayout llRegistration;

    String userLogin;
    String userPassword;
    String passwordConfirm;
    String firstName;
    String lastName;
    String middleName;

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

        llRegistration = (LinearLayout) findViewById(R.id.llRegistration);

        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btCreateAccountClick();
            }
        });

    }

    private void btCreateAccountClick() {
        if (!etUserLogin.getText().toString().isEmpty())
            userLogin = etUserLogin.getText().toString();
        else {
            Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etUserPassword.getText().toString().isEmpty())
            userPassword = etUserPassword.getText().toString();
        else {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etPasswordConfirm.getText().toString().isEmpty())
            passwordConfirm = etPasswordConfirm.getText().toString();
        else {
            Toast.makeText(this, "Введите пароль ещё раз", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etFirstName.getText().toString().isEmpty())
            firstName = etFirstName.getText().toString();
        else {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etLastName.getText().toString().isEmpty())
            lastName = etLastName.getText().toString();
        else {
            Toast.makeText(this, "Введите фамилию", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etMiddleName.getText().toString().isEmpty())
            middleName = etMiddleName.getText().toString();
        else {
            Toast.makeText(this, "Введите отчество", Toast.LENGTH_SHORT).show();
            return;
        }

        // --------------------------------------------------------------//
        //                      Подключаемся к серверу
        // --------------------------------------------------------------//

        llRegistration.setEnabled(false);

        try {
            if (APIWorker.registationMethod(userLogin, userPassword, passwordConfirm,
                    firstName, lastName, middleName))
                Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
        }

        llRegistration.setEnabled(true);

    }
}
