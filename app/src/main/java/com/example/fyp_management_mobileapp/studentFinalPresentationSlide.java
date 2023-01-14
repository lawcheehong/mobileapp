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

public class studentFinalPresentationSlide extends AppCompatActivity {

    Button select_btn, upload_btn;
    EditText file_name, studentComment;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_final_presentation_slide);

        select_btn = findViewById(R.id.select_btn);
        upload_btn = findViewById(R.id.upload_btn);
        file_name = findViewById(R.id.name);
        studentComment = findViewById(R.id.comment);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("finalPresentationSlideSubmissionFolder");

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFiles();
            }
        });
    }

    private void selectFiles() {
        String title = file_name.getText().toString();
        String comment = studentComment.getText().toString();
        if(title.isEmpty()){
            Toast.makeText(studentFinalPresentationSlide.this, "Please enter your filename.", Toast.LENGTH_SHORT).show();
        }else if (comment.isEmpty()) {
            Toast.makeText(studentFinalPresentationSlide.this, "Please add some comment.", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent();
            intent.setType("application/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select ppt files...."), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Toast.makeText(studentFinalPresentationSlide.this, "File selected", Toast.LENGTH_SHORT).show();

            upload_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UploadFiles(data.getData());
                }
            });
        }
    }

    private void UploadFiles(Uri data) {
        String title = file_name.getText().toString();
        String comment = studentComment.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading....");
        progressDialog.show();

        StorageReference reference = storageReference.child("finalPresentationSlideSubmissionFolder/" + title);
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri url = uriTask.getResult();

                        submissionClass proposal = new submissionClass(file_name.getText().toString(), url.toString(), studentComment.getText().toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(proposal);
                        Toast.makeText(studentFinalPresentationSlide.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(studentFinalPresentationSlide.this, studentFinalPresentationSlide.class);
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
        Intent intent = new Intent(studentFinalPresentationSlide.this,studentDashboard.class);
        startActivity(intent);
    }
}
