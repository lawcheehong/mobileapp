package com.example.fyp_management_mobileapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class studentFYPTitleAndAbstract extends AppCompatActivity {

    Button select_btn, upload_btn;
    EditText title1, title2, title3, FYPabstractname;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_fyptitle_and_abstract);

        select_btn = findViewById(R.id.select_btn);
        upload_btn = findViewById(R.id.upload_btn);
        FYPabstractname = findViewById(R.id.abstractname);
        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        title3 = findViewById(R.id.title3);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("FYP_TitleAndAbstractFolder");

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFiles();
            }
        });
    }

    private void selectFiles() {
        String abstractname = FYPabstractname.getText().toString();
        String titleone = title1.getText().toString();
        String titletwo = title2.getText().toString();
        String titlethree = title3.getText().toString();
        if(titleone.isEmpty()){
            Toast.makeText(studentFYPTitleAndAbstract.this, "Please enter your first FYP title.", Toast.LENGTH_SHORT).show();
        }else if (titletwo.isEmpty()) {
            Toast.makeText(studentFYPTitleAndAbstract.this, "Please enter your second FYP title.", Toast.LENGTH_SHORT).show();
        }else if (titlethree.isEmpty()) {
            Toast.makeText(studentFYPTitleAndAbstract.this, "Please enter your third FYP title.", Toast.LENGTH_SHORT).show();
        }else if (abstractname.isEmpty()) {
            Toast.makeText(studentFYPTitleAndAbstract.this, "Please enter your abstract filename.", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent();
            intent.setType("application/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select pdf files...."), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Toast.makeText(studentFYPTitleAndAbstract.this, "File selected", Toast.LENGTH_SHORT).show();

            upload_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UploadFiles(data.getData());
                }
            });
        }
    }

    private void UploadFiles(Uri data) {
        String abstractname = FYPabstractname.getText().toString();
        String titleone = title1.getText().toString();
        String titletwo = title2.getText().toString();
        String titlethree = title3.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading....");
        progressDialog.show();

        StorageReference reference = storageReference.child("FYP_TitleAndAbstractFolder/" + abstractname);
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri url = uriTask.getResult();

                        submissionClass proposal = new submissionClass(FYPabstractname.getText().toString(), url.toString(), title1.getText().toString(), title2.getText().toString(), title3.getText().toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(proposal);
                        Toast.makeText(studentFYPTitleAndAbstract.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(studentFYPTitleAndAbstract.this, studentFYPTitleAndAbstract.class);
                        startActivity(intent);
                    }
                })


                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded...." + (int) progress + "%");
                    }
                });
    }

    public void onBackPressed() {
        Intent intent = new Intent(studentFYPTitleAndAbstract.this,studentDashboard.class);
        startActivity(intent);
    }
}
