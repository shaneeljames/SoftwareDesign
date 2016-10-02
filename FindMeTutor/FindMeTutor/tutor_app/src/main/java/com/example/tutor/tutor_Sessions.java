package com.example.tutor;

import android.app.Activity;
import android.content.Context;

import java.util.List;

/**
 * Created by admin on 10-Sep-16.
 */

public class tutor_Sessions {

    String sessionID;
    String tutorID;
    String studentID;
    String subjectID;
    String subjectName;
    String subjectCode ;
    String amount;
    String date;
    String time;
    String description;
    String studentName ;
    String studentSurname ;
    String studentNumber ;
    String studentContact;
    String studentEmail ;
    String status;
    String tutor_checkin ;
    float rating ;




   // String status;

    int icon ;
    Context context;

    public List<tutor_Sessions> list ;
    tutor_Sessions(String tsi,String tid,String studID,String subID,String subjN , String subjC,String a, String d , String t, String sn , String ss , String desc,String sNum,String scontact , String sEmail , String stat,String tchin,float r, int i, Activity context)// Subjects obj)
    {
        this.sessionID = tsi ;
        this.tutorID = tid ;
        this.studentID = studID ;
        this.subjectName = subjN;
        this.subjectCode = subjC;
        this.amount = a ;
        this.date = d ;
        this.time = t ;
        this.studentName = sn ;
        this.studentSurname = ss ;
        this.description = desc ;
        this.studentNumber = sNum ;
        this.studentContact = scontact ;
        this.studentEmail = sEmail ;
        this.status = stat ;
        this.subjectID = subID ;
        this.context = context;
        this.tutor_checkin = tchin ;
        this.rating = r ;

        this.icon = i ;
        // this.subjectObj = obj;

    }

    public void initializeData() {

//        list = new ArrayList<>();
//        String name = null;


    }
}
