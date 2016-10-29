package com.example.tutor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddSubjectActivity extends AppCompatActivity implements tutor_AsyncResponse{

    private  Toolbar toolbar;
    private SearchView mSearchView;
    RecyclerView rv;
    tutor_getallsubjects connect2server;
    tutor_getallsubjects refresh2server ;
    SwipeRefreshLayout swipeRefreshLayout ;
    SharedPreferences myprefs ;

    String tutor_id;
    List<Subjects> list = new ArrayList<Subjects>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Subjects");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout2) ;


        rv = (RecyclerView) findViewById(R.id.rvAddSubj);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

         myprefs =  getSharedPreferences("user", MODE_PRIVATE);
        tutor_id = myprefs.getString("tutor_id", null);


        connect2server = new tutor_getallsubjects(this, tutor_id, list);
        connect2server.delegate = this;
        connect2server.execute();

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright)
                ,getResources().getColor(android.R.color.holo_green_light)
                , getResources().getColor(android.R.color.holo_orange_light)
                , getResources().getColor(android.R.color.holo_red_light));





        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems() ;
                Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });




        //get Your Current Locationif ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {


    }

    public void refreshItems() {

        list.clear();
        refresh2server = new tutor_getallsubjects(this, tutor_id, list);
        refresh2server.delegate = this ;
        refresh2server.execute()  ;

    }

    public void onItemsLoadComplete()
    {
        swipeRefreshLayout.setRefreshing(false);

    }


    @Override
    public void processFinish(String output) {
        list = connect2server.getList();

      //  Toast.makeText(this, list.get(3).subject + " id :"+list.get(3).subjID, Toast.LENGTH_SHORT).show();
        SubjectsAddViewAdapter adapter = new SubjectsAddViewAdapter(list, tutor_id, this,this.getApplicationContext());
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
        onItemsLoadComplete();

    }

}
