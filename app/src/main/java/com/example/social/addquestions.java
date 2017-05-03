package com.example.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class addquestions extends Activity {

    int countOfQuestions = 0; // Флаг указываюший на кол-во вопросов
    int numOfQuestions = 1; // Номер текущего вопроса

    ArrayList<String> CollectionWithAnswers;

    EditText question_text_field;
    EditText answer_1_field;
    EditText answer_2_field;
    EditText answer_3_field;
    EditText answer_4_field;
    EditText answer_5_field;
    EditText answer_6_field;
    EditText answer_7_field;
    EditText answer_8_field;
    EditText answer_9_field;
    EditText answer_10_field;
    EditText answer_11_field;
    EditText answer_12_field;

    Switch isMultiple;

    Button next_question_button;

    private void getControls() {
        question_text_field = (EditText) findViewById(R.id.question_text_field);

        answer_1_field = (EditText) findViewById(R.id.answer_1_field);
        answer_2_field = (EditText) findViewById(R.id.answer_2_field);
        answer_3_field = (EditText) findViewById(R.id.answer_3_field);
        answer_4_field = (EditText) findViewById(R.id.answer_4_field);
        answer_5_field = (EditText) findViewById(R.id.answer_5_field);
        answer_6_field = (EditText) findViewById(R.id.answer_6_field);
        answer_7_field = (EditText) findViewById(R.id.answer_7_field);
        answer_8_field = (EditText) findViewById(R.id.answer_8_field);
        answer_9_field = (EditText) findViewById(R.id.answer_9_field);
        answer_10_field = (EditText) findViewById(R.id.answer_10_field);
        answer_11_field = (EditText) findViewById(R.id.answer_11_field);
        answer_12_field = (EditText) findViewById(R.id.answer_12_field);

        next_question_button = (Button) findViewById(R.id.next_question_button);

        isMultiple = (Switch) findViewById(R.id.multiple_field);
    }

    private void cleanAllEditText() {
        question_text_field.setText("");

        answer_1_field.setText("");
        answer_2_field.setText("");
        answer_3_field.setText("");
        answer_4_field.setText("");
        answer_5_field.setText("");
        answer_6_field.setText("");
        answer_7_field.setText("");
        answer_8_field.setText("");
        answer_9_field.setText("");
        answer_10_field.setText("");
        answer_11_field.setText("");
        answer_12_field.setText("");
    }

    private void addAnswerToCollection() {
        if (!answer_1_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_1_field.getText().toString());
        if (!answer_2_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_2_field.getText().toString());
        if (!answer_3_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_3_field.getText().toString());
        if (!answer_4_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_4_field.getText().toString());
        if (!answer_5_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_5_field.getText().toString());
        if (!answer_6_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_6_field.getText().toString());
        if (!answer_7_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_7_field.getText().toString());
        if (!answer_8_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_8_field.getText().toString());
        if (!answer_9_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_9_field.getText().toString());
        if (!answer_10_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_10_field.getText().toString());
        if (!answer_11_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_11_field.getText().toString());
        if (!answer_12_field.getText().toString().isEmpty())
            CollectionWithAnswers.add(answer_12_field.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestions);

        CollectionWithAnswers = new ArrayList<String>();

        setTitle("Создание вопросов (вопрос " + numOfQuestions + ")");
        getControls();

        try {
            Intent intent = getIntent();
            countOfQuestions = intent.getIntExtra("Количество вопросов", 0);
            Toast.makeText(addquestions.this, "Количество вопросов : " + countOfQuestions, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(addquestions.this, "Произошла неизвестная ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    protected void next_question_method(View v) {
        try {
            if (answer_1_field.getText().toString().isEmpty() || answer_2_field.getText().toString().isEmpty()
                    || question_text_field.getText().toString().isEmpty()) {
                Toast.makeText(addquestions.this, "Не все обязательные поля заполнены ! ", Toast.LENGTH_SHORT).show();
                return;
            }
            // Проверка количества вопросов.
            // Добавляем вопрос и ответы
            addAnswerToCollection();
            classWithQuestionaire.interwie.NewQuestion(question_text_field.getText().toString()
                    , CollectionWithAnswers.size(), isMultiple.isChecked(), CollectionWithAnswers);

            CollectionWithAnswers = new ArrayList<String>();

            // Очищаем поля ввода
            cleanAllEditText();
            ++numOfQuestions;
            if (numOfQuestions > countOfQuestions) {
                Intent intent = new Intent(this, activity_with_created_quiz.class);
                startActivity(intent);
                return;
            }
            setTitle("Создание вопросов (вопрос " + numOfQuestions + ")");

        } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                Toast.makeText(addquestions.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}
