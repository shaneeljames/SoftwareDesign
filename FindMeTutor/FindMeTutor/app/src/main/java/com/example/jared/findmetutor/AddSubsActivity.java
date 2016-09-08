package com.example.jared.findmetutor;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;
import java.util.List;

public class AddSubsActivity extends AppCompatActivity implements AsyncResponse{

    private  Toolbar toolbar;
    private SearchView mSearchView;
    RecyclerView rv;
    getallsubjects connect2server;
    String student_id;
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
        student_id = myprefs.getString("student_id", null);


        connect2server = new getallsubjects(this, student_id, list);
        connect2server.delegate = this;
        connect2server.execute();


    }

    @Override
    public void processFinish(String output) {
        list = connect2server.getList();

        //Toast.makeText(this, list.get(3).subject + " id :"+list.get(3).subjID, Toast.LENGTH_SHORT).show();
        SubjectsAddViewAdapter adapter = new SubjectsAddViewAdapter(list, student_id, this,this.getApplicationContext());
        rv.setAdapter(adapter);
    }

    @Override
    public void processFinish2(String out) {

    }
}
