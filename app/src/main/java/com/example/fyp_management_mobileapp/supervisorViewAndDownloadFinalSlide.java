package com.example.fyp_management_mobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class supervisorViewAndDownloadFinalSlide extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseReference;
    List<submissionClass> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_view_and_download_final_slide);
        listView = findViewById(R.id.listViewfinalslide);
        uploads = new ArrayList<>();

        viewAllFiles();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                submissionClass pdfuploads = uploads.get(i);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/*");
                Toast.makeText(supervisorViewAndDownloadFinalSlide.this, "Downloading file...", Toast.LENGTH_SHORT).show();
                intent.setData(Uri.parse(pdfuploads.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void viewAllFiles() {
        databaseReference = FirebaseDatabase.getInstance().getReference("finalPresentationSlideSubmissionFolder");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                    submissionClass pdf = postsnapshot.getValue(com.example.fyp_management_mobileapp.submissionClass.class);
                    uploads.add(pdf);
                }
                String[] Uploads = new String[uploads.size()];
                for (int i = 0; i < Uploads.length; i++) {
                    Uploads[i] = uploads.get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Uploads) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        text.setTextSize(25);

                        return view;
                    }
                };
                listView.setAdapter(adapter);
                Toast.makeText(supervisorViewAndDownloadFinalSlide.this, "Got list", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(supervisorViewAndDownloadFinalSlide.this, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}