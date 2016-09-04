package com.example.jared.findmetutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;


public class RequestActivity extends AppCompatActivity implements AsyncResponse {

    Toolbar toolbar;
    Spinner spinner1;
    TimePicker timePicker;

    DatePicker datePicker;
    Button req;
    String curSelection;

    int month;

    List<Subjects> list = new ArrayList<Subjects>();
    getsubject connect2server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Request Tutor");



        SharedPreferences myprefs =  getSharedPreferences("user", MODE_PRIVATE);
        String id= myprefs.getString("student_id", null);

        connect2server = new getsubject(this, id, list);
        connect2server.delegate = this;
        connect2server.execute();



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

    @Override
    public void processFinish(String output){
        List<Subjects> curr = connect2server.getList();
        String [] listA = new String[curr.size()];
       // String [] listA = null;
        //this.list = connect2server.in;
        for(int i=0;i<curr.size();i++)
        {
            //Subjects cur =  connect2server.;
           listA[i] =curr.get(i).subject+ " - "+ curr.get(i).code;
        }


       // Toast.makeText(getApplicationContext(), "Login Successful "+listA[1], Toast.LENGTH_SHORT).show();


        spinner1 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listA);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new ItemSelectedListener());


         /*SubjectsViewAdapter adapter = new SubjectsViewAdapter(connect2server.getList(), this.getApplicationContext());
        rv.setAdapter(adapter);*/
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

    }

    public void request(){
        String time = currentTime();
        String date = currentDate();
        String subj = getSubject();

        EditText desc = (EditText)findViewById(R.id.descriptionTxt);
        String dsp = desc.getText().toString();

        Toast.makeText(getApplicationContext(), date+" "+time +" "+subj+" "+dsp, Toast.LENGTH_SHORT).show();



/*        Event ev = new Event(subj,"Temp Venue", date,time,R.drawable.session);
        //ev.addNewEvent();

        //Go to the homeActivity




       // set Fragmentclass Arguments




        Bundle bundle = new Bundle();
        bundle.putString("subject",subj);
        bundle.putString("date",date);
        bundle.putString("time",time);

        HomeFragment fragInfo = new HomeFragment();
        fragInfo.setArguments(bundle);*/

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


