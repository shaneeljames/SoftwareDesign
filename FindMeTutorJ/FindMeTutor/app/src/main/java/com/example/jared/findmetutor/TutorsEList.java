package com.example.jared.findmetutor;

import android.content.Context;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class TutorsEList {

    String tutorID;
    String tutorStdNum;
    String TutorName;
    String tutorEmail;

    Context context;


    int photoId;

    public List<TutorsEList> events;

    TutorsEList(String id, String stdn, String name,  String em,  int p, Context context) {
        this.tutorID=id;
        this.tutorStdNum = stdn;
        this.TutorName = name;
        this.tutorEmail=em;
        this.photoId = p;
        this.context= context;
    }

}
