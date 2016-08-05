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
        register j = new register(this, "Jared","Naidoo", "0118229141", "Jared@gmail.com","19935050", "My dogs name?", "RUfus", "Student");
        j.execute();
    }



 }
