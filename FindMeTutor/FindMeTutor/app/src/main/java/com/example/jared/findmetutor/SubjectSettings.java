package com.example.jared.findmetutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class SubjectSettings extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Subjects");

        RecyclerView rv = (RecyclerView) findViewById(R.id.rvSubjects);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        Subjects subjects = new Subjects("h", 0);
        subjects.initializeData();

        SubjectsViewAdapter adapter = new SubjectsViewAdapter(subjects.list, this.getApplicationContext());
        rv.setAdapter(adapter);
    }
}
