package com.example.tutor;

import android.app.Activity;
import android.content.Context;
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

//////Actually to get Notifications of student requests

public class tutor_getnotification extends AsyncTask<String, String, String> {
    Activity parent;
    String result = "";
    String TutorID;
    List<Notifications> in;

    public tutor_AsyncResponse delegate = null; //Notify when async is done


    static String out ;

    public tutor_getnotification(Activity par, String student_id , List<Notifications> obj){
        parent = par;
        TutorID = student_id;
        in = obj ;
    }


    public tutor_getnotification(Activity par){
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

          //  Toast.makeText(parent.getApplicationContext(), "making object: " + result, Toast.LENGTH_LONG).show();

            String result1 = result.substring(result.indexOf('['),result.length()) ;
          //  Toast.makeText(parent.getApplicationContext(), " object: " + result1, Toast.LENGTH_LONG).show();

            try {

                JSONArray jsonArr = new JSONArray(result1);
                //Toast.makeText(parent.getApplicationContext(), "making object " + result, Toast.LENGTH_SHORT).show();
                String tutor_student_id= "" ;
                String student_id ;
                String subjectName = "";
                String subjectCode="";
                String date = "" ;
                String time = "";
                String studentName ;
                String studentSurname ;
                String description ;


                //Subjects pass = null;
                //Subjects subjects = new Subjects("h", 0, parent, pass);


                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsObj = jsonArr.getJSONObject(i);
                    tutor_student_id = jsObj.getString("tutor_student_id");
                    student_id = jsObj.getString("student_id") ;
                    subjectName = jsObj.getString("subject_name");
                    subjectCode = jsObj.getString("subject_course_code");
                    date = jsObj.getString("date");
                    time = jsObj.getString("time");
                    studentName = jsObj.getString("student_fname");
                    studentSurname = jsObj.getString("student_lname");
                    description = jsObj.getString("description");
                    Toast.makeText(parent.getApplicationContext(), student_id, Toast.LENGTH_SHORT).show();


                    in.add(new Notifications(tutor_student_id,student_id,subjectName,subjectCode,date,time,studentName,studentSurname,description,R.drawable.notify,parent));
                }

               // Toast.makeText(parent.getApplicationContext(), in.get(0).studentSurname, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            delegate.processFinish(result);

        }
    }

    public String sendResults(){return result;}
    public List getList(){return in;}

   public static void startActivity(Context context) {
        //context.startActivity(new Intent(context, NotificationsFragment.class).putExtra("subject", out));
    }


}

