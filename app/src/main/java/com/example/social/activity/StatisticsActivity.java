package com.example.social.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;
import com.example.social.classes.Option;
import com.example.social.classes.Survey;
import com.example.social.customviews.AdvancedTextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StatisticsActivity extends AppCompatActivity {
    private String responseData;
    private int responseCode;

    private boolean isCorrect;

    private LinearLayout llQuestions;
    private TextView tvNameOfSurvey;
    private TextView tvCountOfQuetions;
    private TextView tvInterviewees;

    private ProgressDialog pd;

    private void initializeView() {
        llQuestions = (LinearLayout) findViewById(R.id.llQuestions);
        tvNameOfSurvey = (TextView) findViewById(R.id.tvNameOfSurvey);
        tvCountOfQuetions = (TextView) findViewById(R.id.tvCountOfQuetions);
        tvInterviewees = (TextView) findViewById(R.id.tvInterviewees);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setTitle("Просмотр статистики");

        initializeView();

        getSurveyStatisticMethod();
    }

    private void createProgressDialog() {
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
            tvInterviewees.setText("Опрошено граждан :" + Data.targetSurvey.getInterviewees());

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
        checkBoxMultiple.setTextColor(Color.parseColor("#000000"));
        checkBoxMultiple.setPadding(30, 0, 0, 0);
        checkBoxMultiple.setTextSize(17);
        checkBoxMultiple.setEnabled(false);
        if (Data.targetSurvey.getArrayListQuestions().get(index).getQuestionType().equals("Select")) {
            checkBoxMultiple.setText("Одиночный выбор");
            checkBoxMultiple.setChecked(true);
        } else {
            checkBoxMultiple.setText("Множественный выбор");
            checkBoxMultiple.setChecked(true);
        }
        linearLayout.addView(checkBoxMultiple, checkBoxMultipleParams);
        // -----------------------------------------------------------------------------------

        // Работа с ответами
        for (int i = 0; i < Data.targetSurvey.getArrayListQuestions().get(index).getArrayListOptions().size(); i++) {
            Option option = Data.targetSurvey.getArrayListQuestions().get(index)
                    .getArrayListOptions().get(i);

            ProgressBar progressBarOpiton = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            TextView textViewOption = new TextView(this);
            AdvancedTextView advancedTextView = new AdvancedTextView(this);

            LinearLayout.LayoutParams TextViewOptionParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );

            // Работаем с текстовым полем
            TextViewOptionParams.setMargins(10, 10, 0, 0);
            textViewOption.setText((i + 1) + ". " + option.getText());
            textViewOption.setTextColor(Color.parseColor("#000000"));
            textViewOption.setBackgroundColor(Color.parseColor("#507CFC00"));
            textViewOption.setTextSize(15);
            linearLayout.addView(textViewOption, TextViewOptionParams);
            // -----------------------------------------------------------------

            int temp = 0;

            try {
                temp = (int) ((double) option.getAnswersCount() / Data.targetSurvey.getInterviewees() * 100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(temp);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.background);
            advancedTextView.setBackground(drawable);
            advancedTextView.setTextColor(Color.parseColor("#000000"));
            advancedTextView.setGravity(Gravity.CENTER);
            advancedTextView.setValue(temp);
            advancedTextView.setText(option.getAnswersCount() + "/" + Data.targetSurvey.getInterviewees());

            linearLayout.addView(advancedTextView, TextViewParams);
        }
        // -----------------------------------------------------------------------------------

        llQuestions.addView(linearLayout, linearLayoutParams);
    }

    // --------------------------------------------------------------//
    //               Методы для получения инфы о опросе
    // --------------------------------------------------------------//

    private void getSurveyStatisticMethod(){
        try {
            createProgressDialog();
            GetSurveyStatisticTask getStatisticSurveyTask = new GetSurveyStatisticTask();
            getStatisticSurveyTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HTTP Post request
    private void makeRequestGet(String url) throws Exception {
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
    }

    private class GetSurveyStatisticTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestGet(Data.URL + "api/Statistic/" + Data.surveyId);
                System.out.println("ID sur " + Data.surveyId);
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
                Data.targetSurvey = Survey.getStatisticSurveyFromJson(responseData);
                showQuestions();
            } else {
                Toast.makeText(StatisticsActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
