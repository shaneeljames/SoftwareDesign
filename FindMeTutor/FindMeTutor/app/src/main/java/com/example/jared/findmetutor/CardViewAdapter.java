package com.example.jared.findmetutor;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView subName;
        TextView venue;
        TextView date;
        TextView time;
        ImageView session;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            subName = (TextView)itemView.findViewById(R.id.subject);
            venue = (TextView)itemView.findViewById(R.id.venue);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            session = (ImageView)itemView.findViewById(R.id.session);
        }
    }

    List<Event> events;

    CardViewAdapter(List<Event> events){
        this.events = events;
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
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        eventViewHolder.subName.setText(events.get(i).subname);
        eventViewHolder.venue.setText(events.get(i).venue);
        eventViewHolder.date.setText(events.get(i).date);
        eventViewHolder.time.setText(events.get(i).time);
        eventViewHolder.session.setImageResource(events.get(i).photoId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
