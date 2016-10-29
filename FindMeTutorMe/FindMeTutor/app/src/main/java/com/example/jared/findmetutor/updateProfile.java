package com.example.jared.findmetutor;

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
 * Created by admin on 24-Sep-16.
 */

public class updateProfile extends AsyncTask<String, String, String> {
    Activity parent;
    String id ;
    String Contact ;
    String Name ;
    String Surname ;
    String Password;

    String result = "";

    static String out;
    public AsyncResponse delegate = null;

    public updateProfile(Activity par, String id, String fn, String ln , String num , String pass  ){
        parent = par;

        this.id = id ;
        this.Name=fn;
        this.Surname=ln;
        this.Contact=num;
        this.Password=pass;
    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/student_updateprofile.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("student_id", id);
        parameter.put("student_fname", Name);
        parameter.put("student_lname", Surname);
        parameter.put("student_contact_num", Contact);
        parameter.put("student_password", Password);

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
        // Toast.makeText(parent.getApplicationContext(), "Login test "+result.substring(1,2), Toast.LENGTH_SHORT).show();
       // Toast.makeText(parent.getApplicationContext(), "Update: " + result, Toast.LENGTH_SHORT).show();

        delegate.processFinish(result);


    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class).putExtra("user", out));
    }

    //Use this method to get stuff from the Login request claass by just making an object when needed and calling getStuff();
    public String getStuff()
    {
        String pass = Password;
        return pass;
    }


}

