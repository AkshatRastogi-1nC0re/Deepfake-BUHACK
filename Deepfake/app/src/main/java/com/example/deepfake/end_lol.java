package com.example.deepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

import java.util.ArrayList;

public class end_lol extends AppCompatActivity {

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_lol);

        loading =  ProgressDialog.show(this,"Processing","Your video is being processed",false,false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Utilities.webAppUrl+"?action=getItems",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    String result_link = "";
                                    JSONObject jobj = new JSONObject(response);
                                    JSONArray jarray = jobj.getJSONArray("items");
                                    JSONObject jo = jarray.getJSONObject(0);
                                    result_link = jo.getString("result_link");


                                    Toast.makeText(end_lol.this, "Operation Successful", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();

                                    SharedPreferences prefos = getApplicationContext().getSharedPreferences("link", 0);
                                    SharedPreferences.Editor editor = prefos.edit();
                                    editor.putString("link", result_link);
                                    editor.apply();

                                    Intent picture_intent = new Intent(end_lol.this,successful.class);
                                    startActivity(picture_intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(end_lol.this, "Error: Please Contact Akshat Rastogi", Toast.LENGTH_SHORT).show();

                            }


                        }


                );

                int socketTimeOut = 50000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

                stringRequest.setRetryPolicy(policy);

                RequestQueue queue = Volley.newRequestQueue(end_lol.this);
                queue.add(stringRequest);
            }
        },300000);
        



    }
}
