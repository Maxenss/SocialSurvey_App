package com.example.social.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.social.R;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener {

    // Счетчик для вопросов
    private int mQuestionIndexator;

    TextView tvNumberOfQuetion;
    TextView tvQuetionText;
    TextView tvQuestionType;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        setTitle("Прохождение опроса");

        initializeViews();


    }

    private void initializeViews() {
        tvNumberOfQuetion = (TextView) findViewById(R.id.tvNumberOfQuetion);
        tvQuetionText = (TextView) findViewById(R.id.tvQuetionText);
        tvQuestionType = (TextView) findViewById(R.id.tvQuestionType);

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

        btAnswer.setOnClickListener(this);
        btNextQuestion.setOnClickListener(this);
        btPreviosQuestion.setOnClickListener(this);
        btFinishSurvey.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAnswer:
                btAnswerClick();
                break;
            case R.id.btNextQuestion:
                btNextQuestionClick();
                break;
            case R.id.btPreviosQuestion:
                btPreviosQuestionClick();
                break;
            case R.id.btFinishSurvey:
                btFinishSurveyClick();
                break;
        }
    }

    private void btAnswerClick() {
    }

    private void btNextQuestionClick() {
    }

    private void btPreviosQuestionClick() {
    }

    private void btFinishSurveyClick() {
    }
}
