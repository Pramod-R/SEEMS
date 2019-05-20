package com.sapthagiri.www.sap;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Activity_Splash extends AppCompatActivity {

    private static int SPLASH_TIME = 1500;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        im = (ImageView) findViewById(R.id.splash) ;
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.animation);
        im.startAnimation(myanim);

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
