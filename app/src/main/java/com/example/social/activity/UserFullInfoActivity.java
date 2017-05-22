package com.example.social.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    EditText etUserID;
    EditText etFirstName;
    EditText etLastName;
    EditText etMiddleName;
    EditText etRole;
    EditText etPassword;

    Button btSaveUserInfo;
    Button btBack;

    ProgressDialog pd;

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

        btSaveUserInfo = (Button) findViewById(R.id.btSaveUserInfo);
        btBack = (Button) findViewById(R.id.btBack);

        btSaveUserInfo.setOnClickListener(this);
        btBack.setOnClickListener(this);
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
        etFirstName.setText(sTempUser.getFirstName());
        etLastName.setText(sTempUser.getLastName());
        etMiddleName.setText(sTempUser.getMiddleName());
        etRole.setText(sTempUser.getRole());
        etPassword.setText(sTempUser.getPassword());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSaveUserInfo:
                btSaveUserInfoClick();
                break;
            case R.id.btBack:
                btBackClick();
                break;
        }
    }

    private void btSaveUserInfoClick() {
        sTempUser.setUserId(Integer.parseInt(etUserID.getText().toString()));
        sTempUser.setFirstName(etFirstName.getText().toString());
        sTempUser.setLastName(etLastName.getText().toString());
        sTempUser.setMiddleName(etMiddleName.getText().toString());
        sTempUser.setRole(etRole.getText().toString());
        sTempUser.setPassword(etPassword.getText().toString());

        try {
            setInfoOnServerMethod();
        }
        catch (Exception e){
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

    // HTTP PUY request
    private String makeRequestPUT(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        responseData = "";

        // Setting basic post request
        con.setRequestMethod("PUT");
        con.setRequestProperty("Authorization", "Bearer " + Data.token);
        con.setRequestProperty("Content-Type", "application/json");

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

    private class SetInfoOnServerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestPUT(Data.URL + "api/user/" + sTempUser.getUserId());
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
                Toast.makeText(UserFullInfoActivity.this, "Все гуд", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(UserFullInfoActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }

            isCorrect = false;
        }
    }
}
