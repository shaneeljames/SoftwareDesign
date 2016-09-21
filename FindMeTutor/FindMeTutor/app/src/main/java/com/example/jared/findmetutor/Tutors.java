package com.example.jared.findmetutor;

import android.content.Context;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class Tutors {
    String TutorName;
    String Rating;

    //String venue;

    Context context;


    int photoId;

    public List<Tutors> events;

    Tutors(String name, String rating, int p, Context context) {
        this.TutorName = name;
        this.Rating = rating;
        this.photoId = p;
        this.context= context;
    }

}
