package com.example.deepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class finale extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finale);

        addItemToSheet();

    }

    private void addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
//        final String name = editTextItemName.getText().toString().trim();
//        final String brand = editTextBrand.getText().toString().trim();
//        final String price = editTextPrice.getText().toString().trim();

        SharedPreferences namedata = getApplicationContext().getSharedPreferences("xyz", 0);
        final String link1 = namedata.getString("link1", "null");
        final String link2 = namedata.getString("link2", "null");
        final String link3 = namedata.getString("link3", "null");
        final String link4 = namedata.getString("link4", "null");



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.webAppUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(finale.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),end_lol.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("imagelink1",link1);
                parmas.put("imagelink2",link2);
                parmas.put("backgroundlink",link3);
                parmas.put("videolink",link4);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);

    }
}