package com.example.tutor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

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


        try {
            //  Picasso.with(context).load("http://neural.net16.net/pictures/s" + Sessions.get(i).studentNumber + "JPG" ).into(eventViewHolder.session);

            Picasso.with(context).load("http://neural.net16.net/pictures/s" + Sessions.get(i).studentNumber + "JPG" ).into(eventViewHolder.session);
            //   Picasso.with(context).load("http://neural.net16.net/pictures/s" + Sessions.get(i).studentNumber + "JPG" ).

        } catch (Exception e)
        {

        }


        Toast.makeText(context, "tutor checkin : "+ Sessions.get(i).tutor_checkin, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "tutor checkout : "+ Sessions.get(i).tutor_checkout, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "tutor rating : "+ Sessions.get(i).rating, Toast.LENGTH_SHORT).show();

        if(Sessions.get(i).tutor_checkin.length() ==0 )
        {
            eventViewHolder.Checkin.setText("Check in");
            Toast.makeText(context, "tutor checkin length=0 : " ,Toast.LENGTH_SHORT).show();

        }
        else if(Sessions.get(i).tutor_checkout.length() == 0)
        {
            eventViewHolder.Checkin.setText("Check out");
            Toast.makeText(context, "tutor checkout length=0 : ", Toast.LENGTH_SHORT).show();
        }
        else if(Sessions.get(i).rating == 0)
        {
            Toast.makeText(context, "tutor rating =0 : " ,Toast.LENGTH_SHORT).show();
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



        eventViewHolder.Checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String sText = eventViewHolder.Checkin.getText().toString() ;
                new GetLocation2(H, Sessions.get(i).sessionID,0);

                if(sText.toString().equals("Check in"))
                {
                    eventViewHolder.Checkin.setText("Check out");
                }
                else if(sText.toString().equals("Check out"))
                {
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
                Snackbar snackbar = Snackbar
                        .make(eventViewHolder.cv, "Are you sure you want to cancel with " + Sessions.get(i).studentName, Snackbar.LENGTH_LONG)
                        .setAction("Yes!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                SharedPreferences myprefs;
                                myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                                String id = myprefs.getString("tutor_id", null) ;
                                String tutor_name =  myprefs.getString("tutor_fname", null) ;
                                String tutor_surname =  myprefs.getString("tutor_lname", null) ;

                                tutor_cancel connect2server = new tutor_cancel(context,Sessions.get(i).sessionID) ;
                                connect2server.execute();
                                eventViewHolder.Cancel.setText("Cancelled");

                                String fromEmail = "FindmetutorSD@gmail.com";
                                String fromPassword = "findmetutors";
                                String toEmails =  Sessions.get(i).studentEmail.toString() ;
                                String adminEmail = "admin@gmail.com";
                                String emailSubject = "Sent from FindMeTutor";
                                String adminSubject = "App Registration Mail";
                                String emailBody =
                                        "Dear "+ Sessions.get(i).studentName + " " + Sessions.get(i).studentSurname
                                                +"<br><br>"+ tutor_name.toString() +" " + tutor_surname.toString()
                                                +" has cancelled your booking for:<br>Subject: "
                                                + Sessions.get(i).subjectName + "<br>Date: "+ Sessions.get(i).date  +"<br>Time: "
                                                + Sessions.get(i).time + "<br>Description: " + Sessions.get(i).description
                                                +".<br><br>Your request has been reopened."
                                                +"<br><br>We apologize for the inconvenience";
                                String adminBody = "Your message";
                                new SendMailTask(context).execute(fromEmail,
                                        fromPassword, toEmails, emailSubject, emailBody);


                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        });


        eventViewHolder.btnRate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick (View v){


                SharedPreferences myprefs;
                myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                String id = myprefs.getString("tutor_id", null) ;


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





}
