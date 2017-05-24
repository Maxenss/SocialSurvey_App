package com.example.social.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.social.R;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("Помощь");
    }
}
