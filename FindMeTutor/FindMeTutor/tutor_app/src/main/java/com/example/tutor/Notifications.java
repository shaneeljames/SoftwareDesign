package com.example.tutor;

import android.app.Activity;
import android.content.Context;

/**
 * Created by admin on 07-Sep-16.
 */

public class Notifications {

    String tutor_student_id ;
    String subject;
    String code;
    String date ;
    String time ;
    String studentName ;
    String studentSurname ;
    String description ;

    int icon;
    Context context;


    Notifications(String tsi,String subj, String cd, String d , String t, String sn , String ss , String desc, int i, Activity context)// Subjects obj)
    {
        this.tutor_student_id = tsi ;
        this.subject = subj;
        this.code = cd;
        this.date = d ;
        this.time = t ;
        this.studentName = sn ;
        this.studentSurname = ss ;
        this.description = desc ;
        this.context = context;
        this.icon = i ;
        // this.subjectObj = obj;

    }

    public void initializeData() {

//        list = new ArrayList<>();
//        String name = null;


    }
}