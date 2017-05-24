package com.example.social.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;

import static com.example.social.classes.Data.accessLevelMenu;


public class ChoiceProfileActivity extends Activity implements View.OnClickListener {

    private Button mBtAdmin;
    private Button mBtControl;
    private Button mBtInter;
    private Button mBtHelp;
    private Button mBtSettings;
    private Button mBtExit;

    private Animation mAnimation;

    private void initializeView() {
        mBtAdmin = (Button) findViewById(R.id.btAdmin);
        mBtControl = (Button) findViewById(R.id.btControl);
        mBtInter = (Button) findViewById(R.id.btInter);
        mBtHelp = (Button) findViewById(R.id.btHelp);
        mBtSettings = (Button) findViewById(R.id.btSettings);
        mBtExit = (Button) findViewById(R.id.btExit);

        mBtAdmin.setOnClickListener(this);
        mBtControl.setOnClickListener(this);
        mBtInter.setOnClickListener(this);
        mBtHelp.setOnClickListener(this);
        mBtSettings.setOnClickListener(this);
        mBtExit.setOnClickListener(this);

        mAnimation = AnimationUtils.loadAnimation(this, R.anim.butanim);
    }

    private void setAccessLevel() {
        switch (Data.accessLevel) {
            case Data.InterviewerRole:
                mBtInter.setEnabled(true);
                mBtControl.setEnabled(false);
                mBtAdmin.setEnabled(false);
                break;
            case Data.ControllerRole:
                mBtInter.setEnabled(true);
                mBtControl.setEnabled(true);
                mBtAdmin.setEnabled(false);
                break;
            case Data.AdminRole:
                mBtInter.setEnabled(true);
                mBtControl.setEnabled(true);
                mBtAdmin.setEnabled(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_profile);
        setTitle("Выберите роль");

        initializeView();
        setAccessLevel();
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(mAnimation);

        switch (v.getId()) {
            case R.id.btAdmin:
                btAdminClick();
                break;
            case R.id.btControl:
                btControlClick();
                break;
            case R.id.btInter:
                btInterClick();
                break;
            case R.id.btHelp:
                btHelpClick();
                break;
            case R.id.btSettings:
                btSettingsClick();
                break;
            case R.id.btExit:
                btExitClick();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        createExitDialog();
    }

    private void creatActivitySelect() {
        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }

    private void createExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChoiceProfileActivity.this);
        builder.setTitle("Выход")
                .setMessage("Вы уверены, что хотите выйти из системы")
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
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void btAdminClick() {
        Toast.makeText(ChoiceProfileActivity.this, "Администратор", Toast.LENGTH_SHORT).show();
        accessLevelMenu = Data.AdminRole;
        creatActivitySelect();
    }

    private void btControlClick() {
        Toast.makeText(ChoiceProfileActivity.this, "Контролёр", Toast.LENGTH_SHORT).show();
        accessLevelMenu = Data.ControllerRole;
        creatActivitySelect();
    }

    private void btInterClick() {
        Toast.makeText(ChoiceProfileActivity.this, "Интерьвюер", Toast.LENGTH_SHORT).show();
        accessLevelMenu = Data.InterviewerRole;
        creatActivitySelect();
    }

    private void btHelpClick() {
        Toast.makeText(ChoiceProfileActivity.this, "Help", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    private void btSettingsClick() {
        Toast.makeText(ChoiceProfileActivity.this, "Settings", Toast.LENGTH_SHORT).show();
    }

    private void btExitClick() {
        createExitDialog();
    }
}
