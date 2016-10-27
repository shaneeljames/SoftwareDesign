package com.example.tutor;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class CardViewNotificationsAdapter extends RecyclerView.Adapter<CardViewNotificationsAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView subName;
        TextView date;
        TextView time;
        ImageView session;
        TextView studentName ;
        TextView Description;
        Button confirm ;
        Button decline;
        RatingBar rate ;


        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            subName = (TextView)itemView.findViewById(R.id.subject);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            session = (ImageView)itemView.findViewById(R.id.session);
            studentName = (TextView)itemView.findViewById(R.id.studentName) ;
            Description = (TextView)itemView.findViewById(R.id.Description) ;
            confirm = (Button)itemView.findViewById(R.id.btnConfirm) ;
            decline = (Button)itemView.findViewById(R.id.btnDecline) ;
            rate = (RatingBar) itemView.findViewById(R.id.ratingBar3) ;
           // submit = (Button)itemView.findViewById(R.id.btnSubmitDecline) ;


        }
    }

    List<Notifications> list;
    Context context;
    Activity activity ;
    SharedPreferences myprefs;
    String tutor_id ;
    String tutor_name ;
    String tutor_surname ;

    CardViewNotificationsAdapter(List<Notifications> events, Context context, Activity a){
        this.list = events;
        this.context = context;
        this.activity = a;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_notifcations, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder eventViewHolder, final int i) {
        eventViewHolder.subName.setText(list.get(i).code + "-"+ list.get(i).subject);
        eventViewHolder.date.setText(list.get(i).date);
        eventViewHolder.time.setText(list.get(i).time);
        eventViewHolder.session.setImageResource(list.get(i).icon);
        eventViewHolder.studentName.setText(list.get(i).studentName + " "+ list.get(i).studentSurname);
        eventViewHolder.Description.setText(list.get(i).description);
        eventViewHolder.rate.setRating( Float.parseFloat(list.get(i).Rating.toString()));




        eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "Index position is: "+i, Toast.LENGTH_SHORT).show();
            }
        });

        eventViewHolder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences myprefs;
                myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                 tutor_id = myprefs.getString("tutor_id", null) ;
                 tutor_name = myprefs.getString("tutor_fname", null) ;;
                 tutor_surname = myprefs.getString("tutor_lname", null) ; ;

                  showInputDialog(list,i , eventViewHolder);






                /*tutor_decline connect2server = new tutor_decline(context,list.get(i).tutor_student_id,id, list.get(i).student_id) ;
                connect2server.execute() ;
                eventViewHolder.decline.setText("Declined");*/
                //  Toast.makeText(context, "Index position is: "+i, Toast.LENGTH_SHORT).show();
            }
        });

        eventViewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Confirmed: "+i, Toast.LENGTH_SHORT).show();


                myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                 tutor_id = myprefs.getString("tutor_id", null) ;
                 tutor_name =  myprefs.getString("tutor_fname", null) ;
                 tutor_surname =  myprefs.getString("tutor_lname", null) ;

               // Toast.makeText(context, "tut: "+list.get(i).tutor_student_id, Toast.LENGTH_SHORT).show();

                tutor_confirm connect2server = new tutor_confirm(context,list.get(i).tutor_student_id,tutor_id, list.get(i).student_id) ;
                connect2server.execute();

                Toast.makeText(context, "Your agreement has been sent to " + list.get(i).studentName +" " + list.get(i).studentSurname, Toast.LENGTH_SHORT).show();
                eventViewHolder.confirm.setText("confirmed");
                eventViewHolder.confirm.setClickable(false);

                Toast.makeText(context, "Student Email " + list.get(i).Email, Toast.LENGTH_SHORT).show();


                String fromEmail = "FindmetutorSD@gmail.com";
                String fromPassword = "findmetutors";
                String toEmails = list.get(i).Email.toString();
                String adminEmail = "admin@gmail.com";
                String emailSubject = "Sent from FindMeTutor";
                String adminSubject = "App Registration Mail";
                String emailBody =
                                "Dear "+ list.get(i).studentName + " " + list.get(i).studentSurname
                                +"<br><br>"+ tutor_name.toString() +" " + tutor_surname.toString()
                                +" has accepted your request for:<br>Subject: "
                                + list.get(i).subject + "<br>Date: "+ list.get(i).date  +"<br>Time: "
                                + list.get(i).time + "<br>Description: " + list.get(i).description
                                +".<br><br>For more information about " + tutor_name.toString() +" " + tutor_surname.toString()
                                +" please log on to your FindMeTutor app.";
                String adminBody = "Your message";
                new SendMailTask(context).execute(fromEmail,
                        fromPassword, toEmails, emailSubject, emailBody);





                //  tutor_getsubject2 connect2server2 = new tutor_getsubject2(context,id) ;


            }
        });
    }



    public void showInputDialog(final List<Notifications> l, final int i, final EventViewHolder e) {


        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View promptView = layoutInflater.inflate(R.layout.inputdialogbox, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        final TextView textView = (TextView) promptView.findViewById(R.id.textView);
        textView.setText("Please give a reason for decline");
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // resultText.setText("Hello, " + editText.getText());

                        String fromEmail = "FindmetutorSD@gmail.com";
                        String fromPassword = "findmetutors";
                        String toEmails =l.get(i).Email.toString();
                        String adminEmail = "admin@gmail.com";
                        String emailSubject = "Sent from FindMeTutor";
                        String adminSubject = "App Registration Mail";
                        String emailBody =
                                "Dear "+ list.get(i).studentName + " " + list.get(i).studentSurname
                                        +"<br><br>"+ tutor_name.toString() +" " + tutor_surname.toString()
                                        +" has Declined your request for:<br>Subject: "
                                        + list.get(i).subject + "<br>Date: "+ list.get(i).date  +"<br>Time: "
                                        + list.get(i).time + "<br>Description: " + list.get(i).description
                                        +".<br>Reason for declining: " + editText.getText().toString()
                                        +"<br><br>Sent from FindMeTutor.";

                        String adminBody = "Your message";
                        new SendMailTask(context).execute(fromEmail,
                                fromPassword, toEmails, emailSubject, emailBody);


                        tutor_decline connect2server = new tutor_decline(context,l.get(i).tutor_student_id, tutor_id, l.get(i).student_id) ;
                        connect2server.execute() ;

                        e.decline.setText("Declined");

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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
