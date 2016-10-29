package com.example.tutor;

/**
 * Created by admin on 21-Sep-16.
 */

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
 * Created by jared on 2016/09/02.\
 * Use following command to call, execute the next activity on post, the String Result contains the json string containing all subjects in the Subject Table.
 * Input a student id and receive all subjects related to that student, by inputing a blank string you receive all subjects in the subject table.
 * Example input a student id and you will get the subjects related to that student id from the Student_Subject table. Input "" and you will receive all subjects listed in the subjects table.
 * Command to use is below.
 * getsubject connect2server = new getsubject(this);
 connect2server.execute();
 Returns null if the student number does not exist in the Student_Subject Table.

 */
public class tutor_removeSubject extends AsyncTask<String, String, String> {
    Context parent;
    String result = "";
    String TutorID;
    String StudentID;
    public tutor_AsyncResponse delegate = null; //Notify when async is done

    public tutor_removeSubject(Context par, String tutor_id, String student_id ){
        parent = par;
        TutorID = tutor_id;
       StudentID = student_id ;
    }

    public tutor_removeSubject(Context par){
        parent = par;
    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/tutor_removesubject.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("tutor_id", TutorID);
        parameter.put("subject_id", StudentID);

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
            Toast.makeText(parent.getApplicationContext(), "No current subjects", Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(parent.getApplicationContext(), result, Toast.LENGTH_SHORT).show();

        }
    }

    public String sendResults()
    {
        return result;
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}

