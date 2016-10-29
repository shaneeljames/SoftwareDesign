package com.example.tutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity implements tutor_AsyncResponse{

    List<Notifications> list = new ArrayList<Notifications>() ;
    RecyclerView rv ;
    tutor_getnotification connect2server ;


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

        Toast.makeText(getApplicationContext(), "tutor: " + id , Toast.LENGTH_SHORT).show();

        connect2server = new tutor_getnotification(this , id , list) ;
        connect2server.delegate = this;
        connect2server.execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabRefresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                finish();
                Intent intent = new Intent(NotificationsActivity.this,NotificationsActivity.class) ;
                startActivity(intent);
            }
        });
    }

    @Override
    public void processFinish(String output) {
        CardViewNotificationsAdapter adapter = new CardViewNotificationsAdapter(connect2server.getList(),this.getApplicationContext()) ;
        rv.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Size: " + adapter.getItemCount() , Toast.LENGTH_SHORT).show();

    }
}
