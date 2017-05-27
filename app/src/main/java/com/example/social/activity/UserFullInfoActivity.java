package com.example.social.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.social.classes.Data.sTempUser;

public class UserFullInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUserID;
    private EditText etLogin;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etMiddleName;
    private EditText etRole;
    private EditText etPassword;

    private Button btSaveUserInfo;
    private Button btBack;

    private CheckBox cbIsDeleted;

    private ProgressDialog pd;

    private Animation animation;

    private String requestData;
    private String responseData;
    private int responseCode;
    private boolean isCorrect = false;

    private void initializeViews() {
        etUserID = (EditText) findViewById(R.id.etUserID);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        etRole = (EditText) findViewById(R.id.etRole);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etLogin = (EditText) findViewById(R.id.etLogin);

        btSaveUserInfo = (Button) findViewById(R.id.btSaveUserInfo);
        btBack = (Button) findViewById(R.id.btBack);

        cbIsDeleted = (CheckBox) findViewById(R.id.cbIsDeleted);

        btSaveUserInfo.setOnClickListener(this);
        btBack.setOnClickListener(this);
        cbIsDeleted.setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.butanim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_full_info);
        try {
            setTitle("Информация о пользователе");
            initializeViews();

            showUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUser() {
        etUserID.setText(sTempUser.getUserId() + "");
        etLogin.setText(sTempUser.getLogin());
        etFirstName.setText(sTempUser.getFirstName());
        etLastName.setText(sTempUser.getLastName());
        etMiddleName.setText(sTempUser.getMiddleName());
        etRole.setText(sTempUser.getRole());
        etPassword.setText(sTempUser.getPassword());
        cbIsDeleted.setChecked(sTempUser.isDeleted());
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(animation);

        switch (v.getId()) {
            case R.id.btSaveUserInfo:
                btSaveUserInfoClick();
                return;
            case R.id.btBack:
                btBackClick();
                return;
        }

        try {
            if (cbIsDeleted.isChecked())
                deleteUserOnServerMethod();
            else
                restoreUserOnServerMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void btSaveUserInfoClick() {
        sTempUser.setUserId(Integer.parseInt(etUserID.getText().toString()));
        //sTempUser.setLogin(etLogin.getText().toString());
        sTempUser.setFirstName(etFirstName.getText().toString());
        sTempUser.setLastName(etLastName.getText().toString());
        sTempUser.setMiddleName(etMiddleName.getText().toString());
        sTempUser.setRole(etRole.getText().toString());
        sTempUser.setPassword(etPassword.getText().toString());

        try {
            setInfoOnServerMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void btBackClick() {
        super.onBackPressed();
    }

    private void createProgressDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("Загрузка");
        pd.setMessage("Ожидание ответа от сервера");
        pd.show();
    }


    // --------------------------------------------------------------//
    //          Методы для изменения информации о пользователе
    // --------------------------------------------------------------//

    private void setInfoOnServerMethod() throws Exception {
        requestData = sTempUser.getJSONForSetDataOnServer();
        createProgressDialog();
        SetInfoOnServerTask setInfoOnServerTask = new SetInfoOnServerTask();
        setInfoOnServerTask.execute();
    }

    // HTTP PUT request
    private void makeRequestPUT(String _url) throws Exception {
        URL targetUrl = new URL(_url);
        HttpURLConnection httpCon = (HttpURLConnection) targetUrl.openConnection();

        httpCon.setDoOutput(true);
        httpCon.setDoInput(true);
        httpCon.setRequestMethod("PUT");
        httpCon.setRequestProperty("Authorization", "Bearer " + Data.token);
        httpCon.setRequestProperty("Content-Type", "application/json");

        // Пишем данные
        DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
        wr.write(requestData.getBytes("UTF-8"));
        wr.close();

        // Читаем данные
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpCon.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        responseData = response.toString();
        responseCode = httpCon.getResponseCode();

        System.out.println("nSending 'PUT' request to URL : " +  _url);
        System.out.println("Response Code : " + responseCode);
        System.out.println("Request Data : " + requestData);
        System.out.println("Response Data: "+ responseData);
    }

    private class SetInfoOnServerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestPUT(Data.URL + "api/users/" + sTempUser.getUserId());
                isCorrect = true;
            } catch (Exception e) {
                e.printStackTrace();
                isCorrect = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            pd.cancel();
            if (isCorrect) {
                Toast.makeText(UserFullInfoActivity.this, "Операция прошла успешно", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(UserFullInfoActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }

            isCorrect = false;
        }
    }


    // --------------------------------------------------------------//
    //          Методы для удаления пользователя на сервере
    // --------------------------------------------------------------//

    private void deleteUserOnServerMethod() throws Exception {
        createProgressDialog();
        DeleteUserOnServerTask deleteUserOnServerTask = new DeleteUserOnServerTask();
        deleteUserOnServerTask.execute();
    }

    // HTTP DELETE request
    private void makeRequestDelete(String _url) throws Exception {
        URL url = new URL(_url);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty(
                "Authorization", "Bearer " + Data.token);
        httpCon.setRequestProperty(
                "Content-Type", "application/json");
        httpCon.setRequestMethod("DELETE");
        httpCon.connect();

        responseCode = httpCon.getResponseCode();
        System.out.println("nSending 'DELETE' request to URL : " + _url);
        System.out.println("Response Code : " + responseCode);
    }

    private class DeleteUserOnServerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestDelete(Data.URL + "api/user/" + sTempUser.getUserId() + "/soft");
                isCorrect = true;
            } catch (Exception e) {
                e.printStackTrace();
                isCorrect = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            pd.cancel();
            if (isCorrect) {
                Toast.makeText(UserFullInfoActivity.this, "Операция прошла успешно", Toast.LENGTH_SHORT).show();

                sTempUser.setDeleted(true);
                cbIsDeleted.setChecked(true);

            } else {
                Toast.makeText(UserFullInfoActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }

            isCorrect = false;
        }
    }


    // --------------------------------------------------------------//
    //         Методы для восстановления пользователя на сервере
    // --------------------------------------------------------------//

    private void restoreUserOnServerMethod() throws Exception {
        createProgressDialog();
        RestoreUserOnServerTask restoreUserOnServerTask = new RestoreUserOnServerTask();
        restoreUserOnServerTask.execute();
    }

    // HTTP DELETE request
    private void makeRequestPATCH(String _url) throws Exception {
        URL url = new URL(_url);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty(
                "Authorization", "Bearer " + Data.token);
        httpCon.setRequestProperty(
                "Content-Type", "application/json");
        httpCon.setRequestMethod("PATCH");
        httpCon.connect();

        responseCode = httpCon.getResponseCode();
        System.out.println("nSending 'DELETE' request to URL : " + _url);
        System.out.println("Response Code : " + responseCode);
    }

    private class RestoreUserOnServerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestPATCH(Data.URL + "api/user/" + sTempUser.getUserId() + "/restore");
                isCorrect = true;
            } catch (Exception e) {
                e.printStackTrace();
                isCorrect = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            pd.cancel();
            if (isCorrect) {
                Toast.makeText(UserFullInfoActivity.this, "Операция прошла успешно", Toast.LENGTH_SHORT).show();

                sTempUser.setDeleted(false);
                cbIsDeleted.setChecked(false);

            } else {
                Toast.makeText(UserFullInfoActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }

            isCorrect = false;
        }
    }
}
