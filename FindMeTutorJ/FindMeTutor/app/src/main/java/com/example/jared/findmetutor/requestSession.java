package com.example.jared.findmetutor;

import android.app.Activity;
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
 * Created by jared on 2016/08/04.
 */
public class requestSession extends AsyncTask<String, String, String> {
    Activity parent;
    String tutor_id;
    String student_id;
    String subject_id;
    String amount;
    String date;
    String time;
    String description;
    String total;


    String result = "";

    public AsyncResponse delegate = null;

    public requestSession(Activity par, String tid, String sid, String subjid, String amt, String dte, String tme, String desc, String tot){
        parent = par;
        tutor_id = tid;
        student_id = sid;
        subject_id = subjid;
        amount = amt;
        date = dte;
        time = tme;
        description = desc;
        total = tot;

    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/student_requestsession.php");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("TutorID", tutor_id);
        parameter.put("StudentID", student_id);
        parameter.put("SubjectID", subject_id);
        parameter.put("Amount", amount);
        parameter.put("Date", date);
        parameter.put("Time", time);
        parameter.put("Description",description);
        parameter.put("Total", total);

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
            Toast.makeText(parent.getApplicationContext(), "Registration Unsuccessful "+result, Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(parent.getApplicationContext(), "Session Request Successful! " +result, Toast.LENGTH_SHORT).show();
        }

        delegate.processFinish3(result);
    }

}

