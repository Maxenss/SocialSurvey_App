package com.example.social.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SurveyFullInfoActivity extends AppCompatActivity {

    private String responseData;
    private int responseCode;

    private boolean isCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_full_info);

        try {
            getSurveyFullMethod();
        }
        catch (Exception e){

        }
    }

    // --------------------------------------------------------------//
    //               Методы для получения инфы о опросах
    // --------------------------------------------------------------//

    private void getSurveyFullMethod() throws Exception {
        GetSurveyFullTask getSurveysListTask = new GetSurveyFullTask();
        getSurveysListTask.execute();
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

    private class GetSurveyFullTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestGet(Data.URL + "api/surveys/" + Data.surveyId);
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
            } else {
                Toast.makeText(SurveyFullInfoActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Заполняет коллекцию mSurveyShortArrayList, данными из JSON-документа
    private void parseJSON() {
       /* JSONObject dataJsonObj = null;

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

        for (SurveyShort ss:
                mSurveyShortArrayList) {
            System.out.println(ss.getCooment());
            System.out.println(ss.getName());
            System.out.println(ss.getSurveyId());
            System.out.println(ss.getUserId());
            System.out.println(ss.isDeleted());
        }*/
    }
}
