package com.example.tutor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            rlSession = (RelativeLayout) itemView.findViewById(R.id.llSession) ;


            session = (ImageView)itemView.findViewById(R.id.session);
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

                   Picasso.with(context).load("http://neural.net16.net/Gow.jpg").into(eventViewHolder.session);
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

                }

        }
        });

        eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Index position is: "+i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
