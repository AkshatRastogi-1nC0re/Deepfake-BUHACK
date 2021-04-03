package com.example.deepfake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class upload4 extends AppCompatActivity {

    Button editText;
    Button btn;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload4);

        editText = findViewById(R.id.editText2);
        btn = findViewById(R.id.btn2);

//        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/hackathon-buhack.appspot.com/o/Aditya%20Sharma.mp4?alt=media&token=b4d123e0-4f47-4947-beff-785fbd8f09ba");
//        VideoView simpleVideoView = (VideoView) findViewById(R.id.vv); // initiate a video view
//        simpleVideoView.setVideoURI(uri);
//        simpleVideoView.start();
        VideoView vv = findViewById(R.id.vv);
        vv.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.vid);
        vv.start();


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadImage");

        btn.setEnabled(true);

        editText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                selectImage();
            }
        });

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("application/jpg");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Image file selected"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == 12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            btn.setEnabled(true);
            editText.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadImageFileFirebase(data.getData());
                }
            });
        }
    }

    private void uploadImageFileFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading");
        progressDialog.show();

        final String image_name4 = "upload"+System.currentTimeMillis()+".mp4";

        StorageReference reference = storageReference.child(image_name4);
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        assert uri != null;
//                        Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
//                        TextView tv1 = findViewById(R.id.tv1);
//                        tv1.setText(uri.toString());

                        putPDF putPDF = new putPDF(editText.getText().toString(), uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);
                        Toast.makeText(upload4.this, "File is Uploaded", Toast.LENGTH_SHORT).show();
                        SharedPreferences prefos = getApplicationContext().getSharedPreferences("xyz", 0);
                        SharedPreferences.Editor editor = prefos.edit();
                        editor.putString("link4", image_name4);
                        editor.apply();
                        progressDialog.dismiss();
                        Intent picture_intent = new Intent(upload4.this,finale.class);
                        startActivity(picture_intent);

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded..." + (int) progress + "%");

            }
        });

    }
}