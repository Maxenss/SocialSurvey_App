package com.example.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class createQuestionnaire extends AppCompatActivity {

    EditText NameOfQuestionnaire_field;
    EditText creatorName_field;
    EditText countOfQuestions_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_questionnaire);

        setTitle("Создание нового опроса");

        NameOfQuestionnaire_field = (EditText) findViewById(R.id.NameOfQuestionnaire_field);
        creatorName_field = (EditText) findViewById(R.id.creatorName_field);
        countOfQuestions_field = (EditText) findViewById(R.id.countOfQuestions_field);
    }

    protected void AddQuestions(View v) {
        try {
            if (NameOfQuestionnaire_field.getText().toString().isEmpty()) {
                Toast.makeText(createQuestionnaire.this, "Введите название опроса!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (creatorName_field.getText().toString().isEmpty()) {
                Toast.makeText(createQuestionnaire.this, "Введите своё имя!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (countOfQuestions_field.getText().toString().isEmpty()) {
                Toast.makeText(createQuestionnaire.this, "Укажите количество вопросов!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
              //  classWithQuestionaire.interwie = new questionnaire(creatorName_field.getText().toString(),
              //          NameOfQuestionnaire_field.getText().toString(),
              //          Integer.parseInt(countOfQuestions_field.getText().toString()));
//
                Intent intent = new Intent(this, addquestions.class);
                intent.putExtra("Количество вопросов", Integer.parseInt(countOfQuestions_field.getText().toString()));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(createQuestionnaire.this, "Проверьте поле с количеством вопросов", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        catch (Exception e){
            Toast.makeText(createQuestionnaire.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
