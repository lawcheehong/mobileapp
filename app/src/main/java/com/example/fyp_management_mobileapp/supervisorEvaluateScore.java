package com.example.fyp_management_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class supervisorEvaluateScore extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    Button failed, approved;
    EditText titleEdt, markEdt, commentEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_evaluate_score);
        findViews();
        setListeners();
    }

    private void findViews() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationsupervisor);
        bottomNavigationView.getMenu().findItem(R.id.supervisorevaluate).setChecked(true);
        titleEdt = findViewById(R.id.titleInput);
        markEdt = findViewById(R.id.markInput);
        commentEdt = findViewById(R.id.commentInput);
        failed = findViewById(R.id.upload_btn_reject);
        approved = findViewById(R.id.upload_btn_approve);
    }

    private void setListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myTitle = titleEdt.getText().toString().trim();
                String myMark = markEdt.getText().toString().trim();
                String myComment = commentEdt.getText().toString().trim();

                if (myTitle.isEmpty()) {
                    Toast.makeText(supervisorEvaluateScore.this, "Please enter the title.", Toast.LENGTH_SHORT).show();
                }else if (myMark.isEmpty()) {
                    Toast.makeText(supervisorEvaluateScore.this, "Please enter the mark.", Toast.LENGTH_SHORT).show();
                }else if (myComment.isEmpty()) {
                    Toast.makeText(supervisorEvaluateScore.this, "Please enter the comment.", Toast.LENGTH_SHORT).show();
                }else{
                    String dbTitle = "Title";
                    String dbMark = "Mark";
                    String dbComment = "Comment";
                    String dbStatus = "Status";

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference(" Evaulation of " + myTitle);

                    databaseReference.child(dbStatus).setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            }else {
                                Toast.makeText(supervisorEvaluateScore.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    databaseReference.child(dbTitle).setValue(myTitle).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                titleEdt.setText("");
                            } else {
                                Toast.makeText(supervisorEvaluateScore.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    databaseReference.child(dbMark).setValue(myMark).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                markEdt.setText("");
                            } else {
                                Toast.makeText(supervisorEvaluateScore.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    databaseReference.child(dbComment).setValue(myComment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                commentEdt.setText("");
                                Toast.makeText(supervisorEvaluateScore.this, "Evaluation done", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(supervisorEvaluateScore.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myTitle = titleEdt.getText().toString().trim();
                String myMark = markEdt.getText().toString().trim();
                String myComment = commentEdt.getText().toString().trim();

                if (myTitle.isEmpty()) {
                    Toast.makeText(supervisorEvaluateScore.this, "Please enter the title.", Toast.LENGTH_SHORT).show();
                }else if (myMark.isEmpty()) {
                    Toast.makeText(supervisorEvaluateScore.this, "Please enter the mark.", Toast.LENGTH_SHORT).show();
                }else if (myComment.isEmpty()) {
                    Toast.makeText(supervisorEvaluateScore.this, "Please enter the comment.", Toast.LENGTH_SHORT).show();
                }else{
                    String dbTitle = "Title";
                    String dbMark = "Mark";
                    String dbComment = "Comment";
                    String dbStatus = "Status";

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference(" Evaulation of " + myTitle);

                    databaseReference.child(dbStatus).setValue("Failed").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            }else {
                                Toast.makeText(supervisorEvaluateScore.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    databaseReference.child(dbTitle).setValue(myTitle).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                titleEdt.setText("");
                            } else {
                                Toast.makeText(supervisorEvaluateScore.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    databaseReference.child(dbMark).setValue(myMark).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                markEdt.setText("");
                            } else {
                                Toast.makeText(supervisorEvaluateScore.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    databaseReference.child(dbComment).setValue(myComment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                commentEdt.setText("");
                                Toast.makeText(supervisorEvaluateScore.this, "Evaluation done", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(supervisorEvaluateScore.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.supervisorhome){
            Intent intent = new Intent(supervisorEvaluateScore.this,supervisorDashboard.class);
            startActivity(intent);
        }else if(id==R.id.supervisorstatus){
            Intent intent = new Intent(supervisorEvaluateScore.this,supervisorCheckStatus.class);
            startActivity(intent);
        }else if(id==R.id.logout){
            onBackPressed();
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(supervisorEvaluateScore.this,MainActivity.class);
                        intent.putExtra("finish", true); // if you are checking for this in your other Activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}