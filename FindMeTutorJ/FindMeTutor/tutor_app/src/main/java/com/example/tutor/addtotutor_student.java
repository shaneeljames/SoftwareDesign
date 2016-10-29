package com.example.tutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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
import java.util.Map;

/**
 * All tested and working to add a tutor_subject entry, run the command below and it will execute, it adds a new entry based on what you feed into it. Run your next activity in the on post method.
 * Created by jared on 2016/09/02.
 *addtotutor_student connect2server = new addtotutor_student(this, "719238", "719238","5","jare50","10","02 September 2016","Complete","Description");
  connect2server.execute();

 */
public class addtotutor_student extends AsyncTask<String, String, String> {

    Activity parent;
    String TutorID;
    String StudentID;
    String SubjectID;
    String Amount;
    String Time;
    String Date;
    String Status;
    String Description;

    String result = "";

    public addtotutor_student(Activity par, String tutorID, String studentID, String subjectID, String amount, String time, String date, String status,String description){
        parent = par;
        TutorID =  tutorID;
        StudentID = studentID;
        SubjectID = subjectID;
        Amount = amount;
        Time = time;
        Date = date;
        Status = status;
        Description = description;
    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://52.35.36.20/add_tutor_student.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("TutorID", TutorID);
        parameter.put("StudentID", StudentID);
        parameter.put("SubjectID", SubjectID);
        parameter.put("Amount", Amount);
        parameter.put("Time", Time);
        parameter.put("Date", Date);
        parameter.put("Status", Status);
        parameter.put("Description", Description);

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
            Toast.makeText(parent.getApplicationContext(), "Add Data Unsuccessful", Toast.LENGTH_SHORT).show();

        }else{
            //If they're in the DB then login to the Home page
            Toast.makeText(parent.getApplicationContext(), "Add Data Successful", Toast.LENGTH_SHORT).show();

        }

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

}

