package com.example.jared.findmetutor;

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import static android.R.attr.data;

/**
 * Created by Jadon on 02-Sep-16.
 */

public class Subjects {

    String subjID;
    String subject;
    String code;
    int icon;
    Context context;



    Subjects(String id, String subj, String cd,int ic, Context context)// Subjects obj)
    {
        this.subjID = id;
        this.subject = subj;
        this.code = cd;
        this.icon = ic;
        this.context = context;
       // this.subjectObj = obj;

    }

    public void initializeData(){

//        list = new ArrayList<>();
//        String name = null;







    }


}
