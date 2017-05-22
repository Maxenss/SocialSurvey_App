package com.example.social.activity;

import android.content.Intent;
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
import com.example.social.classes.SurveyShort;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class InterwierSurveyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String responseData;
    private int responseCode;

    private boolean isCorrect;

    private ArrayList<SurveyShort> mSurveyShortArrayList;

    ListView lvSusrvesyShort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interwier_survey_list);

        lvSusrvesyShort = (ListView) findViewById(R.id.lvSusrvesyShort);

        mSurveyShortArrayList = new ArrayList<>();

        try {
            getSurveysListMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------//
    //               Методы для получения инфы о опросах
    // --------------------------------------------------------------//

    private void getSurveysListMethod() throws Exception {
        GetSurveysListTask getSurveysListTask = new GetSurveysListTask();
        getSurveysListTask.execute();
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

    private class GetSurveysListTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestGet(Data.URL + "api/surveys");
                isCorrect = true;
            } catch (Exception e) {
                isCorrect = false;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (isCorrect) {
                parseJSON();
                createListView();
            } else {
                Toast.makeText(InterwierSurveyListActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Заполняет коллекцию mSurveyShortArrayList, данными из JSON-документа
    private void parseJSON() {
        JSONObject dataJsonObj = null;

        try {
            dataJsonObj = new JSONObject(responseData);
            JSONArray surveysArray = dataJsonObj.getJSONArray("response");

            for (int i = 0; i < surveysArray.length(); i++) {
                JSONObject survey = surveysArray.getJSONObject(i);

                mSurveyShortArrayList.add(new SurveyShort(
                        survey.getInt("surveyId"),
                        survey.getString("name"),
                        survey.getString("comment"),
                        survey.getBoolean("isDeleted"),
                        survey.getInt("userId")
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (SurveyShort ss :
                mSurveyShortArrayList) {
            System.out.println(ss.getCooment());
            System.out.println(ss.getName());
            System.out.println(ss.getSurveyId());
            System.out.println(ss.getUserId());
            System.out.println(ss.isDeleted());
        }
    }

    private void createListView() {
        ArrayList<HashMap<String, String>> mSurveysShortList;
        final String TITLE = "catname"; // Верхний текст
        final String DESCRIPTION = "description"; // ниже главного

        // создаем массив списков
        mSurveysShortList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hm;

        for (SurveyShort ss :
                mSurveyShortArrayList) {
            hm = new HashMap<>();
            hm.put(TITLE, ss.getName()); // Название
            hm.put(DESCRIPTION, ss.getCooment()); // Описание

            mSurveysShortList.add(hm);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mSurveysShortList,
                android.R.layout.simple_list_item_2, new String[]{TITLE, DESCRIPTION},
                new int[]{android.R.id.text1,
                        android.R.id.text2});

        lvSusrvesyShort.setAdapter(adapter);

        lvSusrvesyShort.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);

        Data.surveyId = mSurveyShortArrayList.get(position).getSurveyId();

        startActivity(new Intent(this, SurveyFullInfoActivity.class));
    }

}
