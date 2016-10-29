package com.example.jared.findmetutor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class TutorsViewAdapter extends RecyclerView.Adapter<TutorsViewAdapter.EventViewHolder> implements AsyncResponse  {

    selectTutor select;
    TutorsViewAdapter temp;

    @Override
    public void processFinish(String output) {

    }

    @Override
    public void processFinish2(String out) {


    }

    @Override
    public void processFinish3(String outp) {

    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;



        TextView tutorName;
        RatingBar rating;
        Button selectTutor;
        TextView tutorQuals;


        ImageView session;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_tutors);

            tutorName = (TextView)itemView.findViewById(R.id.tutorNameTxt);
            selectTutor = (Button)itemView.findViewById(R.id.selectTutorBtn);
            rating = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tutorQuals = (TextView)itemView.findViewById(R.id.quali);


            session = (ImageView)itemView.findViewById(R.id.tutorDp);
        }
    }

    List<Tutors> events;
    Context context;
    TutorListFragment base;
    String sessionID;

    TutorsViewAdapter(List<Tutors> events, Context context, TutorListFragment home, String sesID){
        this.events = events;
        this.context = context;
        this.base = home;
        this.sessionID=sesID;
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tutors_cardview, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, final int i) {
        eventViewHolder.tutorName.setText(events.get(i).TutorName);
        eventViewHolder.tutorQuals.setText(events.get(i).tutorQual);
        String rat = events.get(i).Rating;
        int dotIndex = rat.indexOf(".");
        String st = rat.substring(0,dotIndex+2);
        float r = Float.parseFloat(st);
        eventViewHolder.rating.setStepSize(0.1f);
        eventViewHolder.rating.setRating(r);

        //eventViewHolder.session.setImageResource(events.get(i).photoId);
        Toast.makeText(context, "Rating "+ st, Toast.LENGTH_SHORT).show();


        try{
            Picasso.with(context).load("http://neural.net16.net/pictures/t"+events.get(i).tutorStdNum+"JPG").into(eventViewHolder.session);
        }catch (Exception e){

        }


        eventViewHolder.selectTutor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Toast.makeText(context, "Tutor  id: "+events.get(i).tutorID + " "+events.get(i).TutorName, Toast.LENGTH_SHORT).show();

                select = new selectTutor(base, events.get(i).tutorID, sessionID);
                select.execute();
                //Toast.makeText(context,out, Toast.LENGTH_SHORT).show();
                base.switchContent();

            }
        });




    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
