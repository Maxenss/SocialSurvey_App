package com.example.social.activity;

import android.app.ProgressDialog;
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

public class InterwierSurveyListActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    // Список с короткими данными о всех опросах
    private ListView mLvSusrvesyShort;

    // Диалог, всплывающий при подключении к серверу
    private ProgressDialog mProgressDialog;

    // Коллекция с короткими данными о всех опросах
    private ArrayList<SurveyShort> mSurveyShortArrayList;

    // Ответ от сервера
    private String mResponseData;
    // Код ответа от сервера
    private int mResponseCode;
    // Флаг, указывающий на то, как корректно отработал запрос
    private boolean mIsCorrect;

    // Инициализирует View-элементы, и поля
    private void initializeComponents() {
        mLvSusrvesyShort = (ListView) findViewById(R.id.lvSusrvesyShort);
        mSurveyShortArrayList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interwier_survey_list);
        setTitle("Список опросов");

        initializeComponents();
        getSurveysListMethod();
    }

    // Обработчик нажатия на элемент списка mLvSusrvesyShort
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Data.surveyId = mSurveyShortArrayList.get(position).getSurveyId();

        startActivity(new Intent(this, SurveyFullInfoActivity.class));
    }

    // Заполняет коллекцию mSurveyShortArrayList, данными из JSON-документа
    private void parseJSON() {
        JSONObject dataJsonObj = null;

        try {
            dataJsonObj = new JSONObject(mResponseData);
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

    // Создает ListView cо списком опросов
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

        mLvSusrvesyShort.setAdapter(adapter);

        mLvSusrvesyShort.setOnItemClickListener(this);
    }

    // Создает ProgressDialog, уведомляющий о загрузке
    private void createProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Загрузка");
        mProgressDialog.setMessage("Ожидание ответа от сервера");
        mProgressDialog.show();
    }

    // --------------------------------------------------------------//
    //               Методы для получения инфы о опросах
    // --------------------------------------------------------------//

    // Метод вызывающий цепочку действий для заполнения mLvSusrvesyShort
    private void getSurveysListMethod() {
        try {
            createProgressDialog();
            GetSurveysListTask getSurveysListTask = new GetSurveysListTask();
            getSurveysListTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HTTP GET request на получения JSON - списка с опросами
    private void makeRequestGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        mResponseData = "";

        // Setting basic post request
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + Data.token);
        con.setRequestProperty("Content-Type", "application/json");

        mResponseCode = con.getResponseCode();
        System.out.println("nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + mResponseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        mResponseData = response.toString();

        System.out.println(mResponseData);
    }

    // Асинхронный поток для работы с сетью
    private class GetSurveysListTask extends AsyncTask<Void, Void, Void> {

        // Пытаемся выполнить запрос
        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestGet(Data.URL + "api/surveys");
                mIsCorrect = true;
            } catch (Exception e) {
                mIsCorrect = false;
                e.printStackTrace();
            }
            return null;
        }

        // Проверяем результат запроса
        @Override
        protected void onPostExecute(Void res) {
            mProgressDialog.cancel();
            if (mIsCorrect) {
                parseJSON();
                createListView();
            } else {
                Toast.makeText(InterwierSurveyListActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
