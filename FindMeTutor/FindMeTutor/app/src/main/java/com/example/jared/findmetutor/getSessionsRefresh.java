package com.example.jared.findmetutor;

import android.app.Activity;
import android.content.Context;
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
public class getSessionsRefresh extends AsyncTask<String, String, String> {
    Activity parent;
    String result = "";

    String sessionID;
    String tutorID;
    String tutorNum;
    String tutorName;
    String studentID;
    String subjectID;
    String subjectName;
    String amount;
    String date;
    String time;
    String desc;
    String status;
    String available;
    String unavailable;
    String total;


    List<Session> in;
    public AsyncResponse delegate = null;

    public getSessionsRefresh(Activity par, String student_id, List<Session> obj){
        this.parent = par;
        this.studentID = student_id;
        this.in = obj;
    }

    public getSessionsRefresh(Activity par){
        parent = par;
    }
    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/student_getsessions.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("StudentID", studentID);

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
        String temp = result.substring(1,2);
       // Toast.makeText(parent.getApplicationContext(), "Res " + result, Toast.LENGTH_SHORT).show();

       if(temp.equals("]")){ // NULL result
            Toast.makeText(parent.getApplicationContext(), "No current subjects", Toast.LENGTH_SHORT).show();

        }else {
           //If they're in the DB then login to the Home page
           //Toast.makeText(parent.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
           //startActivity(parent);

           //this.result = result;

           try {

               JSONArray jsonArr = new JSONArray(result);


               String fname = "";
               String lname="";


               //Subjects pass = null;
               //Subjects subjects = new Subjects("h", 0, parent, pass);


               for (int i = 0; i < jsonArr.length(); i++) {
                   JSONObject jsObj = jsonArr.getJSONObject(i);

                   sessionID= jsObj.getString("tutor_student_id");
                   tutorID = jsObj.getString("tutor_id");
                   tutorNum = jsObj.getString("tutor_student_num");
                   fname = jsObj.getString("tutor_fname");
                   lname = jsObj.getString("tutor_lname");
                   tutorName = fname + " "+lname;
                  // Toast.makeText(parent.getApplicationContext(), "making object " + tutorName, Toast.LENGTH_LONG).show();
                   subjectName = jsObj.getString("subject_name");
                   subjectID = jsObj.getString("subject_id");
                   amount = jsObj.getString("amount");
                   date = jsObj.getString("date");
                   time = jsObj.getString("time");
                   desc = jsObj.getString("description");
                   status = jsObj.getString("status");
                   available = jsObj.getString("available");

                   unavailable = jsObj.getString("unavailable");
                   total = jsObj.getString("total");
                   // Toast.makeText(parent.getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                   String dte = formateDate(date);
                   in.add(new Session( sessionID, tutorID, tutorNum, tutorName, subjectName,subjectID,amount,dte,time, desc,status,available, unavailable, total, R.drawable.session, parent ));
               }

           } catch (JSONException e) {
               e.printStackTrace();
           }

           //Now we have their subjects
           delegate.processFinish(result);
       }
    }

    public String formateDate(String datefull){
        String[] split = datefull.split("-");
        String year = split[0];
        String monthNum = split[1];
        int mn = Integer.parseInt(monthNum);
        String month = getMonth(mn);
        String datee = split[2];

        String full = datee+" "+month+" "+year;

        return  full;


    }

    public String getMonth(int month){
        String calMonth="";

        switch (month){
            case 1:calMonth="January";
                break;

            case 2:calMonth="Feburary";
                break;

            case 3:calMonth="March";
                break;

            case 4:calMonth="April";
                break;

            case 5:calMonth="May";
                break;

            case 6:calMonth="June";
                break;

            case 7:calMonth="July";
                break;

            case 8:calMonth="August";
                break;

            case 9:calMonth="September";
                break;

            case 10:calMonth="October";
                break;

            case 11:calMonth="November";
                break;

            case 12:calMonth="December";
                break;
            default:calMonth=calMonth;
                break;

        }

        return  calMonth;


    }

    public String sendResults()
    {
        return result;
    }

    public List getList()
    {
        return in;
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}

