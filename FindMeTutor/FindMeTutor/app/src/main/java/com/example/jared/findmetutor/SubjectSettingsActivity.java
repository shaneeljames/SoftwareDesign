package com.example.jared.findmetutor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SubjectSettingsActivity extends AppCompatActivity implements AsyncResponse {

    private Toolbar toolbar;
    Button addSubs;
    List<Subjects> list = new ArrayList<Subjects>();
    RecyclerView rv;
    getsubject connect2server;

    String student_id;
   // getsubject asyncTask =new gett(); //



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Subjects");

        rv = (RecyclerView) findViewById(R.id.rvSubjects);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //Subjects subj=null;

        //Subjects subjects = new Subjects("h", 0, this);

        //get user info from sharedSettings
        SharedPreferences myprefs =  getSharedPreferences("user", MODE_PRIVATE);
        String id= myprefs.getString("student_id", null);

        //Toast.makeText(getApplicationContext(), "11 "+id, Toast.LENGTH_SHORT).show();


        connect2server = new getsubject(this, id, list);
        connect2server.delegate = this;
        connect2server.execute();




        addSubs = (Button)findViewById(R.id.adSub);
        addSubs.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v)
            {
                //Intent goAddSubs = new Intent(SubjectSettingsActivity.this,RequestActivity.class);
                //startActivity(goAddSubs);



            }
        });


    }

    @Override
    public void processFinish(String output){


        SubjectsViewAdapter adapter = new SubjectsViewAdapter(connect2server.getList(), this.getApplicationContext());
        rv.setAdapter(adapter);
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

    }

    public  void adapt(SubjectsViewAdapter ad)
    {

    }
}
