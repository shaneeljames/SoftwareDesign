package com.example.tutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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
import java.util.List;
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
public class tutor_getsubject extends AsyncTask<String, String, String> {
    Activity parent;
    String result = "";
    String TutorID;
    List<JSONObject> in;

    //List<JSONObject> in;

    static String out ;

    public tutor_getsubject(HomeActivity par, String student_id){
        parent = par;
        TutorID = student_id;
     //   in = obj ;
    }


    public tutor_getsubject(HomeActivity par){
        parent = par;
    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/tutor_notifications.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("tutor_id", TutorID);

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
        if (result.equals("null")) {
            Toast.makeText(parent.getApplicationContext(), "No current subjects", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(parent.getApplicationContext(), "making object: " + result, Toast.LENGTH_LONG).show();

           /* try {

                JSONArray jsonArr = new JSONArray(result);
                //Toast.makeText(parent.getApplicationContext(), "making object " + result, Toast.LENGTH_SHORT).show();

                String name = "";
                String code = "";


                //Subjects pass = null;
                //Subjects subjects = new Subjects("h", 0, parent, pass);


                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsObj = jsonArr.getJSONObject(i);
                    name = jsObj.getString("subject_name");
                    code = jsObj.getString("subject_course_code");
                    // Toast.makeText(parent.getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                    in.add(new JSONObject((Map) jsObj));
                }

              Toast.makeText(parent.getApplicationContext(), "making object " + in.get(0).getString("subject_name"), Toast.LENGTH_SHORT).show();


                //If they're in the DB then login to the
                // Home page


            } catch (JSONException e) {
                e.printStackTrace();
            }*/

        }
    }

   public static void startActivity(Context context) {
        context.startActivity(new Intent(context, NotificationsFragment.class).putExtra("subject", out));
    }


    public String getStuff()
    {
        String pass = result;
        return pass;
    }
}

