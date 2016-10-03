package com.example.tutor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

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

/**
 * Created by admin on 02-Oct-16.
 */

public class tutor_checkRegister extends AsyncTask<String, String, String> {
    Activity parent;
    String tutor_email= "" ;
    String result = "";
    String tutorEmail;
   String FirstName = "" ;
    String Surname = "" ;
    String Contact = "" ;
    String StudentNumber = "" ;
    String Password = "" ;
    public tutor_AsyncResponse delegate = null; //Notify when async is done

    public tutor_checkRegister(Activity par, String tutor_email, String first_name, String last_name, String contact_number, String studentNumber,  String password){
        parent = par;
        tutorEmail = tutor_email;
        FirstName = first_name ;
        Surname = last_name ;
        Contact = contact_number ;
        StudentNumber = studentNumber ;
        Password = password ;

    }

    public tutor_checkRegister(Activity par){
        parent = par;
    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/tutor_checkRegister.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("tutor_email", tutorEmail);

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

      //  Toast.makeText(parent.getApplicationContext(), "check Register "+ result, Toast.LENGTH_SHORT).show();

        try {

            JSONArray jsonArr = new JSONArray(result);
            JSONObject jsObj = jsonArr.getJSONObject(0);
            tutor_email = jsObj.getString("COUNT(tutor_email)");

            if(tutor_email.equals("0"))
            {

             //   Toast.makeText(parent.getApplicationContext(),"good: " + tutor_email.toString(), Toast.LENGTH_SHORT).show();
                register connect2server = new register(parent, FirstName, Surname, Contact, StudentNumber, tutorEmail, Password);
                connect2server.execute();


                String fromEmail = "FindmetutorSD@gmail.com";
                String fromPassword = "findmetutors";
                String toEmails = tutorEmail.toString();
                String adminEmail = "admin@gmail.com";
                String emailSubject = "Sent from FindMeTutor";
                String adminSubject = "App Registration Mail";
                String emailBody =
                        "Dear "+ FirstName + " " +Surname
                                +"<br><br> You have successfully registered on FindMeTutor as a tutor!<br> " +
                                "Simply use your Email: " + tutorEmail.toString() + " and your Password: " + Password.toString()
                                +" to log in." ;

                String adminBody = "Your message";
                new SendMailTask(parent.getApplicationContext()).execute(fromEmail,
                        fromPassword, toEmails, emailSubject, emailBody);


                parent.finish();
                Intent goHome = new Intent(parent, LoginActivity.class);
                parent.startActivity(goHome);
            }
            else {
               // Toast.makeText(parent.getApplicationContext(), "Registration Unsuccessful! Account already exists", Toast.LENGTH_SHORT).show();

                parent.finish(); ;
                Intent goHome = new Intent(parent, RegisterActivity.class);
                parent.startActivity(goHome);

            }


            // Toast.makeText(parent.getApplicationContext(),"Check register 4 " + id, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

      //  delegate.processFinish(tutor_email);
    }

    public String sendResults()
    {
        return tutor_email;
    }


}


