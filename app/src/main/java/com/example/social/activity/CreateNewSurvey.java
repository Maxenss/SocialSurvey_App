package com.example.social.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Option;
import com.example.social.classes.Question;
import com.example.social.classes.Survey;

import java.util.ArrayList;

public class CreateNewSurvey extends AppCompatActivity implements View.OnClickListener {
    EditText etSurveyName;
    EditText etSurveyComment;
    EditText etCountOfQuestions;

    ScrollView svMainInfoNewSurvey;
    ScrollView svNewSurveyQuetions;

    Switch swMultiple;

    EditText etQuestionText;

    EditText evNewAnswer1;
    EditText evNewAnswer2;
    EditText evNewAnswer3;
    EditText evNewAnswer4;
    EditText evNewAnswer5;
    EditText evNewAnswer6;
    EditText evNewAnswer7;
    EditText evNewAnswer8;
    EditText evNewAnswer9;
    EditText evNewAnswer10;
    EditText evNewAnswer11;
    EditText evNewAnswer12;
    EditText evNewAnswer13;
    EditText evNewAnswer14;
    EditText evNewAnswer15;
    EditText evNewAnswer16;

    TextView tvQuestionNumber;

    Button btContinueCreateSurvey;
    Button btNextQuestion;
    Button btPreviosQuestion;

    Survey newSurvey;

