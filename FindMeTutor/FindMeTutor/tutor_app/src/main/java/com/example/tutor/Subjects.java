package com.example.tutor;

import android.content.Context;

/**
 * Created by Jadon on 02-Sep-16.
 */

public class Subjects {

    String subjID;
    String subject;
    String code;
    int icon;
    Context Context;

    Subjects(String sid ,String subj,String c, int ic, Context context)
    {
        this.subjID = sid ;
        this.subject = subj;
        this.code = c;
        this.icon = ic;
        this.Context = context ;

    }


}
