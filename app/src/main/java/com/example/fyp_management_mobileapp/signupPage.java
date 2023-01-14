package com.example.fyp_management_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupPage extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private EditText signupEmail, signupPassword, userName, signuppasswordconfirm;
    private Button signupButton;
    private TextView loginRedirectText;
    RadioGroup signupcategories;
    RadioButton student, supervisor;
    String selectedSignUpCategories = "";
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        signuppasswordconfirm = findViewById(R.id.signup_password_confirm);
        signupcategories = (RadioGroup) findViewById(R.id.signupcategories);
        student = (RadioButton) findViewById(R.id.radioButtonStudent);
        supervisor = (RadioButton) findViewById(R.id.radioButtonSupervisor);
        loginRedirectText = findViewById(R.id.loginText);
        userName = findViewById(R.id.signup_username);
        signupcategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioButtonStudent){
                    selectedSignUpCategories = student.getText().toString();
                }else if(i==R.id.radioButtonSupervisor){
                    selectedSignUpCategories = supervisor.getText().toString();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String Name = userName.getText().toString().trim();
                String confirmpass = signuppasswordconfirm.getText().toString().trim();

                if (Name.isEmpty()) {
                    userName.setError("Username cannot be empty");
                } else if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                } else if (pass.isEmpty()){
                    Toast.makeText(signupPage.this, "Passwords cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (confirmpass.isEmpty()) {
                    Toast.makeText(signupPage.this, "Confirm passwords cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(confirmpass)){
                    Toast.makeText(signupPage.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                } else if (selectedSignUpCategories.isEmpty()) {
                    Toast.makeText(signupPage.this, "Please select a categories", Toast.LENGTH_SHORT).show();
                }else {

                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(signupPage.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signupPage.this, loginPage.class));
                                FirebaseUser user = auth.getCurrentUser();
                                Toast.makeText(signupPage.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                //set students as users collection
                                DocumentReference df = fStore.collection("Users").document(user.getUid());
                                Map<String,Object> userinfo = new HashMap<>();
                                userinfo.put("Fullname", Name);
                                userinfo.put("Email", signupEmail.getText().toString());
                                //userinfo.put("Password", signupPassword.getText().toString());
                                if (selectedSignUpCategories.equals("Student account")){
                                    userinfo.put("isStudent", "1");
                                }else if (selectedSignUpCategories.equals("Supervisor account")){
                                    userinfo.put("isSupervisor", "1");
                                }
                                df.set(userinfo);
                                startActivity(new Intent(signupPage.this, loginPage.class));
                            } else {
                                Toast.makeText(signupPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    Users users = new Users(Name);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
                    reference.child(Name).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            userName.setText("");
                        }
                    });
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupPage.this, loginPage.class));
            }
        });

    }
}