package com.example.tutor;

import android.content.Context;
import android.os.AsyncTask;

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
 * Created by admin on 27-Sep-16.
 */

public class tutor_decline  extends AsyncTask<String, String, String> {

    Context parent;
    String Tutor_id;
        String Tutor_student_id;
        String Student_id ;

        String result = "";

static String out;

public tutor_decline(Context par, String tutor_student_id, String tutor_id, String student_id){
        parent = par;
        Tutor_id = tutor_id;
        Tutor_student_id = tutor_student_id;
        Student_id = student_id ;
        }
@Override
protected String doInBackground(String... params) {

        URL url = null;

        try {
        url = new URL("http://neural.net16.net/tutor_decline.php");

        } catch (MalformedURLException e) {
        e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("tutor_student_id", Tutor_student_id);
        parameter.put("tutor_id", Tutor_id);
        parameter.put("student_id", Student_id) ;

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
       // Toast.makeText(parent.getApplicationContext(), "Login test "+result, Toast.LENGTH_SHORT).show();



        }

public static void startActivity(Context context) {
        // context.startActivity(new Intent(context, HomeActivity.class).putExtra("user", out));
        }

        //Use this method to get stuff from the Login request claass by just making an object when needed and calling getStuff();
   /* public String getStuff()
    {
       // String pass = Password;
       // return pass;
    }*/


        }

