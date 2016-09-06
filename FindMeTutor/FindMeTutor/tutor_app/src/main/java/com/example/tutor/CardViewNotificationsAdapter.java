package com.example.tutor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



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
        }
    }

    List<Events_notifications> events;
    Context context;

    CardViewNotificationsAdapter(List<Events_notifications> events, Context context){
        this.events = events;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_notifcations, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, final int i) {
        eventViewHolder.subName.setText(events.get(i).subname);
        eventViewHolder.date.setText(events.get(i).date);
        eventViewHolder.time.setText(events.get(i).time);
        eventViewHolder.session.setImageResource(events.get(i).photoId);
        eventViewHolder.studentName.setText(events.get(i).studentName);
        eventViewHolder.Description.setText(events.get(i).Description);




        eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Index position is: "+i, Toast.LENGTH_SHORT).show();
            }
        });

        eventViewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Confirmed: "+i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
