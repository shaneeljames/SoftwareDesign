package com.example.jared.findmetutor;

import android.content.Context;
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
        Toast.makeText(context,out, Toast.LENGTH_SHORT).show();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;



        TextView tutorName;
        //RatingBar rating;
        Button selectTutor;


        ImageView session;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_tutors);

            tutorName = (TextView)itemView.findViewById(R.id.tutorNameTxt);
            selectTutor = (Button)itemView.findViewById(R.id.selectTutorBtn);
            //rating = (RatingBar) itemView.findViewById(R.id.ratingBar);


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
       // eventViewHolder.rating.setNumStars(2);

        eventViewHolder.session.setImageResource(events.get(i).photoId);

        eventViewHolder.selectTutor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Toast.makeText(context, "Tutor  id: "+events.get(i).tutorID + " "+events.get(i).TutorName, Toast.LENGTH_SHORT).show();

                select = new selectTutor(base, events.get(i).tutorID, sessionID);
                select.delegate = temp;
                select.execute();

            }
        });




    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
