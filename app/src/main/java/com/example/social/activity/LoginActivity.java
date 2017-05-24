package com.example.social.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private EditText etLogin;
    private EditText etPassword;
    private Button btLogin;
    private Button btRegistration;
    private Button btTest1;
    private LinearLayout llSignIn;

    private ProgressDialog pd;

    private Animation animation;

    private String userLogin;
    private String userPassword;

    private String requestData;
    private String responseData;
    private int responseCode;

    private boolean signInFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Вход");

        animation = AnimationUtils.loadAnimation(this, R.anim.butanim);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegistration = (Button) findViewById(R.id.btRegistration);
        btTest1 = (Button) findViewById(R.id.btTest1);
        llSignIn = (LinearLayout) findViewById(R.id.llSignIn);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                btLoginClick();
            }
        });

        btRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                btRegistrationClick();
            }
        });
    }

    @Override
    public void onBackPressed() {
        createExitDialog();
    }

    private void createExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Выход")
                .setMessage("Вы уверены, что хотите выйти из приложения?")
                .setCancelable(true).setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
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
            createProgressDialog();
            signInMethod();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    private void btRegistrationClick() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    private void createProgressDialog(){
        pd = new ProgressDialog(this);
        pd.setTitle("Загрузка");
        pd.setMessage("Ожидание ответа от сервера");
        pd.show();
    }

    // --------------------------------------------------------------//
    //                      Методы для залогинивания
    // --------------------------------------------------------------//

    private void signInMethod() throws Exception {
        llSignIn.setEnabled(false);
        requestData = "login=" + userLogin + "&password=" + userPassword;

        SignInTask pt = new SignInTask();
        pt.execute();
    }

    // HTTP Post request
    private String makeRequestPost(String url, String requestData) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        responseData = "";

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestData);
        wr.flush();
        wr.close();

        responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + requestData);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        responseData = response.toString();

        System.out.println(responseData);
        return responseData;
    }

    private class SignInTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestPost(Data.URL + "token", requestData);
                signInFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
                signInFlag = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            pd.cancel();
            if (signInFlag) {
                Toast.makeText(LoginActivity.this, "Вы залогинены", Toast.LENGTH_SHORT).show();

                // Получаем токен пользователя
                JSONObject dataJsonObj = null;

                try {
                    dataJsonObj = new JSONObject(responseData);
                    Data.token = dataJsonObj.getString("access_token");
                    System.out.println(Data.token);
                    getInfoMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else
                Toast.makeText(LoginActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();

            signInFlag = false;
        }
    }

    // --------------------------------------------------------------//
    //               Методы для получения инфы о пользователе
    // --------------------------------------------------------------//

    private void getInfoMethod() throws Exception {
        GetInfoTask task = new GetInfoTask();
        task.execute();
    }

    // HTTP Post request
    private String makeRequestGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        responseData = "";

        // Setting basic post request
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + Data.token);
        con.setRequestProperty("Content-Type", "application/json");

        responseCode = con.getResponseCode();
        System.out.println("nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        responseData = response.toString();

        System.out.println(responseData);
        return responseData;
    }

    private class GetInfoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestGet(Data.URL + "api/account");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            llSignIn.setEnabled(true);

            // Получаем токен пользователя
            JSONObject dataJsonObj = null;

            try {
                dataJsonObj = new JSONObject(responseData).getJSONObject("response");
                Data.userId = dataJsonObj.getInt("userId");
                Data.login = dataJsonObj.getString("login");
                Data.firstName = dataJsonObj.getString("firstName");
                Data.lastName = dataJsonObj.getString("lastName");
                Data.middleName = dataJsonObj.getString("middleName");
                Data.role = dataJsonObj.getString("role");

                System.out.println(Data.userId);
                System.out.println(Data.login);
                System.out.println(Data.firstName);
                System.out.println(Data.lastName);
                System.out.println(Data.middleName);
                System.out.println(Data.role);

                Data.setAccessLevel();

                System.out.println(Data.accessLevel);

                Context context = getApplicationContext();
                startActivity(new Intent(context, ChoiceProfileActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}