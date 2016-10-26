package com.example.jared.findmetutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    Button send;


    String id;
    String subjId;
    String fname;
    String lname;

    String date;
    String time;
    String dsp;


    List<Subjects> list = new ArrayList<Subjects>();
    List<TutorsEList> eList = new ArrayList<TutorsEList>();
    getsubject connect2server;
    getTutorEmailList getList;

    RequestActivity temp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Request Tutor");
        temp = this;



        SharedPreferences myprefs =  getSharedPreferences("user", MODE_PRIVATE);
        id= myprefs.getString("student_id", null);
        fname = myprefs.getString("student_fname",null);
        lname = myprefs.getString("student_lname",null);

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

    public void sendEmails(List<TutorsEList> list){

        for(int i=0; i<list.size();i++) {
            String fromEmail = "FindmetutorSD@gmail.com";
            String fromPassword = "findmetutors";
            String toEmails = list.get(i).tutorEmail.toString(); //Sessions.get(i).studentEmail.toString() ;
            String adminEmail = "admin@gmail.com";
            String emailSubject = "Find Me Tutor - Session Request";
            String adminSubject = "App Registration Mail";
            String emailBody =
                    "Dear " + list.get(i).TutorName
                            + "<br><br>"+fname+" "+lname+" is requesting a tutorial session"
                            + "<br><br>"
                            +"Subject : "+curSelection+ "<br>"
                            +"Description : "+ dsp +"<br>"
                            +"Date : " + date+ "<br>"
                            +"Time :" +time+ "<br>"
                            +"<br>"
                            +"Please log into the FindMeTutor app Should you wish to accept/decline this request";

            String adminBody = "Your message";
            new SendMailTask(getApplicationContext()).execute(fromEmail,
                    fromPassword, toEmails, emailSubject, emailBody);
        }
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
        //get the relevant tutor email list
        getList = new getTutorEmailList(temp, subjId, eList );
        getList.delegate=this;
        getList.execute();

    }

    @Override
    public void processFinish3(String outp) {
        //On return from getList
        Toast.makeText(temp, "Sending emails", Toast.LENGTH_SHORT).show();
        List<TutorsEList> emailsList = getList.getList();
        //sendEmails(emailsList);


        //Intent goHome = new Intent(RequestActivity.this, HomeActivity.class);
        //goHome.putExtra("key","1");
        //startActivity(goHome);

        SharedPreferences myprefs= getSharedPreferences("user", MODE_PRIVATE);
        String email=myprefs.getString("student_email",null);
        String pass= myprefs.getString("student_password", null);
        login in = new login(this, email, pass);
        in.execute();

    }

    public void request(){
        String tutor_id = "1"; //Default Tutor
        String student_id = id;
        String subject_id = subjId;
        String amount = "R100";

        time = currentTime();
        date = currentDate();
        String subj = getSubject();

        EditText desc = (EditText)findViewById(R.id.descriptionTxt);
        dsp = desc.getText().toString();

        Toast.makeText(getApplicationContext(), date+" "+time +" "+subj+" "+dsp, Toast.LENGTH_SHORT).show();

        requestSession session = new requestSession(this, tutor_id, student_id, subject_id, amount, date,time,dsp);
        session.delegate=this;
        //sends to process2 when done
        session.execute();



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
        String ret = datePicker.getDayOfMonth()+"/"+datePicker.getMonth() + "/"+datePicker.getYear();
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


