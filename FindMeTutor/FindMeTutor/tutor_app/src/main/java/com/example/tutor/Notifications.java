package com.example.tutor;

import android.content.Context;

/**
 * Created by admin on 07-Sep-16.
 */

public class Notifications {

    String subject;
    String code;
    String date ;
    String time ;
    String studentName ;
    String studentSurname ;
    String description ;

    int icon;
    Context context;


    Notifications(String subj, String cd, String d ,String t, String sn , String ss , String desc,int i, Context context)// Subjects obj)
    {
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