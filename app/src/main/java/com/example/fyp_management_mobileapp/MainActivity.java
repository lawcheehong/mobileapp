package com.example.fyp_management_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Button loginpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        findViews();
        setListeners();

        LinearLayout animation = findViewById(R.id.interface1);
        AnimationDrawable testing = (AnimationDrawable) animation.getBackground();
        testing.setEnterFadeDuration(1000);
        testing.setExitFadeDuration(3000);
        testing.start();
    }

    private void findViews(){
        loginpage = (Button) findViewById(R.id.btnloginpage);
    }

    private void setListeners(){
        loginpage.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,loginPage.class);
            startActivity(intent);
        });
    }

}