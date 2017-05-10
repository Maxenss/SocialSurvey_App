package com.example.social.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

    String requsetData;
    String responseData;

    int responseCode;
    boolean isRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Регистрация");

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

        if (!userPassword.equals(passwordConfirm)){
            Toast.makeText(this, "Пароли должны совпадать", Toast.LENGTH_SHORT).show();
            return;
        }

        // --------------------------------------------------------------//
        //                      Подключаемся к серверу
        // --------------------------------------------------------------//

        try {
            registationMethod();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(RegistrationActivity.this, "Ошибка на этапе регистрации", Toast.LENGTH_SHORT).show();
        }
    }

    private void registationMethod() throws Exception {
        llRegistration.setEnabled(false);
        Map<String, String> hashMapParams = new HashMap<>();

        hashMapParams.put("login", userLogin);
        hashMapParams.put("password", userPassword);
        hashMapParams.put("passwordConfirm", passwordConfirm);
        hashMapParams.put("firstName", firstName);
        hashMapParams.put("lastName", lastName);
        hashMapParams.put("middleName", middleName);

        requsetData = new GsonBuilder().create().toJson(hashMapParams, Map.class);

        RegistrationTask pt = new RegistrationTask();
        pt.execute();
    }

    // HTTP Post request
    private String makeRequest(String url, String postJsonData) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        responseData = "";

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postJsonData);
        wr.flush();
        wr.close();

        int responceCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + postJsonData);
        System.out.println("Response Code : " + responceCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        responseData = response.toString();
        responseCode = responceCode;

        System.out.println(responseData);
        return responseData;
    }

    private class RegistrationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequest(Data.URL + "register", requsetData);
                isRegistered = true;
            } catch (Exception e) {
                e.printStackTrace();
                isRegistered = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
           // super.onPostExecute(res);
            llRegistration.setEnabled(true);
            if(isRegistered)
                Toast.makeText(RegistrationActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(RegistrationActivity.this, "Ошибка на этапе регистрации", Toast.LENGTH_SHORT).show();
        }
    }
}
