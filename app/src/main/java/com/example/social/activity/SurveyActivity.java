package com.example.social.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Answer;
import com.example.social.classes.Data;
import com.example.social.classes.PassedSurvey;
import com.example.social.classes.Question;
import com.example.social.classes.Survey;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static android.view.View.GONE;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvNumberOfQuetion;
    TextView tvQuestionText;
    TextView tvQuestionType;
    TextView tvResultOfSurvey;

    CheckBox cbQuestion1;
    CheckBox cbQuestion2;
    CheckBox cbQuestion3;
    CheckBox cbQuestion4;
    CheckBox cbQuestion5;
    CheckBox cbQuestion6;
    CheckBox cbQuestion7;
    CheckBox cbQuestion8;
    CheckBox cbQuestion9;
    CheckBox cbQuestion10;
    CheckBox cbQuestion11;
    CheckBox cbQuestion12;
    CheckBox cbQuestion13;
    CheckBox cbQuestion14;
    CheckBox cbQuestion15;
    CheckBox cbQuestion16;

    Button btAnswer;
    Button btNextQuestion;
    Button btPreviosQuestion;
    Button btFinishSurvey;
    Button btRepeatSurvey;
    Button btToMenu;

    LinearLayout llSurveyInProcess;
    LinearLayout llResOfSurvey;

    private Animation animation;

    private PassedSurvey mPassedSurvey;

    private Survey mSurvey;

    private ArrayList<CheckBox> mCheckBoxesArrayList;

    // Счетчик для вопросов
    private int mQuestionIndexator;

    private String requestData;
    private String responseData;
    private int responseCode;
    private boolean isCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        setTitle("Прохождение опроса");

        initializeViews();

        // Опрос, который будет пройден
        mSurvey = Data.targetSurvey;

        // Создаем объект "Пройденный опрос"
        mPassedSurvey = new PassedSurvey(getCurrentTime(), mSurvey.getSurveyId());

        showQuestion();
    }

    private void initializeViews() {
        tvNumberOfQuetion = (TextView) findViewById(R.id.tvNumberOfQuetion);
        tvQuestionText = (TextView) findViewById(R.id.tvQuetionText);
        tvQuestionType = (TextView) findViewById(R.id.tvQuestionType);
        tvResultOfSurvey = (TextView) findViewById(R.id.tvResultOfSurvey);

        cbQuestion1 = (CheckBox) findViewById(R.id.cbQuestion1);
        cbQuestion2 = (CheckBox) findViewById(R.id.cbQuestion2);
        cbQuestion3 = (CheckBox) findViewById(R.id.cbQuestion3);
        cbQuestion4 = (CheckBox) findViewById(R.id.cbQuestion4);
        cbQuestion5 = (CheckBox) findViewById(R.id.cbQuestion5);
        cbQuestion6 = (CheckBox) findViewById(R.id.cbQuestion6);
        cbQuestion7 = (CheckBox) findViewById(R.id.cbQuestion7);
        cbQuestion8 = (CheckBox) findViewById(R.id.cbQuestion8);
        cbQuestion9 = (CheckBox) findViewById(R.id.cbQuestion9);
        cbQuestion10 = (CheckBox) findViewById(R.id.cbQuestion10);
        cbQuestion11 = (CheckBox) findViewById(R.id.cbQuestion11);
        cbQuestion12 = (CheckBox) findViewById(R.id.cbQuestion12);
        cbQuestion13 = (CheckBox) findViewById(R.id.cbQuestion13);
        cbQuestion14 = (CheckBox) findViewById(R.id.cbQuestion14);
        cbQuestion15 = (CheckBox) findViewById(R.id.cbQuestion15);
        cbQuestion16 = (CheckBox) findViewById(R.id.cbQuestion16);

        btAnswer = (Button) findViewById(R.id.btAnswer);
        btNextQuestion = (Button) findViewById(R.id.btNextQuestion);
        btPreviosQuestion = (Button) findViewById(R.id.btPreviosQuestion);
        btFinishSurvey = (Button) findViewById(R.id.btFinishSurvey);
        btRepeatSurvey = (Button) findViewById(R.id.btRepeatSurvey);
        btToMenu = (Button) findViewById(R.id.btToMenu);

        llSurveyInProcess = (LinearLayout) findViewById(R.id.llSurveyInProcess);
        llResOfSurvey = (LinearLayout) findViewById(R.id.llResOfSurvey);

        btAnswer.setOnClickListener(this);
        btNextQuestion.setOnClickListener(this);
        btPreviosQuestion.setOnClickListener(this);
        btFinishSurvey.setOnClickListener(this);
        btRepeatSurvey.setOnClickListener(this);
        btToMenu.setOnClickListener(this);

        mCheckBoxesArrayList = new ArrayList<>();

        mCheckBoxesArrayList.add(cbQuestion1);
        mCheckBoxesArrayList.add(cbQuestion2);
        mCheckBoxesArrayList.add(cbQuestion3);
        mCheckBoxesArrayList.add(cbQuestion4);
        mCheckBoxesArrayList.add(cbQuestion5);
        mCheckBoxesArrayList.add(cbQuestion6);
        mCheckBoxesArrayList.add(cbQuestion7);
        mCheckBoxesArrayList.add(cbQuestion8);
        mCheckBoxesArrayList.add(cbQuestion9);
        mCheckBoxesArrayList.add(cbQuestion10);
        mCheckBoxesArrayList.add(cbQuestion11);
        mCheckBoxesArrayList.add(cbQuestion12);
        mCheckBoxesArrayList.add(cbQuestion13);
        mCheckBoxesArrayList.add(cbQuestion14);
        mCheckBoxesArrayList.add(cbQuestion15);
        mCheckBoxesArrayList.add(cbQuestion16);

        cbQuestion1.setOnClickListener(this);
        cbQuestion2.setOnClickListener(this);
        cbQuestion3.setOnClickListener(this);
        cbQuestion4.setOnClickListener(this);
        cbQuestion5.setOnClickListener(this);
        cbQuestion6.setOnClickListener(this);
        cbQuestion7.setOnClickListener(this);
        cbQuestion8.setOnClickListener(this);
        cbQuestion9.setOnClickListener(this);
        cbQuestion10.setOnClickListener(this);
        cbQuestion11.setOnClickListener(this);
        cbQuestion12.setOnClickListener(this);
        cbQuestion13.setOnClickListener(this);
        cbQuestion14.setOnClickListener(this);
        cbQuestion15.setOnClickListener(this);
        cbQuestion16.setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.butanim);
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(animation);
        try {
            switch (v.getId()) {
                case R.id.btAnswer:
                    btAnswerClick();
                    return;
                case R.id.btNextQuestion:
                    btNextQuestionClick();
                    return;
                case R.id.btPreviosQuestion:
                    btPreviosQuestionClick();
                    return;
                case R.id.btFinishSurvey:
                    btFinishSurveyClick();
                    return;
                case R.id.btRepeatSurvey:
                    btRepeatSurveyClick();
                    return;
                case R.id.btToMenu:
                    btToMenuClick();
                    return;
            }

            // Для CheckBox'ов
            boolean isMultiply = mSurvey.getArrayListQuestions().
                    get(mQuestionIndexator).
                    getQuestionType().
                    equals("MultiSelect");

            CheckBox checkBox = (CheckBox) v;

            if (!isMultiply) {
                for (int i = 0; i < mCheckBoxesArrayList.size(); i++)
                    mCheckBoxesArrayList.get(i).setChecked(false);
                checkBox.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        createExitDialog();
    }

    private void showQuestion() {
        Question question = mSurvey.getArrayListQuestions().get(mQuestionIndexator);

        tvNumberOfQuetion.setText("Вопрос: " + (mQuestionIndexator + 1 + "/"
                + mSurvey.getArrayListQuestions().size()));

        tvQuestionText.setText(question.getText());

        if (question.getQuestionType().equals("Select"))
            tvQuestionType.setText("Одиночный выбор");
        else
            tvQuestionType.setText("Множественный выбор");

        for (int i = 0; i < mCheckBoxesArrayList.size(); i++) {
            mCheckBoxesArrayList.get(i).setVisibility(GONE);
            mCheckBoxesArrayList.get(i).setChecked(false);
        }

        for (int i = 0; i < question.getArrayListOptions().size(); i++) {
            mCheckBoxesArrayList.get(i).setVisibility(View.VISIBLE);
            mCheckBoxesArrayList.get(i).setText((i + 1) + ") " + question.getArrayListOptions().get(i).getText() + ".");
        }

    }

    private void btAnswerClick() {
        boolean flagIsChose = false;
        for (int i = 0; i < mCheckBoxesArrayList.size(); i++) {
            flagIsChose = mCheckBoxesArrayList.get(i).isChecked();

            if (flagIsChose) break;
        }

        if (!flagIsChose) {
            Toast.makeText(this, "Выберите ответ", Toast.LENGTH_SHORT).show();
            return;
        }

        Question question = mSurvey.getArrayListQuestions().get(mQuestionIndexator);
        Answer answer = new Answer(question.getQuestionId(), question.getText());

        for (int i = 0; i < mCheckBoxesArrayList.size(); i++)
            if (mCheckBoxesArrayList.get(i).isChecked())
                answer.getAnswersId().add(question.getArrayListOptions().get(i).getOptionId());

        mPassedSurvey.getAnswers().add(mQuestionIndexator, answer);

        ++mQuestionIndexator;

        if (mQuestionIndexator < mSurvey.getArrayListQuestions().size()) {
            showQuestion();
        } else {
            btFinishSurveyClick();
        }
    }

    private void btNextQuestionClick() {
    }

    private void btPreviosQuestionClick() {
    }

    private void btFinishSurveyClick() {
        if (mQuestionIndexator == mSurvey.getArrayListQuestions().size()) {
            mPassedSurvey.setEndDate(getCurrentTime());
            try {
                sendPassedSurveyMethod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Вы ответили не на все вопросы", Toast.LENGTH_SHORT).show();
        }
    }

    private void btRepeatSurveyClick() {
        // Создаем объект "Пройденный опрос"
        mPassedSurvey = new PassedSurvey(getCurrentTime(), mSurvey.getSurveyId());
        llSurveyInProcess.setVisibility(View.VISIBLE);
        llResOfSurvey.setVisibility(View.GONE);

        mQuestionIndexator = 0;

        showQuestion();
    }

    private void btToMenuClick() {
        startActivity(new Intent(this, InterwierSurveyListActivity.class));
    }

    private void createExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Завершить опрос")
                .setMessage("При выходе из опроса, все данные будут удалены")
                .setCancelable(true).setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getBack();
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

    private void getBack() {
        super.onBackPressed();
    }

    private String getCurrentTime() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        return nowAsISO;
    }

    // --------------------------------------------------------------//
    //           Методы для отправки результата опроса на сервер
    // --------------------------------------------------------------//

    private void sendPassedSurveyMethod() throws Exception {
        SendPassedSurveyTask spst = new SendPassedSurveyTask();
        spst.execute();
    }

    // HTTP Post request
    private String makeRequestPost(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        responseData = "";
        requestData = mPassedSurvey.getJSONFromPassedSurvey();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + Data.token);
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(requestData.getBytes("UTF-8"));
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

    private class SendPassedSurveyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestPost(Data.URL + "api/forms");
                isCorrect = true;
            } catch (Exception e) {
                e.printStackTrace();
                isCorrect = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            llSurveyInProcess.setVisibility(View.GONE);
            llResOfSurvey.setVisibility(View.VISIBLE);
            if (isCorrect) {
                Toast.makeText(SurveyActivity.this, "Корректно", Toast.LENGTH_SHORT).show();

                tvResultOfSurvey.setText("Результат опроса отправлен на сервер");
            } else {
                tvResultOfSurvey.setText("Возникла ошибка при отправлении опроса на сервер");
            }

            isCorrect = false;
        }
    }
}
