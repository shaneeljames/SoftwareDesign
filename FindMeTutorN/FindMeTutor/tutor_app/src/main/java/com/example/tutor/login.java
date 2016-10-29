package com.example.tutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jared on 2016/08/04.
 */
public class login extends AsyncTask<String, String, String> {
    Activity parent;
    String Email;
    String Password;
    int Check ;
    ProgressBar bar ;
    EditText txtPass ;
    public int  Test= 1 ;
    final public CountDownLatch signal = new CountDownLatch(1);

    String Password1 ;

     String result = "";

    static String out;

    public LoginActivity delegate = null; //Notify when async is done

    public login(Activity par, String email, String password, int c, ProgressBar b, EditText e){
        parent = par;
        Email = email;
        Password = password;
        Check = c;
        bar = b;
        txtPass = e ;

    }


    @Override
    protected void onPreExecute(){
       //LoginActivity.bar.setVisibility(View.VISIBLE);
    }

    @Override
    public String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/tutor_login.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("Email", Email);
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
    public void onPostExecute(String result) {
        //Handle Result
       // Toast.makeText(parent.getApplicationContext(), "Login test "+result.substring(1,2), Toast.LENGTH_SHORT).show();
        String result1 = result.substring(1,2) ;

        if(result1.equals("]")){
             Toast.makeText(parent.getApplicationContext(), "Login Unsuccessful ", Toast.LENGTH_SHORT).show();
            Test = 0 ;
            bar.setVisibility(View.GONE);
            txtPass.setText("");

        }else{
          //  Toast.makeText(parent.getApplicationContext(), "Login Successful ", Toast.LENGTH_SHORT).show();


        try{
            JSONArray jsonArr = new JSONArray(result);
            //Toast.makeText(parent.getApplicationContext(), "making object " + result, Toast.LENGTH_SHORT).show();

            String name = "";
            String code="";
            String id ="";


            //Subjects pass = null;
            //Subjects subjects = new Subjects("h", 0, parent, pass);


            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsObj = jsonArr.getJSONObject(i);
                Password1 = jsObj.getString("tutor_password");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Password1.toString().equals(Password.toString())) {

            if(Check == 0) {

                Toast.makeText(parent.getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                Test =1 ;

                Intent goHome = new Intent(parent, HomeActivity.class);
                goHome.putExtra("user", result);
                parent.startActivity(goHome);
            }
            if(Check ==1)
            {
                Intent goHome = new Intent(parent, HomeActivity.class);
                goHome.putExtra("user", result);
                parent.startActivity(goHome);
            }
        }
            else
        {
            Toast.makeText(parent.getApplicationContext(),"Login unsuccessful ", Toast.LENGTH_SHORT).show();
            Test = 0;
            bar.setVisibility(View.GONE);
            txtPass.setText("");
        }

        }

        signal.countDown();
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class).putExtra("user", out));
    }

    //Use this method to get stuff from the Login request claass by just making an object when needed and calling getStuff();
    public int getStuff()
    {
        int pass = Test;
        return Test;
    }


}

