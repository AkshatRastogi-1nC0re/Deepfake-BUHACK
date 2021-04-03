package com.example.deepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        final ImageView iv = (ImageView) findViewById(R.id.imageMenu);

        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(homepage.this, iv);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.navigation_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Rate Us")){
                            final String appPackageName = getPackageName();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                            Toast.makeText(homepage.this, "Rate Us", Toast.LENGTH_SHORT).show();
                        }
                        if(item.getTitle().equals("Logout")){
                            SharedPreferences prefos = getApplicationContext().getSharedPreferences("loooool", 0);
                            SharedPreferences.Editor editor = prefos.edit();
                            editor.putBoolean("otp_verified", false);
                            editor.apply();
                            Intent picture_intent = new Intent(homepage.this,SplashScreen.class);
                            startActivity(picture_intent);
                            Toast.makeText(homepage.this, "Logout", Toast.LENGTH_SHORT).show();
                        }
                        if(item.getTitle().equals("Download WCP App")){
                            final String appPackageName = getPackageName();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.example.akshatrastogi.willowoodagri")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                            Toast.makeText(homepage.this, "Download WCP App", Toast.LENGTH_LONG).show();
                        }
                        if(item.getTitle().equals("Terms and Conditions")){
                            Toast.makeText(homepage.this, "These features will be added later", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method


        LinearLayout menu_photos = (LinearLayout )findViewById(R.id.layoutYourPoints);
        menu_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picture_intent = new Intent(homepage.this,MainActivity.class);
                startActivity(picture_intent );
            }
        });

        LinearLayout lolol = (LinearLayout )findViewById(R.id.nucleus_scheme_home_button);
        lolol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(homepage.this, "These features will be added later", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout lololc = (LinearLayout )findViewById(R.id.layoutCalc);
        lololc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(homepage.this, "These features will be added later", Toast.LENGTH_SHORT).show();
            }
        });

        final Button twitter = (Button) findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(homepage.this, "These features will be added later", Toast.LENGTH_SHORT).show();
            }
        });

        final Button facebook = (Button) findViewById(R.id.facebook_btn);
        facebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(homepage.this, "These features will be added later", Toast.LENGTH_SHORT).show();
            }
        });


        Button btn_yt = (Button) findViewById(R.id.youtube_btn);
        btn_yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(homepage.this, "These features will be added later", Toast.LENGTH_SHORT).show();
            }
        });



    }
}