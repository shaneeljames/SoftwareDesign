package com.example.jared.findmetutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void click(View view){
        query j = new query();
        String temp = j.ans();
        //new query().execute("http://52.35.36.20/connect.php");

        TextView jared = (TextView)findViewById(R.id.textView2);
        jared.setText(temp);

    }

}
