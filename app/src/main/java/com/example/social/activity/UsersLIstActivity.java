package com.example.social.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;
import com.example.social.classes.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class UsersLIstActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lvUsersList;

    private String responseData;
    private int responseCode;
    private boolean isCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        setTitle("Просмотр пользователей");

        lvUsersList = (ListView) findViewById(R.id.lvUsersList);

        try {
            getUsersTaskMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSON() {
        Data.sUserArrayList = new ArrayList<>();

        JSONObject dataJsonObj;
        JSONArray usersArray;
        User user;

        try {

            dataJsonObj = new JSONObject(responseData);
            usersArray = dataJsonObj.getJSONArray("response");

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userJSON = usersArray.getJSONObject(i);

                user = new User();

                user.setUserId(userJSON.getInt("userId"));
                user.setLogin(userJSON.getString("login"));
                user.setFirstName(userJSON.getString("firstName"));
                user.setLastName(userJSON.getString("lastName"));
                user.setMiddleName(userJSON.getString("middleName"));
                user.setRole(userJSON.getString("role"));
                user.setDeleted(userJSON.getBoolean("isDeleted"));

                Data.sUserArrayList.add(user);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createListView() {
        ArrayList<HashMap<String, String>> mUsersList;
        final String TITLE = "catname"; // Верхний текст
        final String DESCRIPTION = "description"; // ниже главного

        // создаем массив списков
        mUsersList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hm;

        for (User user :
                Data.sUserArrayList) {
            hm = new HashMap<>();
            hm.put(TITLE, user.getLogin()); // Названик
            hm.put(DESCRIPTION, user.getRole()); // Описание

            mUsersList.add(hm);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mUsersList,
                android.R.layout.simple_list_item_2, new String[]{TITLE, DESCRIPTION},
                new int[]{android.R.id.text1,
                        android.R.id.text2});

        lvUsersList.setAdapter(adapter);

        lvUsersList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //System.out.println(position);

        // Data.surveyId = mSurveyShortArrayList.get(position).getSurveyId();

        //startActivity(new Intent(this, SurveyFullInfoActivity.class));
    }

    // --------------------------------------------------------------//
    //           Методы для получения данных о пользователях
    // --------------------------------------------------------------//

    private void getUsersTaskMethod() throws Exception {
        GetUsersTask gut = new GetUsersTask();
        gut.execute();
    }

    // HTTP GET request
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

    private class GetUsersTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestGet(Data.URL + "api/users");
                isCorrect = true;
            } catch (Exception e) {
                e.printStackTrace();
                isCorrect = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (isCorrect) {
                Toast.makeText(UsersLIstActivity.this, "Все гуд", Toast.LENGTH_SHORT).show();
                parseJSON();
                createListView();
            } else {
                Toast.makeText(UsersLIstActivity.this, "Не гуд", Toast.LENGTH_SHORT).show();
            }

            isCorrect = false;
        }
    }
}
