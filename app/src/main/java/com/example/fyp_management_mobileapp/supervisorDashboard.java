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

public class supervisorDashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    RelativeLayout titleSubmissionlist, proposalSubmissionlist, thesisSubmissionlist, proposalSlideSubmissionlist, finalPresentationSlideSubmissionlist, posterSubmissionlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_dashboard);
        findViews();
        setListeners();
    }

    private void findViews() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationsupervisor);
        bottomNavigationView.getMenu().findItem(R.id.supervisorhome).setChecked(true);
        titleSubmissionlist = findViewById(R.id.fyptitlesubmissionlist);
        proposalSubmissionlist = findViewById(R.id.proposalsubmissionlist);
        thesisSubmissionlist = findViewById(R.id.thesissubmissionlist);
        proposalSlideSubmissionlist = findViewById(R.id.proposalslidesubmissionlist);
        finalPresentationSlideSubmissionlist = findViewById(R.id.finalpresentationslidesubmissionlist);
        posterSubmissionlist = findViewById(R.id.postersubmissionlist);
    }

    private void setListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        titleSubmissionlist.setOnClickListener(view -> {
            Intent intent = new Intent(supervisorDashboard.this,supervisorViewAndDownloadTitleAbstract.class);
            startActivity(intent);
        });

        proposalSubmissionlist.setOnClickListener(view -> {
            Intent intent = new Intent(supervisorDashboard.this,supervisorViewAndDownloadProposal.class);
            startActivity(intent);
        });

        thesisSubmissionlist.setOnClickListener(view -> {
            Intent intent = new Intent(supervisorDashboard.this,supervisorViewAndDownloadThesisDraft.class);
            startActivity(intent);
        });

        proposalSlideSubmissionlist.setOnClickListener(view -> {
            Intent intent = new Intent(supervisorDashboard.this,supervisorViewAndDownloadProposalSlide.class);
            startActivity(intent);
        });

        finalPresentationSlideSubmissionlist.setOnClickListener(view -> {
            Intent intent = new Intent(supervisorDashboard.this,supervisorViewAndDownloadFinalSlide.class);
            startActivity(intent);
        });

        posterSubmissionlist.setOnClickListener(view -> {
            Intent intent = new Intent(supervisorDashboard.this,supervisorViewAndDownloadPoster.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.supervisorevaluate){
            Intent intent = new Intent(supervisorDashboard.this,supervisorEvaluateScore.class);
            startActivity(intent);
        }else if(id==R.id.supervisorstatus){
            Intent intent = new Intent(supervisorDashboard.this,supervisorCheckStatus.class);
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
                        Intent intent = new Intent(supervisorDashboard.this,MainActivity.class);
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