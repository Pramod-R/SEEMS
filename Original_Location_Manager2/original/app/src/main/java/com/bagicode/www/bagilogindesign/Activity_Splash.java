package com.sapthagiri.www.sap;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_Splash extends AppCompatActivity {

    private static int SPLASH_TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mySuperIntent = new Intent(Activity_Splash.this, LoginHome.class);
                startActivity(mySuperIntent);
                finish();
            }
        }, SPLASH_TIME);
    }
}
