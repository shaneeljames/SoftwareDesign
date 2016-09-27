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


    String id;
    String subjId;

    List<Subjects> list = new ArrayList<Subjects>();
    getsubject connect2server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Request Tutor");



        SharedPreferences myprefs =  getSharedPreferences("user", MODE_PRIVATE);
        id= myprefs.getString("student_id", null);

        connect2server = new getsubject(this, id, list);
        connect2server.delegate = this;
        connect2server.execute();



        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setMinDate(System.currentTimeMillis()-1000);

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
    @Override
    public  void processFinish2(String out){
        Intent goHome = new Intent(this, HomeFragment.class);
        startActivity(goHome);
    }

    public void request(){
        String tutor_id = "1";
        String student_id = id;
        String subject_id = subjId;
        String amount = "R100";

        String time = currentTime();
        String date = currentDate();
        String subj = getSubject();

        EditText desc = (EditText)findViewById(R.id.descriptionTxt);
        String dsp = desc.getText().toString();

        Toast.makeText(getApplicationContext(), date+" "+time +" "+subj+" "+dsp, Toast.LENGTH_SHORT).show();

        requestSession session = new requestSession(this, tutor_id, student_id, subject_id, amount, date,time,dsp);
        session.delegate=this;
        session.execute();



/*        Session ev = new Session(subj,"Temp Venue", date,time,R.drawable.session);
        //ev.addNewEvent();

        //Go to the homeActivity




       // set Fragmentclass Arguments




        Bundle bundle = new Bundle();
        bundle.putString("subject",subj);
        bundle.putString("date",date);
        bundle.putString("time",time);

        HomeFragment fragInfo = new HomeFragment();
        fragInfo.setArguments(bundle);*/

       // Intent goHome = new Intent(this, HomeFragment.class);
        //startActivity(goHome);





        //Maybe push notifications from here




    }

    public String getSubject(){
        return curSelection;
    }

    public String currentTime() {
        String mcurrentTime =  timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
        return mcurrentTime;
    }

    public String currentDate() {
        StringBuilder mcurrentDate = new StringBuilder();
        int month = datePicker.getMonth() + 1;
        String ret = datePicker.getDayOfMonth()+" "+getMonth(month) + " "+datePicker.getYear();
        return ret;
    }

    public String getMonth(int month){
        String calMonth="";

        switch (month){
            case 1:calMonth="January";
                break;

            case 2:calMonth="Feburary";
                break;

            case 3:calMonth="March";
                break;

            case 4:calMonth="April";
                break;

            case 5:calMonth="May";
                break;

            case 6:calMonth="June";
                break;

            case 7:calMonth="July";
                break;

            case 8:calMonth="August";
                break;

            case 9:calMonth="September";
                break;

            case 10:calMonth="October";
                break;

            case 11:calMonth="November";
                break;

            case 12:calMonth="December";
                break;
            default:calMonth=calMonth;
                break;

        }

        return  calMonth;


    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner1.getSelectedItem());



        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            curSelection = firstItem;
            if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
                // ToDo when first item is selected
                Subjects getId = list.get(pos);
                subjId = getId.subjID;
            } else {
                Subjects getId = list.get(pos);
                subjId = getId.subjID;

                curSelection = parent.getItemAtPosition(pos).toString();


                // Todo when item is selected by the user
                Toast.makeText(parent.getContext(), "You have selected : " + curSelection + "ID :"+subjId, Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }


}


