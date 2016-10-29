package com.example.jared.findmetutor;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class Session {
    String sessionID;
    String tutorID;
    String tutorStdNum;
    String tutorName;
    String subjectName;
    String subjectID;
    String amount;
    String date;
    String time;
    String desc;
    String status;
    String available;
    String unavailable;
    String total;

    //String venue;

    Context context;


    int photoId;

    public List<Session> events;

    Session(String sesId, String tId, String tSdtN, String tNme, String subName,  String subId, String amt, String dte, String tme, String dsc, String st, String av, String unav, String totl, int p, Context context) {
        this.sessionID = sesId;
        this.tutorID = tId;
        this.tutorStdNum=tSdtN;
        this.tutorName=tNme;
        this.subjectName=subName;
        this.subjectID=subId;
        this.amount = amt;
        this.date = dte;
        this.time = tme;
        this.desc = dsc;
        this.status = st;
        this.available = av;
        this.unavailable= unav;
        this.total = totl;
        this.photoId = p;
        this.context= context;
    }

}
