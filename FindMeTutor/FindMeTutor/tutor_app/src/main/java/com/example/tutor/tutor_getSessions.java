package com.example.tutor;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 10-Sep-16.
 */

public class tutor_getSessions extends AsyncTask<String, String, String> {



    Activity parent;
    String result = "";

    String sessionID;
    String tutorID;
    String studentID;
    String subjectID;
    String subjectName;
    String subjectCode ;
    String studentNumber ;
    String studentName ;
    String StudentSurname ;
    String Student_contact_num ;
    String Student_email ;
    String amount;
    String date;
    String time;
    String desc;
    String status;
    String tutor_checkin ;
    String student_rating ;
    String tutor_checkout ;
    String student_checkin ;
    String student_checkout ;
    String paid ;
    int[] currentDate = new int[3];

    List<tutor_Sessions> in;
    public tutor_AsyncResponse delegate = null; //Notify when async is done

    public tutor_getSessions(Activity par, String tutor_id, List<tutor_Sessions> obj){
        this.parent = par;
        this.tutorID = tutor_id;
        this.in = obj;
    }

    public tutor_getSessions(Activity par){
        parent = par;
    }

    @Override
    protected void onPreExecute() {
        Calendar c = Calendar.getInstance() ;
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd") ;
        String date1 = d.format(c.getTime());

        String[] split = date1.split("-") ;

        for(int i = 0; i<split.length;i++)
        {
            // Toast.makeText(context,date[i], Toast.LENGTH_SHORT).show();
            currentDate[i] =  Integer.parseInt( split[i]) ;
        }



    }

    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/tutor_sessions.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("tutor_id", tutorID);

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
                String result1 =  result.substring(result.indexOf("["), result.length()) ;

                JSONArray jsonArr = new JSONArray(result1);
                //Toast.makeText(parent.getApplicationContext(), "making object " + result, Toast.LENGTH_SHORT).show();

                String name = "";
                String code="";

                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsObj = jsonArr.getJSONObject(i);

                    sessionID= jsObj.getString("tutor_student_id");
                    tutorID = jsObj.getString("tutor_id");
                    studentID= jsObj.getString("student_id") ;
                    subjectName = jsObj.getString("subject_name");
                    subjectID = jsObj.getString("subject_id");
                    studentName = jsObj.getString("student_fname");
                    StudentSurname = jsObj.getString("student_lname");
                    studentNumber = jsObj.getString("student_student_number");
                    Student_contact_num = jsObj.getString("student_contact_num");
                    Student_email = jsObj.getString("student_email");
                    subjectCode = jsObj.getString("subject_course_code") ;
                    amount = jsObj.getString("amount");
                    date = jsObj.getString("date");
                    time = jsObj.getString("time");
                    desc = jsObj.getString("description");
                    status = jsObj.getString("status");
                    tutor_checkin = jsObj.getString("tutor_checkin");
                    tutor_checkout = jsObj.getString("tutor_checkout");
                    student_checkin = jsObj.getString("student_checkin");
                    student_checkout = jsObj.getString("student_checkout");
                    student_rating = jsObj.getString("student_rating");
                    paid =  jsObj.getString("paid");

                   if( isExpired(date) == false)
                   {
                      // tutor_checkExpired connect2server = new tutor_checkExpired(parent,sessionID) ;
                       //connect2server.execute() ;
                   }
                    else {
                       //  Toast.makeText(parent.getApplicationContext(),"session " + tutor_checkin, Toast.LENGTH_SHORT).show();

                       // Toast.makeText(parent.getApplicationContext(),"paid " + paid, Toast.LENGTH_SHORT).show();
                   }
                    in.add(new tutor_Sessions(sessionID, tutorID, studentID, subjectID, subjectName, subjectCode, amount, date, time, studentName, StudentSurname, desc, studentNumber, Student_contact_num, Student_email, status, tutor_checkin, Float.parseFloat(student_rating), tutor_checkout, student_checkin, student_checkout, paid, R.drawable.session, parent));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

          //  Toast.makeText(parent.getApplicationContext(),"test" + in.get(0).description, Toast.LENGTH_SHORT).show();

            //Now we have their subjects


           // Toast.makeText(parent.getApplicationContext(), "Sessions " + result, Toast.LENGTH_SHORT).show();
           delegate.processFinish(result); //let the other clsses know when onPost is finished
        }
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

    public boolean isExpired(String sessionDate)
    {

        boolean b = false;


        int[] dateSesh = new int[3] ;
        String[] splitSesh = sessionDate.split("-");

        for(int i = 0; i<splitSesh.length;i++)
        {
            dateSesh[i] =  Integer.parseInt( splitSesh[i]) ;
        }



        if(dateSesh[0]<currentDate[0])
        {
           b=false ;
        }
        else
        if(dateSesh[0]== currentDate[0])
        {
            if (dateSesh[1] < currentDate[1])
            //if(dateSesh[2] < date[2])
            {
                b = false;
                // Toast.makeText(parent.getApplicationContext(),dateSesh[1] + " Expired", Toast.LENGTH_SHORT).show();
            }
            else
             if(dateSesh[1] == currentDate[1])
             {
                 if(dateSesh[2] < currentDate[2])
                 {
                     b=false ;
                 }
                 else
                     b=true ;
             }
        }
        else
        if(dateSesh[0] > currentDate[0])
        {
            b=true ;
        }



        return b ;






    }


}

