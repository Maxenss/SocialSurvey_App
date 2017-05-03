package com.example.social.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.help;


public class ChoiceProfileActivity extends Activity {

    static int flag_access = 0;   // Флаг указывающий , на права доступа
    static Integer password = 123456789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_profile);


    }

    public void Admin(View v) {
        flag_access = 1;
        Toast.makeText(ChoiceProfileActivity.this, "Администратор", Toast.LENGTH_SHORT).show();

        creatActivitySelect();
    }

    public void Control(View v) {
        flag_access = 2;
        Toast.makeText(ChoiceProfileActivity.this, "Контролёр", Toast.LENGTH_SHORT).show();

        creatActivitySelect();
    }

    public void Inter(View v) {
        flag_access = 3;
        Toast.makeText(ChoiceProfileActivity.this, "Интерьвюер", Toast.LENGTH_SHORT).show();

        creatActivitySelect();
    }

    public void Help(View v) {
        Toast.makeText(ChoiceProfileActivity.this, "Help", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, help.class);
        startActivity(intent);
    }

    public void Settings(View v) {
        Toast.makeText(ChoiceProfileActivity.this, "Settings", Toast.LENGTH_SHORT).show();
    }

    public void Exit(View v) {
        Toast.makeText(ChoiceProfileActivity.this, "Exit", Toast.LENGTH_SHORT).show();
    }

    private void creatActivitySelect() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("flag", flag_access);

        startActivity(intent);
    }
}