package com.example.social;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_with_created_quiz extends Activity {

    int accessPermissions = 0;
    int countOfQuestions = classWithQuestionaire.interwie.getCountOfQuestions();

    TextView nameOfQuestionnaire_field;
    TextView nameOfCreator_field;
    TextView countOfQuesions_rezActivity_field;
    TextView tvQuestions_field;

    private void showQuestionsAndAnswers(){
        String str = "";
        for (int i = 0; i < countOfQuestions; i++){
            questions question = classWithQuestionaire.interwie.getCollectionWithQuestions().get(i);
            str = str.concat("Вопрос "+ (i+1) + ": " + question.getQuestionsText());

            if(question.isMultiple()) str = str.concat("\n   Множественный выбор : Есть");
            else str = str.concat("\n   Множественный выбор : Нет");

            for (int j = 0; j < question.getCountOfAsk(); j++){
                str = str.concat("\n" + "        Вариант ответа " + (j+1) + " : " + question.getAnswers().get(j));
            }
            str = str.concat("\n");
        }
        tvQuestions_field.setText(str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_with_created_quiz);

        setTitle("Просмотр готовой анкеты");

        nameOfQuestionnaire_field = (TextView) findViewById(R.id.nameOfQuestionnaire_field);
        nameOfCreator_field = (TextView) findViewById(R.id.nameOfCreator_field);
        countOfQuesions_rezActivity_field = (TextView) findViewById(R.id.countOfQuesions_rezActivity_field);
        tvQuestions_field = (TextView) findViewById(R.id.tvQuestions_field);

        nameOfQuestionnaire_field.setText("Название опроса : " + classWithQuestionaire.interwie.getNameOfQuestionnaire().toString());
        nameOfCreator_field.setText("Имя создателя : " + classWithQuestionaire.interwie.getNameOfCreator().toString());
        countOfQuesions_rezActivity_field.setText("Количество вопросов : " + classWithQuestionaire.interwie.getCountOfQuestions());

        ArrayList<questions> collectionWithQuestions = classWithQuestionaire.interwie.getCollectionWithQuestions();

        showQuestionsAndAnswers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
