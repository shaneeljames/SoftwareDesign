package com.example.jared.findmetutor;

import android.app.Activity;
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
import java.util.Map;

/**
 * Created by jared on 2016/08/04.
 */
public class studentCheckReg extends AsyncTask<String, String, String> {
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
    String student_email;

    String result = "";

    public studentCheckReg(Activity par, String first_name, String last_name, String contact_number, String studentNumber, String student_email, String password){
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
            url = new URL("http://neural.net16.net/student_checkreg.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("student_email", email);

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
        try {

            JSONArray jsonArr = new JSONArray(result);
            JSONObject jsObj = jsonArr.getJSONObject(0);
            student_email = jsObj.getString("COUNT(student_email)");

            if(student_email.equals("0"))
            {

                //   Toast.makeText(parent.getApplicationContext(),"good: " + tutor_email.toString(), Toast.LENGTH_SHORT).show();
                register connect2server = new register(parent, Firstname, Lastname, Contact_Number, StudentNumber, email, Password);
                connect2server.execute();


                String fromEmail = "FindmetutorSD@gmail.com";
                String fromPassword = "findmetutors";
                String toEmails = email.toString();
                String adminEmail = "admin@gmail.com";
                String emailSubject = "Sent from FindMeTutor";
                String adminSubject = "App Registration Mail";
                String emailBody =
                        "Dear "+ Firstname + " " +Lastname
                                +"<br><br> You have successfully registered on FindMeTutor as a Student!<br> " +
                                "Simply use your Email: " + email.toString() + " and your Password: " + Password.toString()
                                +" to log in." ;

                String adminBody = "Your message";
                new SendMailTask(parent.getApplicationContext()).execute(fromEmail,
                        fromPassword, toEmails, emailSubject, emailBody);


                parent.finish();
                Intent goHome = new Intent(parent, LoginActivity.class);
                parent.startActivity(goHome);
            }
            else {
                 Toast.makeText(parent.getApplicationContext(), "Registration Unsuccessful! Account already exists", Toast.LENGTH_SHORT).show();

                parent.finish(); ;
                Intent goHome = new Intent(parent, RegisterActivity.class);
                parent.startActivity(goHome);

            }


            // Toast.makeText(parent.getApplicationContext(),"Check register 4 " + id, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

