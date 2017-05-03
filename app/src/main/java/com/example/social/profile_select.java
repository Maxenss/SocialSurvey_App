package com.example.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile_select extends Activity {

    Button create;
    Button edit;
    Button view;
    Button quiz;
    Button inter_profile;

    int flag_access = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_select);

        create = (Button)findViewById(R.id.Create_new);
        edit = (Button)findViewById(R.id.Edit_quiz);
        view = (Button)findViewById(R.id.View_quiz);
        quiz = (Button)findViewById(R.id.quiz);
        inter_profile = (Button)findViewById(R.id.inter_profile);

        try {
            Intent intent = getIntent();
            flag_access = intent.getIntExtra("flag", 0);
        }
        catch (Exception e){}

        System.out.println(flag_access);

        setVisible();
    }

    protected void setVisible(){
        try {
            switch (flag_access) {
                case 1: {
                    create.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                    quiz.setVisibility(View.GONE);
                    inter_profile.setVisibility(View.GONE);
                    break;
                }
                case 2:{
                    create.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                    quiz.setVisibility(View.GONE);
                    inter_profile.setVisibility(View.GONE);
                    break;
                }
                case 3:{
                    create.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                    edit.setVisibility(View.GONE);
                    quiz.setVisibility(View.VISIBLE);
                    inter_profile.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

        catch (Exception e ){}
    }

    protected void NewQuiz (View v){
        Intent intent = new Intent(this, createQuestionnaire.class);
        startActivity(intent);
    } // Создание нового опроса

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
