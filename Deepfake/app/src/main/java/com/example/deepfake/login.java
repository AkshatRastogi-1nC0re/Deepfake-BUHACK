package com.example.deepfake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText inputCustomerId = findViewById(R.id.inputCustomerid);

        final Button buttonGetOTP = findViewById(R.id.getCustomerID);

        final ProgressBar progressbar = findViewById(R.id.progressBar);

        final FirebaseAuth mAuth;

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                progressbar.setVisibility(View.GONE);
                buttonGetOTP.setVisibility(View.VISIBLE);
                Toast.makeText(login.this, "Successful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                progressbar.setVisibility(View.GONE);
                buttonGetOTP.setVisibility(View.VISIBLE);
                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                progressbar.setVisibility(View.GONE);
                buttonGetOTP.setVisibility(View.VISIBLE);

                SharedPreferences lmao = getApplicationContext().getSharedPreferences("User_details", 0);
                String sai = lmao.getString("phone_number", null);
                Intent intent = new Intent(getApplicationContext(), verify_otp.class);
                intent.putExtra("mobile",sai);
                intent.putExtra("verificationId", verificationId);
                startActivity(intent);

            }
        };

        mAuth = FirebaseAuth.getInstance();
        String lol = "";

        buttonGetOTP.setOnClickListener(new View.OnClickListener(){
//            String kast_mar_phon = "9540014357";
            @Override
            public void onClick(View view) {
                if(inputCustomerId.getText().toString().trim().isEmpty()){
                    Toast.makeText(login.this, "Enter Customer ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                buttonGetOTP.setVisibility(View.INVISIBLE);

                ///////fetching the phone number from the database using customer id ///////////////

                String kast_mar_id = inputCustomerId.getText().toString();
                SharedPreferences loool = getApplicationContext().getSharedPreferences("User_details", 0);
                SharedPreferences.Editor editor = loool.edit();
                editor.putString("phone_number", kast_mar_id);
                editor.apply();

                String phon_ka_nambar = "+91" + kast_mar_id;

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(phon_ka_nambar)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(login.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

            }
        });


    }
}