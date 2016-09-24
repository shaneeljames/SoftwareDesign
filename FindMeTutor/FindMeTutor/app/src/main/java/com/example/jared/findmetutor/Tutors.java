package com.example.jared.findmetutor;

import android.content.Context;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class Tutors {

    String tutorID;
    String TutorName;
    String Rating;
    String tutorContact;
    String tutorEmail;
    String tutorQual;

    //String venue;

    Context context;


    int photoId;

    public List<Tutors> events;

    Tutors(String id, String name, String rating, String num, String em, String quali, int p, Context context) {
        this.tutorID=id;
        this.TutorName = name;
        this.Rating = rating;
        this.tutorContact = num;
        this.tutorEmail=em;
        this.tutorQual=quali;
        this.photoId = p;
        this.context= context;
    }

}
