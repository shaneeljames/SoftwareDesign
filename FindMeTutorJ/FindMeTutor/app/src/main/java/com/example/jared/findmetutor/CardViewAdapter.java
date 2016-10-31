package com.example.jared.findmetutor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.EventViewHolder>  {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        String sessionID;
        String tutorID;
        String studentID;
        String subjectID;


        TextView subjectName;
        TextView amount;
        TextView date;
        TextView time;
        TextView desc;
        TextView status;

        TextView sess;
        TextView avail;

        TextView tutorName;

        ImageView session;

        Button cancel;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);



            subjectName = (TextView)itemView.findViewById(R.id.subject);
            amount = (TextView)itemView.findViewById(R.id.amount);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            desc = (TextView)itemView.findViewById(R.id.descTxt);
            status = (TextView)itemView.findViewById(R.id.status);


            session = (ImageView)itemView.findViewById(R.id.session);
            tutorName = (TextView)itemView.findViewById(R.id.tutorName);
            sess = (TextView)itemView.findViewById(R.id.sessID);
            avail = (TextView)itemView.findViewById(R.id.available);
            cancel = (Button)itemView.findViewById(R.id.cancel_session);
        }
    }
    String sessID;
    List<Session> events;
    Context context;
    HomeFragment base;



    CardViewAdapter(List<Session> events, Context context, HomeFragment home){
        this.events = events;
        this.context = context;
        this.base = home;

        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder eventViewHolder, final int i) {

        SharedPreferences myprefs =  context.getSharedPreferences("user", MODE_PRIVATE);
        final String stdid = myprefs.getString("student_id", null);
        String fname = myprefs.getString("student_fname",null);
        String lname = myprefs.getString("student_lname",null);
        String email = myprefs.getString("student_email", null);


            eventViewHolder.sess.setText(events.get(i).sessionID);
            sessID = eventViewHolder.sess.getText().toString();
            eventViewHolder.subjectName.setText(events.get(i).subjectName);
           // eventViewHolder.tutorName.setText(events.get(i).tutorName);
            eventViewHolder.amount.setText(events.get(i).amount);
            eventViewHolder.date.setText(events.get(i).date);
            eventViewHolder.time.setText(events.get(i).time);
            eventViewHolder.desc.setText(events.get(i).desc);
            eventViewHolder.status.setText(events.get(i).status);//condition this
            eventViewHolder.avail.setText(events.get(i).available);
            eventViewHolder.session.setImageResource(events.get(i).photoId);



            if(eventViewHolder.status.getText().toString().equals("Confirmed") && eventViewHolder.avail.getText().toString().equals(("-1"))){
                //Toast.makeText(context, "Confirmed "+events.get(i).tutorName, Toast.LENGTH_SHORT).show();
                eventViewHolder.cv.setCardBackgroundColor(Color.parseColor("#C1FFC1"));
                eventViewHolder.cancel.setVisibility(View.INVISIBLE);
                eventViewHolder.tutorName.setVisibility(View.VISIBLE);
                eventViewHolder.tutorName.setText("With "+ events.get(i).tutorName);

                eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "Session id: "+events.get(i).sessionID, Toast.LENGTH_SHORT).show();
                        //isExpired("10");


                        base.switchContentSession(events.get(i).tutorStdNum, events.get(i).sessionID, events.get(i).tutorID, events.get(i).subjectName);

                    }
                });


            }

            else if(Integer.parseInt(eventViewHolder.avail.getText().toString())>0)
            {
                eventViewHolder.status.setText("Tutors Available"+" ("+eventViewHolder.avail.getText().toString()+")");
                eventViewHolder.cv.setCardBackgroundColor(Color.parseColor("#B0E0E6"));
                eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "Session id: "+events.get(i).sessionID, Toast.LENGTH_SHORT).show();



                        base.switchContent(events.get(i).sessionID);

                    }
                });

                eventViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar = Snackbar
                                .make(eventViewHolder.cv, "Are you sure you want to cancel ", Snackbar.LENGTH_LONG)
                                .setAction("Yes!", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        student_cancel connect2server = new student_cancel(context,events.get(i).sessionID, stdid) ;
                                        connect2server.execute();
                                        eventViewHolder.status.setText("Cancelled");



                                    }
                                });
                        snackbar.setActionTextColor(Color.RED);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
                    }
                });
            }

            else if(eventViewHolder.status.getText().toString().equals("pending"))
            {
                eventViewHolder.status.setText("Pending - With "+events.get(i).total+ " tutors in pool");
                eventViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar = Snackbar
                                .make(eventViewHolder.cv, "Are you sure you want to cancel ", Snackbar.LENGTH_LONG)
                                .setAction("Yes!", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        student_cancel connect2server = new student_cancel(context,events.get(i).sessionID, stdid) ;
                                        connect2server.execute();
                                        eventViewHolder.status.setText("Cancelled");



                                    }
                                });
                        snackbar.setActionTextColor(Color.WHITE);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.RED);
                        snackbar.show();
                    }
                });

                if(events.get(i).total.equals("0")){
                    student_cancel connect2server = new student_cancel(context,events.get(i).sessionID, stdid) ;
                    connect2server.execute();
                    eventViewHolder.status.setText("No Tutors Available");

                    String fromEmail = "FindmetutorSD@gmail.com";
                    String fromPassword = "findmetutors";
                    String toEmails = email; //Sessions.get(i).studentEmail.toString() ;
                    String adminEmail = "admin@gmail.com";
                    String emailSubject = "Find Me Tutor - Session Unavailable";
                    String adminSubject = "App Registration Mail";
                    String emailBody =
                            "Dear " + fname +" "+lname +"<br>"
                                    +"We apologize for this but your request for "+events.get(i).subjectName+"<br>"
                                    +"Currently has no avaiable tutor's... We are constantly working on fixing this issue"+"<br>"
                                    +"Your account has been refunded."+"<br>"
                                    +"Please feel free to make another request ... "
                                    + "<br><br>"
                                    +"From FindMeTutor";

                    String adminBody = "Your message";
                    new SendMailTask(context).execute(fromEmail,
                            fromPassword, toEmails, emailSubject, emailBody);

                }

               else if(events.get(i).total.equals(events.get(i).unavailable)){

                    student_cancel connect2server = new student_cancel(context,events.get(i).sessionID, stdid) ;
                    connect2server.execute();
                    eventViewHolder.status.setText("Session Unavailable");




                    String fromEmail = "FindmetutorSD@gmail.com";
                    String fromPassword = "findmetutors";
                    String toEmails = email; //Sessions.get(i).studentEmail.toString() ;
                    String adminEmail = "admin@gmail.com";
                    String emailSubject = "Find Me Tutor - Session Unavailable";
                    String adminSubject = "App Registration Mail";
                    String emailBody =
                            "Dear " + fname +" "+lname +"<br>"
                                    +"We regret to inform you that all the respective tutors have declined your request for "+events.get(i).subjectName+"<br>"
                                    +"Your account has been refunded."+"<br>"
                                    +"Please feel free to make another request ... "
                                    + "<br><br>"
                                    +"From FindMeTutor";

                    String adminBody = "Your message";
                    new SendMailTask(context).execute(fromEmail,
                            fromPassword, toEmails, emailSubject, emailBody);

                }
            }



        //Check if the session is expired
        //ie. passed the current date+1

        try {
            if(isExpired(eventViewHolder.date.getText().toString())){

                //Toast.makeText(context,eventViewHolder.date.getText().toString() + " Expired",Toast.LENGTH_LONG).show();
                eventViewHolder.status.setText("Session Expired (Past date)");

                student_cancel connect2server = new student_cancel(context,events.get(i).sessionID, stdid) ;
                connect2server.execute();




                String fromEmail = "FindmetutorSD@gmail.com";
                String fromPassword = "findmetutors";
                String toEmails = email; //Sessions.get(i).studentEmail.toString() ;
                String adminEmail = "admin@gmail.com";
                String emailSubject = "Find Me Tutor - Session Expired";
                String adminSubject = "App Registration Mail";
                String emailBody =
                        "Dear " + fname +" "+lname +"<br>"
                                +"Unfortunatly, your session for "+events.get(i).subjectName+" On the "+ events.get(i).date+" has epired."+"<br>"
                                +"Your account has been refunded."+"<br>"
                                +"Please feel free to make another request ... "
                                + "<br><br>"
                                +"From FindMeTutor";

                String adminBody = "Your message";
                new SendMailTask(context).execute(fromEmail,
                        fromPassword, toEmails, emailSubject, emailBody);



            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        eventViewHolder.itemView.setTag(events.get(i));

        }

    public boolean isExpired(String cdate) throws ParseException {

        boolean re = false;


       // Toast.makeText(context, cdate+" after (out) ", Toast.LENGTH_LONG).show();
        String[] split = cdate.split(" ");
        String valid = split[2] +"-"+getMonth(split[1]) +"-"+split[0];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date strDate = sdf.parse(valid);

        Date today = new Date();

        Date todayWithZeroTime = removeTime(today);

       // Toast.makeText(context, todayWithZeroTime + " after  "+strDate, Toast.LENGTH_LONG).show();


        if (todayWithZeroTime.after(strDate)) {

            if (todayWithZeroTime.equals(strDate)) {
                //Toast.makeText(context, new Date()+" after  "+strDate, Toast.LENGTH_LONG).show();
                re = false;
            }
            else{

                re = true;
            }

        }

        // formattedDate have current date/time
       // Toast.makeText(context, "Year :"+year+" Month:"+month+ " Date: "+date +">>>" + cYear+" "+getMonth((cMoth))+" "+cDate, Toast.LENGTH_LONG).show();


        return  re;

    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public int getMonth(String month){
        int calMonth=0;


        if(month.equals("January"))
            calMonth =1;
        if(month.equals("Feburary"))
            calMonth =2;
        if(month.equals("March"))
            calMonth =3;
        if(month.equals("April"))
            calMonth =4;
        if(month.equals("May"))
            calMonth =5;
        if(month.equals("June"))
            calMonth =6;
        if(month.equals("July"))
            calMonth =7;
        if(month.equals("August"))
            calMonth =8;
        if(month.equals("September"))
            calMonth =9;
        if(month.equals("October"))
            calMonth =10;
        if(month.equals("November"))
            calMonth =11;
        if(month.equals("December"))
            calMonth =12;

        return  calMonth;


    }


    // Custom method for converting DP/DIP value to pixels
    protected int getPixelsFromDPs(int dps){
        Resources r = context.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }

    public void sendEmails(List<TutorsEList> list){

        for(int i=0; i<list.size();i++) {
            String fromEmail = "FindmetutorSD3@gmail.com";
            String fromPassword = "findmetutors";
            String toEmails = list.get(i).tutorEmail.toString(); //Sessions.get(i).studentEmail.toString() ;
            String adminEmail = "admin@gmail.com";
            String emailSubject = "Find Me Tutor - Session Request";
            String adminSubject = "App Registration Mail";
            String emailBody =
                    "Dear " + list.get(i).TutorName
                            + "<br><br>";

            String adminBody = "Your message";
            new SendMailTask(context).execute(fromEmail,
                    fromPassword, toEmails, emailSubject, emailBody);
        }
    }








    //Toast.makeText(context, "Session id: "+eventViewHolder.sess.getText().toString()+ " Available : "+eventViewHolder.avail.getText().toString(), Toast.LENGTH_LONG).show();





    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Clean all elements of the recycler

    public void clear() {

        events.clear();

        notifyDataSetChanged();

    }



// Add a list of items

    public void addAll(List<Session> list) {

        events.addAll(list);

        notifyDataSetChanged();

    }

}
