package com.example.jared.findmetutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class Event {
    String subname;
    String venue;
    String date;
    String time;
    int photoId;

    public List<Event> events;

    Event(String s, String v, String d, String t, int p) {
        this.subname = s;
        this.venue = v;
        this.date=d;
        this.time=t;
        this.photoId = p;
    }


    // This method creates an ArrayList that has three Person objects
    public void initializeData(){
        events = new ArrayList<>();
        events.add(new Event("Linear Algebra", "WSS 3","20th September", "1pm", R.drawable.session));
        events.add(new Event("Basic Analysis", "CB 123","24th September", "8am", R.drawable.session));
        events.add(new Event("CAM", "WSS 3","23th September", "1pm", R.drawable.session));
        events.add(new Event("Operating Systems", "CB 123","27th September", "8am", R.drawable.session));
        events.add(new Event("Multivariable Calculus", "Exams Hall 3","25th September", "1pm", R.drawable.session));
        events.add(new Event("Physics", "CB 123","27th September", "8am", R.drawable.session));
    }

    public void addNewEvent(){
        events.add(new Event(subname, venue,date,time,photoId));
    }
}
