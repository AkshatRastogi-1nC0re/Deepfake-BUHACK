package com.example.deepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;


public class successful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful);

        SharedPreferences prefs = getSharedPreferences("link", 0);
        String link = prefs.getString("link", "");

        Uri uri = Uri.parse(link);
        VideoView simpleVideoView = (VideoView) findViewById(R.id.vv1); // initiate a video view
        simpleVideoView.setVideoURI(uri);
        simpleVideoView.start();

//        if(!Python.isStarted())
//            Python.start(new AndroidPlatform(this));
//
//        Python py = Python.getInstance();
//        final PyObject pyobj = py.getModule("script");
//        pyobj.callAttr("foo");

//        val python = Python.getInstance()
//        val pythonFile = python.getModule("helloworldscript")
//        val helloWorldString = pythonFile.callAttr("helloworld")
//        hello_textview.text = helloWorldString.toString()

    }
}