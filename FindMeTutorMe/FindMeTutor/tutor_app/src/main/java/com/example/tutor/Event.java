package com.example.tutor;

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

    }

    public void addNewEvent(){
        events.add(new Event(subname, venue,date,time,photoId));
    }
}
