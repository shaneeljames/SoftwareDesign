package com.example.tutor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity implements tutor_AsyncResponse{

    List<Notifications> list = new ArrayList<Notifications>() ;
    RecyclerView rv ;
    tutor_getsubject connect2server ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         rv = (RecyclerView) findViewById(R.id.rvNotifications) ;
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences myprefs = getSharedPreferences("user",MODE_PRIVATE ) ;
        String id = myprefs.getString("tutor_id", null) ;

        connect2server = new tutor_getsubject(this , id , list) ;
        connect2server.delegate = this;
        connect2server.execute();
    }

    @Override
    public void processFinish(String output) {
        CardViewNotificationsAdapter adapter = new CardViewNotificationsAdapter(connect2server.getList(),this.getApplicationContext()) ;
        rv.setAdapter(adapter);
       // Toast.makeText(getApplicationContext(), "Size: " + adapter.getItemCount() , Toast.LENGTH_SHORT).show();

    }
}