    int countOfQuestions = 0;
    int questionIndexator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_survey);

        initializeView();

        tvQuestionNumber.setText(1 + ". Вопрос : ");
    }

    private void initializeView() {
        etSurveyName = (EditText) findViewById(R.id.etSurveyName);
        etSurveyComment = (EditText) findViewById(R.id.etSurveyComment);
        etCountOfQuestions = (EditText) findViewById(R.id.etCountOfQuestions);

        tvQuestionNumber = (TextView) findViewById(R.id.tvQuestionNumber);

        btContinueCreateSurvey = (Button) findViewById(R.id.btContinueCreateSurvey);

        svMainInfoNewSurvey = (ScrollView) findViewById(R.id.svMainInfoNewSurvey);
        svNewSurveyQuetions = (ScrollView) findViewById(R.id.svNewSurveyQuetions);

        swMultiple = (Switch) findViewById(R.id.swMultiple);

        etQuestionText = (EditText) findViewById(R.id.etQuestionText);

        evNewAnswer1 = (EditText) findViewById(R.id.evNewAnswer1);
        evNewAnswer2 = (EditText) findViewById(R.id.evNewAnswer2);
        evNewAnswer3 = (EditText) findViewById(R.id.evNewAnswer3);
        evNewAnswer4 = (EditText) findViewById(R.id.evNewAnswer4);
        evNewAnswer5 = (EditText) findViewById(R.id.evNewAnswer5);
        evNewAnswer6 = (EditText) findViewById(R.id.evNewAnswer6);
        evNewAnswer7 = (EditText) findViewById(R.id.evNewAnswer7);
        evNewAnswer8 = (EditText) findViewById(R.id.evNewAnswer8);
        evNewAnswer9 = (EditText) findViewById(R.id.evNewAnswer9);
        evNewAnswer10 = (EditText) findViewById(R.id.evNewAnswer10);
        evNewAnswer11 = (EditText) findViewById(R.id.evNewAnswer11);
        evNewAnswer12 = (EditText) findViewById(R.id.evNewAnswer12);
        evNewAnswer13 = (EditText) findViewById(R.id.evNewAnswer13);
        evNewAnswer14 = (EditText) findViewById(R.id.evNewAnswer14);
        evNewAnswer15 = (EditText) findViewById(R.id.evNewAnswer15);
        evNewAnswer16 = (EditText) findViewById(R.id.evNewAnswer16);

        btContinueCreateSurvey = (Button) findViewById(R.id.btContinueCreateSurvey);
        btNextQuestion = (Button) findViewById(R.id.btNextQuestion);
        btPreviosQuestion = (Button) findViewById(R.id.btPreviosQuestion);
    }

    private void clearAllEditText() {
        evNewAnswer1.setText("");
        evNewAnswer2.setText("");
        evNewAnswer3.setText("");
        evNewAnswer4.setText("");
        evNewAnswer5.setText("");
        evNewAnswer6.setText("");
        evNewAnswer7.setText("");
        evNewAnswer8.setText("");
        evNewAnswer9.setText("");
        evNewAnswer10.setText("");
        evNewAnswer11.setText("");
        evNewAnswer12.setText("");
        evNewAnswer13.setText("");
        evNewAnswer14.setText("");
        evNewAnswer15.setText("");
        evNewAnswer16.setText("");

        etQuestionText.setText("");

        swMultiple.setChecked(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btContinueCreateSurvey: {
                btContinueCreateSurveyClick();
                break;
            }
            case R.id.btNextQuestion:{
                btNextQuestionClick();
                break;
            }
            case R.id.btPreviosQuestion:{
                btPreviosQuestionClick();
                break;
            }
        }
    }

    private void btContinueCreateSurveyClick() {
        if (etSurveyName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Название опроса\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etSurveyComment.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Комментарией о опросе\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etCountOfQuestions.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Количество вопросов\"", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            countOfQuestions = Integer.parseInt(etCountOfQuestions.getText().toString());

            if (countOfQuestions == 0) {
                Toast.makeText(this, "Количество вопросов не может быть равно 0", Toast.LENGTH_SHORT).show();
                return;
            } else if (countOfQuestions < 0) {
                Toast.makeText(this, "Количество вопросов не может быть меньше 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Введены некорректные данные ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newSurvey == null) {
            newSurvey = new Survey();
        }

        newSurvey.setName(etSurveyName.getText().toString());
        newSurvey.setComment(etSurveyComment.getText().toString());
        newSurvey.setArrayListQuestions(new ArrayList<Question>(countOfQuestions));

        svMainInfoNewSurvey.setVisibility(View.GONE);
        svNewSurveyQuetions.setVisibility(View.VISIBLE);

        questionIndexator = 0;
    }

    private void btNextQuestionClick() {
        if (etQuestionText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Текст вопроса\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (evNewAnswer1.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Обязательный вопрос 1\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (evNewAnswer1.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Обязательный вопрос 1\"", Toast.LENGTH_SHORT).show();
            return;
        }

        Question question = new Question();
        question.setText(etQuestionText.getText().toString());
        question.setQuestionType(swMultiple.isChecked() ? "Select" : "MultiSelect");
        question.getArrayListOptions().add(new Option(evNewAnswer1.getText().toString()));
        question.getArrayListOptions().add(new Option(evNewAnswer2.getText().toString()));

        if (!evNewAnswer3.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer3.getText().toString()));

        if (!evNewAnswer4.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer4.getText().toString()));

        if (!evNewAnswer5.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer5.getText().toString()));

        if (!evNewAnswer6.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer6.getText().toString()));

        if (!evNewAnswer7.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer7.getText().toString()));

        if (!evNewAnswer8.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer8.getText().toString()));

        if (!evNewAnswer9.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer9.getText().toString()));

        if (!evNewAnswer10.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer10.getText().toString()));

        if (!evNewAnswer11.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer11.getText().toString()));

        if (!evNewAnswer12.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer12.getText().toString()));

        if (!evNewAnswer13.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer13.getText().toString()));

        if (!evNewAnswer14.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer14.getText().toString()));

        if (!evNewAnswer15.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer15.getText().toString()));

        if (!evNewAnswer16.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer16.getText().toString()));

        newSurvey.getArrayListQuestions().add(questionIndexator, question);

        ++questionIndexator;

        tvQuestionNumber.setText(questionIndexator + 2 + ". Вопрос : ");

        if (questionIndexator < countOfQuestions) {
            clearAllEditText();
        }
        // Если вопросы закончились
        else {

        }

    }

    private void btPreviosQuestionClick(){
        --questionIndexator;
        if (questionIndexator < 0){
            svMainInfoNewSurvey.setVisibility(View.VISIBLE);
            svNewSurveyQuetions.setVisibility(View.GONE);
        }
        else{
            tvQuestionNumber.setText(questionIndexator + 1 + ". Вопрос : ");

            // Заполнить поля данными, если не null
        }
    }
}
