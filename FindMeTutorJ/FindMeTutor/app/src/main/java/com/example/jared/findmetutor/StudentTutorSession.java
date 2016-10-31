package com.example.jared.findmetutor;

import android.content.Context;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class StudentTutorSession {
    String description;
    String studentCin;
    String studentCout;
    String tutorCin;
    String tutorCout;
    String paid;

    //String venue;

    Context context;


    int photoId;

    public List<StudentTutorSession> events;

    StudentTutorSession(String dsc, String scin, String scout, String tcin, String tcoit, String pd) {
        this.description =dsc;
        this.studentCin = scin;
        this.studentCout = scout;
        this.tutorCin = tcin;
        this.tutorCout = tcoit;
        this.paid = pd;
    }

}
