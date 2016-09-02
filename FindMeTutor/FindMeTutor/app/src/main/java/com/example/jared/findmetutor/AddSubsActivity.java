package com.example.jared.findmetutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.arlib.floatingsearchview.FloatingSearchView;

public class AddSubsActivity extends AppCompatActivity {

    private  Toolbar toolbar;
    private SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Subjects");



    }
}
