package com.example.fyp_management_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class studentDashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    RelativeLayout titleSubmission, proposalSubmission, thesisSubmission, proposalSlideSubmission, finalPresentationSlideSubmission, posterSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        findViews();
        setListeners();
    }

    private void findViews() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationstudent);
        bottomNavigationView.getMenu().findItem(R.id.studenthome).setChecked(true);
        titleSubmission = findViewById(R.id.fyptitlesubmission);
        proposalSubmission = findViewById(R.id.proposalsubmission);
        thesisSubmission = findViewById(R.id.thesissubmission);
        proposalSlideSubmission = findViewById(R.id.proposalslidesubmission);
        finalPresentationSlideSubmission = findViewById(R.id.finalpresentationslidesubmission);
        posterSubmission = findViewById(R.id.postersubmission);

    }

    private void setListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        titleSubmission.setOnClickListener(view -> {
            Intent intent = new Intent(studentDashboard.this,studentFYPTitleAndAbstract.class);
            startActivity(intent);
        });

        proposalSubmission.setOnClickListener(view -> {
            Intent intent = new Intent(studentDashboard.this,studentProposal.class);
            startActivity(intent);
        });

        thesisSubmission.setOnClickListener(view -> {
            Intent intent = new Intent(studentDashboard.this,studentThesisDraft.class);
            startActivity(intent);
        });

        proposalSlideSubmission.setOnClickListener(view -> {
            Intent intent = new Intent(studentDashboard.this,studentProposalSlide.class);
            startActivity(intent);
        });

        finalPresentationSlideSubmission.setOnClickListener(view -> {
            Intent intent = new Intent(studentDashboard.this,studentFinalPresentationSlide.class);
            startActivity(intent);
        });

        posterSubmission.setOnClickListener(view -> {
            Intent intent = new Intent(studentDashboard.this,studentPoster.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.studentcheckscore){
            Intent intent = new Intent(studentDashboard.this,studentCheckScore.class);
            startActivity(intent);
        }else if (id==R.id.studentstatus){
            Intent intent = new Intent(studentDashboard.this,studentWorkStatus.class);
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
                        Intent intent = new Intent(studentDashboard.this,MainActivity.class);
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