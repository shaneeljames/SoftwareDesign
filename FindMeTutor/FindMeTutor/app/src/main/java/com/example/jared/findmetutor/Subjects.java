package com.example.jared.findmetutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jadon on 02-Sep-16.
 */

public class Subjects {

    String subject;
    int icon;
    public List<Subjects> list;

    Subjects(String subj, int ic)
    {
        this.subject = subj;
        this.icon = ic;
    }

    public void initializeData(){
        list = new ArrayList<>();
        list.add(new Subjects( "Basic Analysis", R.drawable.subj));
        list.add(new Subjects( "Software Design", R.drawable.subj));
        list.add(new Subjects( "Hello Games", R.drawable.subj));

    }


}
