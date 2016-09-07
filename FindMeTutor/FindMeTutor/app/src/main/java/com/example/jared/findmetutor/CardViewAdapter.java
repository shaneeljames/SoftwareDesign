package com.example.jared.findmetutor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.EventViewHolder> {

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


            session = (ImageView)itemView.findViewById(R.id.session);
        }
    }

    List<Session> events;
    Context context;

    CardViewAdapter(List<Session> events, Context context){
        this.events = events;
        this.context = context;
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
    public void onBindViewHolder(EventViewHolder eventViewHolder, final int i) {
        eventViewHolder.subjectName.setText(events.get(i).subjectName);
        eventViewHolder.amount.setText(events.get(i).amount);
        eventViewHolder.date.setText(events.get(i).date);
        eventViewHolder.time.setText(events.get(i).time);
        eventViewHolder.desc.setText(events.get(i).desc);
        eventViewHolder.status.setText(events.get(i).status);
        eventViewHolder.session.setImageResource(events.get(i).photoId);



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
