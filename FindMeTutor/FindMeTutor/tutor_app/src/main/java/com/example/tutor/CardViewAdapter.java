package com.example.tutor;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        TextView subjectName;
        TextView amount;
        TextView date;
        TextView time;
        TextView desc;
        TextView status;
        TextView studentName;
        TextView viewDetails ;
        LinearLayout llStudent ;
        RelativeLayout rlSession ;
        Button Checkin ;
        TextView Cancel ;
        RatingBar rate ;
        Button btnRate ;
        SharedPreferences myprefs ;

        int count = 0 ;

        ImageView session;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);

            subjectName = (TextView)itemView.findViewById(R.id.subject);
            amount = (TextView)itemView.findViewById(R.id.amount);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            desc = (TextView)itemView.findViewById(R.id.descTxt);
            status = (TextView)itemView.findViewById(R.id.status);
            studentName = (TextView) itemView.findViewById(R.id.studentName)  ;
            viewDetails = (TextView) itemView.findViewById(R.id.txtStudentDetails) ;
            llStudent = (LinearLayout) itemView.findViewById(R.id.llStudent) ;
          //  rlSession = (RelativeLayout) itemView.findViewById(R.id.llSession) ;
            Checkin = (Button) itemView.findViewById(R.id.btnCheckin) ;
            session = (ImageView)itemView.findViewById(R.id.session);
            Cancel = (TextView)itemView.findViewById(R.id.txtCancel);
            rate = (RatingBar) itemView.findViewById(R.id.ratingBar) ;
            btnRate = (Button) itemView.findViewById(R.id.btnRate);

        }
    }

    List<tutor_Sessions> Sessions;
    Context context;
    Activity H ;
    private LocationManager locationManager;
    private LocationListener locationListener;

    SharedPreferences myprefs;
    String tutorid;
    String tutor_name ;
    String tutor_surname ;

    CardViewAdapter(List<tutor_Sessions> sessions, Context context, Activity h){
        this.Sessions = sessions;
        this.context = context;
        this.H = h ;
    }

    @Override
    public int getItemCount() {
        return Sessions.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder eventViewHolder, final int i) {

        // eventViewHolder.studentName.setText(Sessions.get(i).studentName + " " +Sessions.get(i).studentSurname);

        eventViewHolder.subjectName.setText(Sessions.get(i).subjectName + "-" + Sessions.get(i).subjectCode);
        eventViewHolder.amount.setText(Sessions.get(i).amount);
        eventViewHolder.date.setText(Sessions.get(i).date);
        eventViewHolder.time.setText(Sessions.get(i).time);
        eventViewHolder.desc.setText(Sessions.get(i).description);
        eventViewHolder.status.setText(Sessions.get(i).status);
       // eventViewHolder.session.setImageResource(Sessions.get(i).icon);
        eventViewHolder.rate.setRating(Sessions.get(i).rating);
          myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
         tutorid = myprefs.getString("tutor_id", null) ;
         tutor_name = myprefs.getString("tutor_fname" , null) ;
         tutor_surname= myprefs.getString("tutor_lname",null) ;



        Random r = new Random();
        int i1 = r.nextInt(999999 - 111111) + 111111;

        String ran =    Integer.toString(i1) ;



        try {
            Picasso.with(context).load("http://neural.net16.net/pictures/s"+ Sessions.get(i).studentNumber +"JPG?"+ ran).into(eventViewHolder.session);
        }catch (Exception e)
        {

        }




      //  Toast.makeText(context, "tutor checkin : "+ Sessions.get(i).tutor_checkin, Toast.LENGTH_SHORT).show();
       // Toast.makeText(context, "tutor checkout : "+ Sessions.get(i).tutor_checkout, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, "tutor rating : "+ Sessions.get(i).rating, Toast.LENGTH_SHORT).show();

        if(Sessions.get(i).tutor_checkin.length() ==0 )
        {
            eventViewHolder.Checkin.setText("Check in");
          //  Toast.makeText(context, "tutor checkin length=0 : " ,Toast.LENGTH_SHORT).show();

        }
        else if(Sessions.get(i).tutor_checkout.length() == 0)
        {
            eventViewHolder.Checkin.setText("Check out");
            //Toast.makeText(context, "tutor checkout length=0 : ", Toast.LENGTH_SHORT).show();
        }
        else if(Sessions.get(i).rating == 0 && Sessions.get(i).tutor_checkout.length() != 0)
        {
          //  Toast.makeText(context, "tutor rating =0 : " ,Toast.LENGTH_SHORT).show();
            new GetLocation2(H, Sessions.get(i).sessionID,1);
            eventViewHolder.Checkin.setText("Checked out");
            eventViewHolder.Checkin.setClickable(false);
            eventViewHolder.subjectName.setText("Rate " + Sessions.get(i).studentName + " " + Sessions.get(i).studentSurname);
            eventViewHolder.viewDetails.setVisibility(View.GONE);
            eventViewHolder.btnRate.setVisibility(View.VISIBLE);
            eventViewHolder.amount.setVisibility(View.GONE);
            eventViewHolder.date.setVisibility(View.GONE);
            eventViewHolder.time.setVisibility(View.GONE);
            eventViewHolder.desc.setVisibility(View.GONE);
            eventViewHolder.status.setVisibility(View.GONE);
            eventViewHolder.rate.setVisibility(View.VISIBLE);
            eventViewHolder.Cancel.setVisibility(View.INVISIBLE);
            eventViewHolder.rate.setRating(3);
            eventViewHolder.rate.setIsIndicator(false) ;
        }
        else if (Sessions.get(i).student_checkin.length() == 0 || Sessions.get(i).student_checkout.length() ==0 )
        {
            eventViewHolder.subjectName.setText("Waiting for student to check out");
            eventViewHolder.subjectName.setPadding(0,10,0,0);
           // Toast.makeText(context, "tutor rating =0 : " ,Toast.LENGTH_SHORT).show();
          //  new GetLocation2(H, Sessions.get(i).sessionID,1);
            eventViewHolder.Checkin.setText("Checked out!");
            eventViewHolder.Checkin.setClickable(false);
            eventViewHolder.viewDetails.setVisibility(View.GONE);
            eventViewHolder.btnRate.setVisibility(View.GONE);
            eventViewHolder.amount.setVisibility(View.GONE);
            eventViewHolder.date.setVisibility(View.GONE);
            eventViewHolder.time.setVisibility(View.GONE);
            eventViewHolder.desc.setVisibility(View.GONE);
            eventViewHolder.status.setVisibility(View.GONE);
            eventViewHolder.rate.setVisibility(View.VISIBLE);
            eventViewHolder.Cancel.setVisibility(View.INVISIBLE);
           eventViewHolder.rate.setVisibility(View.GONE);
        }
        else if ( Sessions.get(i).Paid.equals("0"))
        {

            SharedPreferences myprefs;
            myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
            String id = myprefs.getString("tutor_id", null) ;

            tutor_paid connect2sever = new tutor_paid(H, Sessions.get(i).sessionID,id, Sessions.get(i).studentID) ;
            connect2sever.execute() ;
        }



        eventViewHolder.Checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String sText = eventViewHolder.Checkin.getText().toString() ;



                if(sText.toString().equals("Check in"))
                {
                    eventViewHolder.Checkin.setText("Check out");
                    new GetLocation2(H, Sessions.get(i).sessionID,0);



                }
                else if(sText.toString().equals("Check out"))
                {

                    new GetLocation2(H, Sessions.get(i).sessionID,1);
                    eventViewHolder.Checkin.setText("Checked out");
                    if(Sessions.get(i).student_checkin.length() != 0 || Sessions.get(i).student_checkout.length() != 0)
                    {
                    eventViewHolder.Checkin.setClickable(false);
                    eventViewHolder.subjectName.setText("Rate " + Sessions.get(i).studentName + " " + Sessions.get(i).studentSurname);
                    eventViewHolder.viewDetails.setVisibility(View.GONE);
                    eventViewHolder.btnRate.setVisibility(View.VISIBLE);
                    eventViewHolder.amount.setVisibility(View.GONE);
                    eventViewHolder.date.setVisibility(View.GONE);
                    eventViewHolder.time.setVisibility(View.GONE);
                    eventViewHolder.desc.setVisibility(View.GONE);
                    eventViewHolder.status.setVisibility(View.GONE);
                    eventViewHolder.rate.setVisibility(View.VISIBLE);
                    eventViewHolder.Cancel.setVisibility(View.INVISIBLE);
                    eventViewHolder.rate.setRating(3);
                    eventViewHolder.rate.setIsIndicator(false) ;


                        SharedPreferences myprefs;
                        myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                        String id = myprefs.getString("tutor_id", null) ;

                        tutor_paid connect2sever = new tutor_paid(H, Sessions.get(i).sessionID,id, Sessions.get(i).studentID) ;
                        connect2sever.execute() ;
                    }
                    else
                    {

                        eventViewHolder.subjectName.setText("Waiting for student to check out");
                        eventViewHolder.subjectName.setPadding(0,10,0,0);
                        // Toast.makeText(context, "tutor rating =0 : " ,Toast.LENGTH_SHORT).show();
                        //  new GetLocation2(H, Sessions.get(i).sessionID,1);
                        eventViewHolder.Checkin.setText("Checked out!");
                        eventViewHolder.Checkin.setClickable(false);
                        eventViewHolder.viewDetails.setVisibility(View.GONE);
                        eventViewHolder.btnRate.setVisibility(View.GONE);
                        eventViewHolder.amount.setVisibility(View.GONE);
                        eventViewHolder.date.setVisibility(View.GONE);
                        eventViewHolder.time.setVisibility(View.GONE);
                        eventViewHolder.desc.setVisibility(View.GONE);
                        eventViewHolder.status.setVisibility(View.GONE);
                        eventViewHolder.rate.setVisibility(View.VISIBLE);
                        eventViewHolder.Cancel.setVisibility(View.INVISIBLE);
                        eventViewHolder.rate.setVisibility(View.GONE);
                    }


                }
            }



        });


        eventViewHolder.viewDetails.setOnClickListener(new View.OnClickListener()
        {

            @Override
                    public void onClick(View v){

                if (eventViewHolder.viewDetails.getText().toString().equals("View Students Details")) {
                    eventViewHolder.subjectName.setText(Sessions.get(i).studentName + " " + Sessions.get(i).studentSurname);
                    eventViewHolder.amount.setText("Contact Number: " + Sessions.get(i).studentContact);
                    eventViewHolder.date.setText("Student Number: " + Sessions.get(i).studentNumber);
                    eventViewHolder.time.setText("Student Email: " + Sessions.get(i).studentEmail);
                    eventViewHolder.viewDetails.setText("View Session Details");
                    eventViewHolder.desc.setText("");
                    eventViewHolder.status.setText("");
                    eventViewHolder.rate.setVisibility(View.VISIBLE);


                }
                else {
                    eventViewHolder.subjectName.setText(Sessions.get(i).subjectName + "-" + Sessions.get(i).subjectCode);
                    eventViewHolder.amount.setText(Sessions.get(i).amount);
                    eventViewHolder.date.setText(Sessions.get(i).date);
                    eventViewHolder.time.setText(Sessions.get(i).time);
                    eventViewHolder.desc.setVisibility(View.VISIBLE);
                    eventViewHolder.status.setVisibility(View.VISIBLE);
                    eventViewHolder.rate.setVisibility(View.GONE);
                    eventViewHolder.desc.setText(Sessions.get(i).description);
                    eventViewHolder.status.setText(Sessions.get(i).status);
                    eventViewHolder.viewDetails.setText("View Students Details");

                //    Picasso.with(context).load("http://neural.net16.net/pictures/s815050JPG" ).into(eventViewHolder.session);

                }

        }
        });

        eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Index position is: "+i, Toast.LENGTH_SHORT).show();
            }
        });


        eventViewHolder.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInputDialog(Sessions,i,eventViewHolder) ;





            }
        });


        eventViewHolder.btnRate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick (View v){



                myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                 tutorid = myprefs.getString("tutor_id", null) ;


                tutor_rateStudent connect2server = new tutor_rateStudent(context,Sessions.get(i).studentID,Sessions.get(i).sessionID, Float.toString(eventViewHolder.rate.getRating())) ;
                connect2server.execute() ;

                tutor_updateStudentRating connect = new tutor_updateStudentRating(context,  Sessions.get(i).studentID);
                connect.execute() ;

                Toast.makeText(context, "STudent id " + Sessions.get(i).studentID, Toast.LENGTH_SHORT).show();

                eventViewHolder.btnRate.setText("Rated");
                eventViewHolder.btnRate.setClickable(false);



            }
        });

    }

     @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public void showInputDialog(final List<tutor_Sessions> l, final int i, final CardViewAdapter.EventViewHolder e) {


        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(H);
        View promptView = layoutInflater.inflate(R.layout.inputdialogbox, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(H);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        final TextView textView = (TextView) promptView.findViewById(R.id.textView);
        textView.setText("Please give a reason for Cancelling");
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // resultText.setText("Hello, " + editText.getText());

                        String fromEmail = "FindmetutorSD@gmail.com";
                        String fromPassword = "findmetutors";
                        String toEmails =l.get(i).studentEmail;
                        String adminEmail = "admin@gmail.com";
                        String emailSubject = "Sent from FindMeTutor";
                        String adminSubject = "App Registration Mail";
                        String emailBody =
                                "Dear "+ l.get(i).studentName + " " + l.get(i).studentSurname
                                        +"<br><br>"+ tutor_name.toString() +" " + tutor_surname.toString()
                                        +" has Cancelled your session for:<br>Subject: "
                                        + l.get(i).subjectName + "<br>Date: "+ l.get(i).date  +"<br>Time: "
                                        + l.get(i).time + "<br>Description: " + l.get(i).description
                                        +".<br>Reason for Cancelling: " + editText.getText().toString()
                                        +"<br><br>Sent from FindMeTutor.";

                        String adminBody = "Your message";
                        new SendMailTask(context).execute(fromEmail,
                                fromPassword, toEmails, emailSubject, emailBody);


                        tutor_deleteAccount connect2server = new tutor_deleteAccount(H,tutorid) ;
                        connect2server.execute() ;

                        e.Cancel.setText("Cancelled");
                        e.Cancel.setClickable(false);

                        Toast.makeText(context,editText.getText(),Toast.LENGTH_SHORT).show() ;
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }


                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }





}
