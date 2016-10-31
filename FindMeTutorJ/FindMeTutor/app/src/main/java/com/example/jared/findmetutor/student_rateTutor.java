package com.example.jared.findmetutor;

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
 * Created by admin on 29-Sep-16.
 */

public class student_rateTutor extends AsyncTask<String, String, String> {
        Context parent;
        String result = "";
        String StudentID;
        String TutorStudentID ;
        String Rating ;

public AsyncResponse delegate = null; //Notify when async is done

public student_rateTutor(Context par, String tsi , String r){
        parent = par;
        TutorStudentID = tsi ;
        Rating = r ;

        }

public student_rateTutor(Context par){
        parent = par;
        }
@Override
protected String doInBackground(String... params) {

        URL url = null;

        try {
        url = new URL("http://neural.net16.net/student_ratetutor.php");

        } catch (MalformedURLException e) {
        e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("tutor_student_id", TutorStudentID);
        parameter.put("student_rating", Rating);

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

    //Toast.makeText(parent, result, Toast.LENGTH_SHORT).show();
        //Handle Result


        }

    public String sendResults()
        {
        return result;
        }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
        }
        }

