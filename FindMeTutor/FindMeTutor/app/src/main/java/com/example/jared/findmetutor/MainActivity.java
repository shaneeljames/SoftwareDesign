package com.example.jared.findmetutor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view){
        //connect_php j = new connect_php(this,"http://52.35.36.20/connect.php");
        //j.execute();

        send_php j = new send_php(this,"http://52.35.36.20/connect.php","SELECT");
        j.execute();
    }



 }
