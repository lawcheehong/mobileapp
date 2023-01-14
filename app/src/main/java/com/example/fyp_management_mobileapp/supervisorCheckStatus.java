package com.example.fyp_management_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class supervisorCheckStatus extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    Spinner spinner;
    TextView student1, student2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_check_status);
        findViews();
        setListeners();
    }

    private void findViews() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationsupervisor);
        bottomNavigationView.getMenu().findItem(R.id.supervisorstatus).setChecked(true);
        student1 = findViewById(R.id.notice_tv1);
        student2 = findViewById(R.id.notice_tv2);
    }

    private void setListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        // everything about spinner goes here
        final String[] batch = new String[1];
        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> schoolAdapter = ArrayAdapter.createFromResource(this, R.array.batch, android.R.layout.simple_spinner_item);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(schoolAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                batch[0] = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.supervisorevaluate){
            Intent intent = new Intent(supervisorCheckStatus.this,supervisorEvaluateScore.class);
            startActivity(intent);
        }else if(id==R.id.supervisorhome){
            Intent intent = new Intent(supervisorCheckStatus.this,supervisorDashboard.class);
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
                        Intent intent = new Intent(supervisorCheckStatus.this,MainActivity.class);
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