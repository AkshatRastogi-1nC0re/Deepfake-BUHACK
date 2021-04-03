package com.example.deepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    public static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final ImageView zoom = (ImageView) findViewById(R.id.logo);
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoom.startAnimation(zoomAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("loooool", 0);
                boolean agreed = prefs.getBoolean("otp_verified", false);
                if(!agreed) {
                    Intent registerIntent = new Intent(SplashScreen.this, login.class);
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    SplashScreen.this.startActivity(registerIntent);
                }
                else {
                    Intent registerIntent = new Intent(SplashScreen.this, homepage.class);
                    SplashScreen.this.startActivity(registerIntent);
                    }
                }
        }, SPLASH_TIME_OUT);


    }
}