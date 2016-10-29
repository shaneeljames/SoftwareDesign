package com.example.jared.findmetutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jared on 2016/09/02.\
 * Use following command to call, execute the next activity on post, the String Result contains the json string containing all subjects in the Subject Table.
 * Input a student id and receive all subjects related to that student, by inputing a blank string you receive all subjects in the subject table.
 * Example input a student id and you will get the subjects related to that student id from the Student_Subject table. Input "" and you will receive all subjects listed in the subjects table.
 * Command to use is below.
 * getsubject connect2server = new getsubject(this);
   connect2server.execute();
 Returns null if the student number does not exist in the Student_Subject Table.

 */
public class studentTutor extends AsyncTask<String, String, String> {
    Activity parent;
    String result = "";
    String SessionID;

    List<Tutors> in;
    public AsyncResponse delegate = null; //Notify when async is done

    public studentTutor(Activity par, String session_id, List<Tutors> obj){
        parent = par;
        SessionID = session_id;
        in = obj;
    }

    public studentTutor(Activity par){
        parent = par;
    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/student_tutor.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("tutorID", SessionID);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : parameter.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            try {
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            postData.append('=');
            try {
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        byte[] postDataBytes = new byte[0];
        try {
            postDataBytes = postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        try {
            conn.getOutputStream().write(postDataBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (int c; (c = in.read()) >= 0;){
                result = result + (char)c;
                //System.out.print((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        //Handle Result
       if(result.equals("null")){
            //Toast.makeText(parent.getApplicationContext(), "No current tutors", Toast.LENGTH_SHORT).show();

        }else {
           //If they're in the DB then login to the Home page
           //Toast.makeText(parent.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
           //startActivity(parent);

           //this.result = result;

           try {

               JSONArray jsonArr = new JSONArray(result);
              // Toast.makeText(parent.getApplicationContext(), "making object " + result, Toast.LENGTH_SHORT).show();
               String tutID="";
               String tutNum="";
               String fname = "";
               String lName ="";
               String fullname="";
               String contact="";
               String email ="";
               String qualis="";

               String rating ="3";


               //Subjects pass = null;
               //Subjects subjects = new Subjects("h", 0, parent, pass);


               for (int i = 0; i < jsonArr.length(); i++) {
                   JSONObject jsObj = jsonArr.getJSONObject(i);
                   tutID = jsObj.getString("tutor_id");
                   tutNum = jsObj.getString("tutor_student_num");
                   fname = jsObj.getString("tutor_fname");
                   lName = jsObj.getString("tutor_lname");
                   fullname = fname + " " +lName;
                   contact=jsObj.getString("tutor_contact_num");
                   email=jsObj.getString("tutor_email");
                   qualis=jsObj.getString("tutor_qualifications");
                   rating=jsObj.getString("tutor_rating");

                   //code = jsObj.getString("subject_course_code");
                  //Toast.makeText(parent.getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                   in.add(new Tutors( tutID, tutNum, fullname, rating,contact,email,qualis, R.drawable.subj, parent));
               }

           } catch (JSONException e) {
               e.printStackTrace();
           }

           //Now we have their subjects


           delegate.processFinish2(result); //let the other clsses know when onPost is finished
       }
    }

    public String sendResults()
    {
        return result;
    }

    public List getList()
    {
        return in;
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}

