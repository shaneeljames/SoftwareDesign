package com.example.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SubjectSettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Button addSubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tutor_Subjects");

        RecyclerView rv = (RecyclerView) findViewById(R.id.rvSubjects);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        Subjects subjects = new Subjects("h", 0);
        subjects.initializeData();

        SubjectsViewAdapter adapter = new SubjectsViewAdapter(subjects.list, this.getApplicationContext());
        rv.setAdapter(adapter);

        addSubs = (Button)findViewById(R.id.adSub);
        addSubs.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v)
            {
                Intent goAddSubs = new Intent(SubjectSettingsActivity.this,RequestActivity.class);
                startActivity(goAddSubs);
            }
        });


    }
}
