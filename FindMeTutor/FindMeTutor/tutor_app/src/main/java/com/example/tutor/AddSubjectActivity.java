package com.example.tutor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class AddSubjectActivity extends AppCompatActivity implements tutor_AsyncResponse{

    private  Toolbar toolbar;
    private SearchView mSearchView;
    RecyclerView rv;
    tutor_getallsubjects connect2server;
    String tutor_id;
    List<Subjects> list = new ArrayList<Subjects>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Subjects");

        rv = (RecyclerView) findViewById(R.id.rvAddSubj);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences myprefs =  getSharedPreferences("user", MODE_PRIVATE);
        tutor_id = myprefs.getString("tutor_id", null);


        connect2server = new tutor_getallsubjects(this, tutor_id, list);
        connect2server.delegate = this;
        connect2server.execute();


    }

    @Override
    public void processFinish(String output) {
        list = connect2server.getList();

      //  Toast.makeText(this, list.get(3).subject + " id :"+list.get(3).subjID, Toast.LENGTH_SHORT).show();
        SubjectsAddViewAdapter adapter = new SubjectsAddViewAdapter(list, tutor_id, this,this.getApplicationContext());
        rv.setAdapter(adapter);
    }

}
