package com.example.tutor;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

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

        }
    }

    List<tutor_Sessions> Sessions;
    Context context;

    CardViewAdapter(List<tutor_Sessions> sessions, Context context){
        this.Sessions = sessions;
        this.context = context;
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
        eventViewHolder.session.setImageResource(Sessions.get(i).icon);




      // eventViewHolder.sCheckin= connect2server.sendResults() ;

       // Toast.makeText(context, checkin  , Toast.LENGTH_SHORT).show();

        if(Sessions.get(i).tutor_checkin.length() ==0 )
        {
            eventViewHolder.Checkin.setText("Check in");



        }
        else
        {
            eventViewHolder.Checkin.setText("Check out");
        }






        eventViewHolder.Checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String sText = eventViewHolder.Checkin.getText().toString() ;

                if(sText.toString().equals("Check in"))
                {
                    eventViewHolder.Checkin.setText("Check out");
            }
                else
                {
                    eventViewHolder.Checkin.setText("Checked out");
                    eventViewHolder.Checkin.setClickable(false);
                }
              //  eventViewHolder.Checkin.getText().toString() ;

              // Toast.makeText(context, sText , Toast.LENGTH_SHORT).show();









              /*  String arr[] = new String[2] ;
                arr[0] = "nivekranjith95@gmail.com" ;
                arr[1] = "802119@students.wits.ac.za" ;

                for(int i = 0 ; i<2 ; i++) {

                    String fromEmail = "FindmetutorSD@gmail.com";
                    String fromPassword = "findmetutors";
                    String toEmails = arr[i];
                    String adminEmail = "admin@gmail.com";
                    String emailSubject = "Sent from FindMeTutor";
                    String adminSubject = "App Registration Mail";
                    String emailBody = "Test";
                    String adminBody = "Your message";
                    new SendMailTask(context).execute(fromEmail,
                            fromPassword, toEmails, emailSubject, emailBody);
                }*/



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



                    try {
                      //  Picasso.with(context).load("http://neural.net16.net/pictures/s" + Sessions.get(i).studentNumber + "JPG" ).into(eventViewHolder.session);

                        Picasso.with(context).load("http://neural.net16.net/pictures/s" + Sessions.get(i).studentNumber + "JPG" ).into(eventViewHolder.session);
                    }finally
                    {
                        eventViewHolder.session.setImageResource(Sessions.get(i).icon);
                    }


                         //   Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);


              //      Count++ ;
                }
                else {
                    eventViewHolder.subjectName.setText(Sessions.get(i).subjectName + "-" + Sessions.get(i).subjectCode);
                    eventViewHolder.amount.setText(Sessions.get(i).amount);
                    eventViewHolder.date.setText(Sessions.get(i).date);
                    eventViewHolder.time.setText(Sessions.get(i).time);
                    eventViewHolder.desc.setText(Sessions.get(i).description);
                    eventViewHolder.status.setText(Sessions.get(i).status);
                    eventViewHolder.session.setImageResource(Sessions.get(i).icon);
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

                                tutor_cancel connect2server = new tutor_cancel(context,Sessions.get(i).sessionID) ;
                                connect2server.execute();
                                eventViewHolder.Cancel.setText("Cancelled");


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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
