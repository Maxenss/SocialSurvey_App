package com.example.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class Switch_profile extends Activity {

    static int flag_access = 0;   // Флаг указывающий , на права доступа
    static Integer password = 123456789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_profile);


    }

    public void Admin(View v) {
        flag_access = 1;
        Toast.makeText(Switch_profile.this, "Администратор", Toast.LENGTH_SHORT).show();

        creatActivitySelect();
    }

    public void Control(View v) {
        flag_access = 2;
        Toast.makeText(Switch_profile.this, "Контролёр", Toast.LENGTH_SHORT).show();

        creatActivitySelect();
    }

    public void Inter(View v) {
        flag_access = 3;
        Toast.makeText(Switch_profile.this, "Интерьвюер", Toast.LENGTH_SHORT).show();

        creatActivitySelect();
    }

    public void Help(View v) {
        Toast.makeText(Switch_profile.this, "Help", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, help.class);
        startActivity(intent);
    }

    public void Settings(View v) {
        Toast.makeText(Switch_profile.this, "Settings", Toast.LENGTH_SHORT).show();
    }

    public void Exit(View v) {
        Toast.makeText(Switch_profile.this, "Exit", Toast.LENGTH_SHORT).show();
    }

    private void creatActivitySelect() {
        Intent intent = new Intent(this, profile_select.class);
        intent.putExtra("flag", flag_access);

        startActivity(intent);
    }
}
