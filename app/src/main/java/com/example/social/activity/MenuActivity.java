package com.example.social.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.social.R;
import com.example.social.classes.Data;

public class MenuActivity extends Activity implements View.OnClickListener {

    Button btCreateNewSurvey;
    Button btViewSurvey;
    Button btUsersInfo;
    Button btStatistics;
    Button btStartSurvey;

    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btCreateNewSurvey = (Button) findViewById(R.id.btCreateNewSurvey);
        btViewSurvey = (Button) findViewById(R.id.btViewSurvey);
        btUsersInfo = (Button) findViewById(R.id.btUsersInfo);
        btStatistics = (Button) findViewById(R.id.btStatistics);
        btStartSurvey = (Button) findViewById(R.id.btStartSurvey);

        btCreateNewSurvey.setOnClickListener(this);
        btViewSurvey.setOnClickListener(this);
        btUsersInfo.setOnClickListener(this);
        btStatistics.setOnClickListener(this);
        btStartSurvey.setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.butanim);

        setVisible();
    }

    protected void setVisible() {
        switch (Data.accessLevelMenu) {
            case Data.InterviewerRole: {
                setTitle("Интервьюер");
                btCreateNewSurvey.setVisibility(View.GONE);
                btViewSurvey.setVisibility(View.GONE);
                btUsersInfo.setVisibility(View.GONE);
                btStatistics.setVisibility(View.GONE);
                btStartSurvey.setVisibility(View.VISIBLE);
                break;
            }
            case Data.ControllerRole: {
                setTitle("Контроллер");
                btCreateNewSurvey.setVisibility(View.GONE);
                btViewSurvey.setVisibility(View.GONE);
                btUsersInfo.setVisibility(View.GONE);
                btStatistics.setVisibility(View.VISIBLE);
                btStartSurvey.setVisibility(View.GONE);
                break;
            }
            case Data.AdminRole: {
                setTitle("Администратор");
                btCreateNewSurvey.setVisibility(View.VISIBLE);
                //btViewSurvey.setVisibility(View.VISIBLE);
                btUsersInfo.setVisibility(View.VISIBLE);
                btStatistics.setVisibility(View.GONE);
                btStartSurvey.setVisibility(View.GONE);
                break;
            }
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(animation);

        switch (v.getId()) {
            case R.id.btCreateNewSurvey:
                btCreateNewSurveyClick();
                break;
            case R.id.btStartSurvey:
                btStartSurveyClick();
                break;
            case R.id.btViewSurvey:
                btViewSurveyClick();
                break;
            case R.id.btUsersInfo:
                btUsersInfoClick();
                break;
            case R.id.btStatistics:
                btStatisticsClick();
                break;
        }
    }

    private void btCreateNewSurveyClick() {
        startActivity(new Intent(this, CreateNewSurvey.class));
    }

    private void btViewSurveyClick() {

    }

    private void btUsersInfoClick() {
        startActivity(new Intent(this, UsersLIstActivity.class));
    }

    private void btStatisticsClick() {
        Data.accesLevelSurveys = Data.CONTROLLERLIST;

        startActivity(new Intent(this, SurveyListActivity.class));
    }

    private void btStartSurveyClick() {
        Data.accesLevelSurveys = Data.INTERVIEWERLIST;

        startActivity(new Intent(this, SurveyListActivity.class));
    }
}
