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
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class getTutorStudentSession extends AsyncTask<String, String, String> {
    Activity parent;
    String result = "";


    String desc;
    String studentCin;
    String studentCout;
    String tutorCin;
    String tutorCout;
    String studentTutorId;
    String paid;

    int[] cuurentDate = new int[3];

    List<StudentTutorSession> in;
    public AsyncResponse delegate = null; //Notify when async is done

    public getTutorStudentSession(Activity par, String student_id, List<StudentTutorSession> obj){
        this.parent = par;
        this.studentTutorId = student_id;
        this.in = obj;
    }

    public getTutorStudentSession(Activity par){
        parent = par;
    }

    @Override
    protected void onPreExecute() {

        Calendar c  = Calendar.getInstance();
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");

        String date = d.format(c.getTime());

        String [] split = date.split("-");

        for(int i=0; i<3;i++ ){
            cuurentDate[i] = Integer.parseInt(split[i]);
        }


        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        URL url = null;

        try {
            url = new URL("http://neural.net16.net/student_gettutorstudent.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new LinkedHashMap<>();
        parameter.put("TutorStudentID", studentTutorId);

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


                   desc = jsObj.getString("description");
                   studentCin = jsObj.getString("student_checkin");
                   studentCout = jsObj.getString("student_checkout");
                   tutorCin = jsObj.getString("tutor_checkin");
                   tutorCout = jsObj.getString("tutor_checkout");
                   paid = jsObj.getString("paid");
                  // Toast.makeText(parent.getApplicationContext(), code, Toast.LENGTH_SHORT).show();

                   in.add(new StudentTutorSession(desc, studentCin, studentCout, tutorCin, tutorCout, paid));

               }

           } catch (JSONException e) {
               e.printStackTrace();
           }

           //Now we have their subjects


           delegate.processFinish3(result); //let the other clsses know when onPost is finished
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


    public boolean isExpired(String cd){
        boolean b = false;

       /* int[] dateSesh = new int[3];

        String[] sp = cd.split("-");

        for(int i=0;i<3;i++){
            dateSesh[i] = Integer.parseInt(sp[i]);
        }


        if(dateSesh[0]< cuurentDate[0]){
            b=false;
        }
        else
        if(dateSesh[0] == cuurentDate[0])
        {
            if(dateSesh[1] < cuurentDate[1])
            {
                b=false ;
            }
            else if(dateSesh[1] == cuurentDate[1])
                {
                    if(dateSesh[2] < cuurentDate[2])
                    {
                        b=false ;
                    }
                    else
                        b=true ;
                }
            else if(dateSesh[1] > cuurentDate[1])
                b=true ;
        }
        else
        b=true ;
*/



        return b;
    }
}

