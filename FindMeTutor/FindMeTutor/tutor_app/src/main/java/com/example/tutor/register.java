package com.example.tutor;

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
public class register extends AsyncTask<String, String, String> {
    Activity parent;
    String Firstname;
    String Lastname;
    String Contact_Number;
    String email;
    String StudentNumber;
    String Password;
    String Security_question;
    String Answer;
    String Tutor_student;

    String result = "";

    public register(Activity par, String first_name, String last_name, String contact_number, String studentNumber, String student_email, String password){
        parent = par;
        Firstname = first_name;
        Lastname = last_name;
        Contact_Number = contact_number;
        StudentNumber = studentNumber;
        email = student_email;
        Password = password;

    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/tutor_register.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("Name", Firstname);
        parameter.put("Surname", Lastname);
        parameter.put("Contactnumber", Contact_Number);
        parameter.put("StudentNumber", StudentNumber);
        parameter.put("Email", email);
        parameter.put("Password", Password);

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
            Toast.makeText(parent.getApplicationContext(), "Registration Unsuccessful" + result, Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(parent.getApplicationContext(), "Registration Successful!" + result, Toast.LENGTH_SHORT).show();
        }
    }

}

