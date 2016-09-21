package com.example.jared.findmetutor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class TutorsViewAdapter extends RecyclerView.Adapter<TutorsViewAdapter.EventViewHolder>  {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        String sessionID;

        TextView tutorName;
        //RatingBar rating;


        ImageView session;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_tutors);

            tutorName = (TextView)itemView.findViewById(R.id.tutorNameTxt);
            //rating = (RatingBar) itemView.findViewById(R.id.ratingBar);


            session = (ImageView)itemView.findViewById(R.id.tutorDp);
        }
    }

    List<Tutors> events;
    Context context;
    TutorListFragment base;

    TutorsViewAdapter(List<Tutors> events, Context context, TutorListFragment home){
        this.events = events;
        this.context = context;
        this.base = home;
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




    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
