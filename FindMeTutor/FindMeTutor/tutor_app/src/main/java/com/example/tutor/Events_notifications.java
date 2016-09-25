package com.example.tutor;

import java.util.List;


/**
 * Created by admin on 04-Sep-16.
 */

    public class Events_notifications {
        String subname;
        String venue;
        String date;
        String time;
        int photoId;
        String studentName ;
        String Description ;

        public List<Events_notifications> events;

    Events_notifications(String s, String v, String d, String t, int p,String stdName,String desc) {
            this.subname = s;
            this.venue = v;
            this.date=d;
            this.time=t;
            this.photoId = p;
            this.studentName = stdName ;
            this.Description = desc ;
        }


        // This method creates an ArrayList that has three Person objects
        public void initializeData(){

        }

        public void addNewEvent(){
            events.add(new Events_notifications(subname, venue,date,time,photoId , studentName,Description));
        }
    }


