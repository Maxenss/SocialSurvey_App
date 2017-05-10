package com.example.social.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;
import com.example.social.help;

import static com.example.social.classes.Data.accessLevelMenu;


public class ChoiceProfileActivity extends Activity {

    Button btAdmin;
    Button btControl;
    Button btInter;
    Button btHelp;
    Button btSettings;
    Button btExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_profile);

        setTitle("Выберите роль");

        btAdmin = (Button) findViewById(R.id.btAdmin);
        btControl = (Button) findViewById(R.id.btControl);
        btInter = (Button) findViewById(R.id.btInter);
        btHelp = (Button) findViewById(R.id.btHelp);
        btSettings = (Button) findViewById(R.id.btSettings);
        btExit = (Button) findViewById(R.id.btExit);

        switch (Data.accessLevel){
            case Data.InterviewerRole:
                btInter.setEnabled(true);
                btControl.setEnabled(false);
                btAdmin.setEnabled(false);
                break;
            case Data.ControllerRole:
                btInter.setEnabled(true);
                btControl.setEnabled(true);
                btAdmin.setEnabled(false);
                break;
            case Data.AdminRole:
                btInter.setEnabled(true);
                btControl.setEnabled(true);
                btAdmin.setEnabled(true);
                break;
        }
    }

    public void Admin(View v) {
        Toast.makeText(ChoiceProfileActivity.this, "Администратор", Toast.LENGTH_SHORT).show();
        accessLevelMenu = Data.AdminRole;
        creatActivitySelect();
    }

    public void Control(View v) {
        Toast.makeText(ChoiceProfileActivity.this, "Контролёр", Toast.LENGTH_SHORT).show();
        accessLevelMenu = Data.ControllerRole;
        creatActivitySelect();
    }

    public void Inter(View v) {
        Toast.makeText(ChoiceProfileActivity.this, "Интерьвюер", Toast.LENGTH_SHORT).show();
        accessLevelMenu = Data.InterviewerRole;
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

        startActivity(intent);
    }
}
