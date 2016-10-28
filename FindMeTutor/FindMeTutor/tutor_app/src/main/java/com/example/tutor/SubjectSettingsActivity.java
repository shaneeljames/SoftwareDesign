package com.example.tutor;

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

import java.util.ArrayList;
import java.util.List;

public class SubjectSettingsActivity extends AppCompatActivity implements tutor_AsyncResponse {

    private Toolbar toolbar;
    Button addSubs;
    List<Subjects> list = new ArrayList<Subjects>();
    RecyclerView rv;
    tutor_getsubject connect2server;
    tutor_getsubject refresh2server;
    String id = null;

    String tutor_id;
    SharedPreferences myprefs ;

    SwipeRefreshLayout swipeRefreshLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tutor_Subjects");

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout) ;

        rv = (RecyclerView) findViewById(R.id.rvSubjects);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

       // Subjects subjects = new Subjects("h", 0);
       // subjects.initializeData();

         myprefs =  getSharedPreferences("user", MODE_PRIVATE);
         id= myprefs.getString("tutor_id", null);

        toolbar.setTitle(id);


        connect2server = new tutor_getsubject(this, id, list);
        connect2server.delegate = this;
        connect2server.execute();

        swipeRefreshLayout.setColorSchemeColors( getResources().getColor(android.R.color.holo_blue_bright),getResources().getColor(android.R.color.holo_green_light)
                ,getResources().getColor(android.R.color.holo_orange_light),getResources().getColor(android.R.color.holo_red_light));


        addSubs = (Button)findViewById(R.id.adSub);
        addSubs.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v)
            {
                Intent goAddSubs = new Intent(SubjectSettingsActivity.this,AddSubjectActivity.class);
                startActivity(goAddSubs);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems() ;
            }


        });


    }

    @Override
    public void processFinish(String output) {
        SubjectsViewAdapter adapter = new SubjectsViewAdapter(connect2server.getList(), this.getApplicationContext());
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
        onItemsLoadComplete();

    }

    public void refreshItems() {

        list.clear();
        refresh2server = new tutor_getsubject(this,id,list);
        refresh2server.delegate = this ;
        refresh2server.execute()  ;
    }

    public void onItemsLoadComplete()
    {
        swipeRefreshLayout.setRefreshing(false);
    }


}
