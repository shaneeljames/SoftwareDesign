package com.example.jared.findmetutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TimePicker;


public class RequestActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner1;
    TimePicker timePicker;

    DatePicker datePicker;
    Button req;
    String curSelection;

    int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Request Tutor");

        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(new ItemSelectedListener());

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        req = (Button)findViewById(R.id.btn_request);

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });



    }

    public void request(){
        String time = currentTime();
        String date = currentDate();
        String subj = getSubject();

        Event ev = new Event(subj,"Temp Venue", date,time,R.drawable.session);
        //ev.addNewEvent();

        //Go to the homeActivity




       // set Fragmentclass Arguments




        Bundle bundle = new Bundle();
        bundle.putString("subject",subj);
        bundle.putString("date",date);
        bundle.putString("time",time);

        HomeFragment fragInfo = new HomeFragment();
        fragInfo.setArguments(bundle);

        Intent goHome = new Intent(this, HomeFragment.class);
        startActivity(goHome);





        //Maybe push notifications from here




    }

    public String getSubject(){
        return curSelection;
    }

    public String currentTime() {
        String mcurrentTime = "Time: " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
        return mcurrentTime;
    }

    public String currentDate() {
        StringBuilder mcurrentDate = new StringBuilder();
        month = datePicker.getMonth() + 1;
        mcurrentDate.append("Date: " + month + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear());
        return mcurrentDate.toString();
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner1.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                curSelection = parent.getItemAtPosition(pos).toString();
                Toast.makeText(parent.getContext(), "You have selected : " + curSelection, Toast.LENGTH_LONG).show();

                // Todo when item is selected by the user
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }


}


