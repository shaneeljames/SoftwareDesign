package com.example.jared.findmetutor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.EventViewHolder>  {

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

        TextView sess;
        TextView avail;

        TextView tutorName;

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
            tutorName = (TextView)itemView.findViewById(R.id.tutorName);
            sess = (TextView)itemView.findViewById(R.id.sessID);
            avail = (TextView)itemView.findViewById(R.id.available);
        }
    }
    String sessID;
    List<Session> events;
    Context context;
    HomeFragment base;

    CardViewAdapter(List<Session> events, Context context, HomeFragment home){
        this.events = events;
        this.context = context;
        this.base = home;

        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
    public void onBindViewHolder( EventViewHolder eventViewHolder, final int i) {


            eventViewHolder.sess.setText(events.get(i).sessionID);
            sessID = eventViewHolder.sess.getText().toString();
            eventViewHolder.subjectName.setText(events.get(i).subjectName);
           // eventViewHolder.tutorName.setText(events.get(i).tutorName);
            eventViewHolder.amount.setText(events.get(i).amount);
            eventViewHolder.date.setText(events.get(i).date);
            eventViewHolder.time.setText(events.get(i).time);
            eventViewHolder.desc.setText(events.get(i).desc);
            eventViewHolder.status.setText(events.get(i).status);//condition this
            eventViewHolder.avail.setText(events.get(i).available);
            eventViewHolder.session.setImageResource(events.get(i).photoId);

            if(eventViewHolder.status.getText().toString().equals("Confirmed") && eventViewHolder.avail.getText().toString().equals(("-1"))){
                //Toast.makeText(context, "Confirmed "+events.get(i).tutorName, Toast.LENGTH_SHORT).show();
                eventViewHolder.tutorName.setVisibility(View.VISIBLE);
                eventViewHolder.tutorName.setText("With "+ events.get(i).tutorName);

                eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "Session id: "+events.get(i).sessionID, Toast.LENGTH_SHORT).show();



                        base.switchContentSession(events.get(i).tutorStdNum, sessID);

                    }
                });


            }

            else if(Integer.parseInt(eventViewHolder.avail.getText().toString())>0)
            {
                eventViewHolder.status.setText("Tutors Available");
                eventViewHolder.cv.setCardBackgroundColor(Color.parseColor("#B0E0E6"));
                eventViewHolder.cv.setMaxCardElevation(getPixelsFromDPs(10));
                eventViewHolder.cv.setCardElevation(getPixelsFromDPs(7));
                eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Session id: "+events.get(i).sessionID, Toast.LENGTH_SHORT).show();



                        base.switchContent(events.get(i).sessionID);

                    }
                });
            }

        eventViewHolder.itemView.setTag(events.get(i));

        }


    // Custom method for converting DP/DIP value to pixels
    protected int getPixelsFromDPs(int dps){
        Resources r = context.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }







        //Toast.makeText(context, "Session id: "+eventViewHolder.sess.getText().toString()+ " Available : "+eventViewHolder.avail.getText().toString(), Toast.LENGTH_LONG).show();





    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
