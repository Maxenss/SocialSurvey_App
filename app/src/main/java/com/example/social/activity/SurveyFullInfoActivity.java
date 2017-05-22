package com.example.social.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;
import com.example.social.classes.Survey;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SurveyFullInfoActivity extends AppCompatActivity {
    private String responseData;
    private int responseCode;

    private boolean isCorrect;

    ArrayList<LinearLayout> LinearLayoutWithQuestionsArrayList;

    LinearLayout llQuestions;
    TextView tvNameOfSurvey;
    TextView tvCountOfQuetions;
    Button btStartSurvey;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_full_info);

        LinearLayoutWithQuestionsArrayList = new ArrayList<>();

        llQuestions = (LinearLayout) findViewById(R.id.llQuestions);
        tvNameOfSurvey = (TextView) findViewById(R.id.tvNameOfSurvey);
        tvCountOfQuetions = (TextView) findViewById(R.id.tvCountOfQuetions);
        btStartSurvey = (Button) findViewById(R.id.btStartSurvey);

        btStartSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btStartSurveyClick();
            }
        });

        try {
            getSurveyFullMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void btStartSurveyClick() {
        startActivity(new Intent(this, SurveyActivity.class));
    }

    private void createProgressDialog(){
        pd = new ProgressDialog(this);
        pd.setTitle("Загрузка");
        pd.setMessage("Ожидание ответа от сервера");
        pd.show();
    }

    private void showQuestions() {
        try {
            System.out.println(Data.targetSurvey.getNewSurveyOnServerJSON());
            tvNameOfSurvey.setText(Data.targetSurvey.getName());
            tvCountOfQuetions.setText("Количество вопросов : " +
                    Data.targetSurvey.getArrayListQuestions().size());

            for (int i = 0; i < Data.targetSurvey.getArrayListQuestions().size(); i++)
                createNewLinearLayoutWithQuestion(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNewLinearLayoutWithQuestion(int index) {
        // Работа с LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(20, 20, 20, 20);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.setMargins(0, 20, 0, 0);
        linearLayout.setBackgroundResource(R.drawable.oval_azure);
        // -----------------------------------------------------------------------------------

        // Работа с textViewName
        TextView textViewName = new TextView(this);
        LinearLayout.LayoutParams TextViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        TextViewParams.topMargin = 5;
        textViewName.setText((index + 1) + ". " +
                Data.targetSurvey.getArrayListQuestions().get(index).getText());
        textViewName.setTextColor(Color.parseColor("#000000"));
        textViewName.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        textViewName.setTextSize(18);
        linearLayout.addView(textViewName, TextViewParams);
        // -----------------------------------------------------------------------------------

        // Работа с checkBoxMultiple
        CheckBox checkBoxMultiple = new CheckBox(this);
        LinearLayout.LayoutParams checkBoxMultipleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        checkBoxMultipleParams.setMargins(10, 10, 10, 10);
        checkBoxMultiple.setText("Множественный выбор");
        checkBoxMultiple.setTextColor(Color.parseColor("#000000"));
        checkBoxMultiple.setPadding(30, 0, 0, 0);
        checkBoxMultiple.setTextSize(17);
        checkBoxMultiple.setEnabled(false);
        if (Data.targetSurvey.getArrayListQuestions().get(index).getQuestionType().equals("Select"))
            checkBoxMultiple.setChecked(false);
        else
            checkBoxMultiple.setChecked(true);
        linearLayout.addView(checkBoxMultiple, checkBoxMultipleParams);
        // -----------------------------------------------------------------------------------

        // Работа с ответами
        for (int i = 0; i < Data.targetSurvey.getArrayListQuestions().get(index).getArrayListOptions().size(); i++) {

            TextView textViewOption = new TextView(this);
            LinearLayout.LayoutParams TextViewOptionParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            TextViewOptionParams.setMargins(10, 10, 0, 0);
            textViewOption.setText((i + 1) + ". " + Data.targetSurvey.getArrayListQuestions().get(index)
                    .getArrayListOptions().get(i).getText());
            textViewOption.setTextColor(Color.parseColor("#000000"));
            textViewOption.setBackgroundColor(Color.parseColor("#507CFC00"));
            textViewOption.setTextSize(15);
            linearLayout.addView(textViewOption, TextViewOptionParams);
        }
        // -----------------------------------------------------------------------------------

        llQuestions.addView(linearLayout, linearLayoutParams);
    }

    // --------------------------------------------------------------//
    //               Методы для получения инфы о опросе
    // --------------------------------------------------------------//

    private void getSurveyFullMethod() throws Exception {
        createProgressDialog();
        GetSurveyFullTask getSurveyFullTask = new GetSurveyFullTask();
        getSurveyFullTask.execute();
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
            pd.cancel();
            if (isCorrect) {
                Data.targetSurvey = Survey.getSurveyFromJSON(responseData);
                showQuestions();
            } else {
                Toast.makeText(SurveyFullInfoActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
