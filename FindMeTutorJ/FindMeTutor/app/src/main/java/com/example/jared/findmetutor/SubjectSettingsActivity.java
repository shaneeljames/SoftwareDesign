package com.example.jared.findmetutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SubjectSettingsActivity extends AppCompatActivity implements AsyncResponse {

    private Toolbar toolbar;
    Button addSubs;
    List<Subjects> list = new ArrayList<Subjects>();
    RecyclerView rv;
    getsubject connect2server;
    getsubject refresh;

    String student_id;
    String id;
   // getsubject asyncTask =new gett(); //

    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Subjects");

        rv = (RecyclerView) findViewById(R.id.rvSubjects);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        //Subjects subj=null;

        //Subjects subjects = new Subjects("h", 0, this);

        //get user info from sharedSettings
        SharedPreferences myprefs =  getSharedPreferences("user", MODE_PRIVATE);
        id= myprefs.getString("student_id", null);

        //Toast.makeText(getApplicationContext(), "11 "+id, Toast.LENGTH_SHORT).show();


        connect2server = new getsubject(this, id, list);
        connect2server.delegate = this;
        connect2server.execute();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });




        addSubs = (Button)findViewById(R.id.adSub);
        addSubs.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v)
            {
                Intent goAddSubs = new Intent(SubjectSettingsActivity.this,AddSubsActivity.class);
                startActivity(goAddSubs);



            }
        });


    }


    public void refreshItems() {
        // Load items
        // ...

        list.clear();
        //Toast.makeText(getApplicationContext(), "refresh items" + list.size() , Toast.LENGTH_SHORT).show();
        refresh = new getsubject(this, id, list);

        refresh.delegate = this;
        refresh.execute();


        // Load complete
        // onItemsLoadComplete();
    }

    public void onItemsLoadComplete() {


        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void processFinish(String output){


        SubjectsViewAdapter adapter = new SubjectsViewAdapter(connect2server.getList(), this.getApplicationContext());
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
        onItemsLoadComplete();
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

    }

    @Override
    public void processFinish2(String out)
    {

    }

    @Override
    public void processFinish3(String outp) {

    }

    public  void adapt(SubjectsViewAdapter ad)
    {

    }
}
