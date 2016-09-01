package com.example.jared.findmetutor;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jadon on 01-Sep-16.
 */

public class Settings {

    String sName;

    int photoId;

    public List<Settings> set;

    Settings(String s, int p) {
        this.sName = s;

        this.photoId = p;
    }




    // This method creates an ArrayList that has three Person objects
    public void initializeData(){
        set = new ArrayList<>();
        set.add(new Settings( "My Subjects", R.drawable.bookss));
        set.add(new Settings( "Account", R.drawable.gear));
        set.add(new Settings( "Help", R.drawable.help));

    }

}


