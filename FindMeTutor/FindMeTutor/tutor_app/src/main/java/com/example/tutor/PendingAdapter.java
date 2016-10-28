package com.example.tutor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView subName;
        TextView date;
        TextView time;
        ImageView session;
        TextView studentName ;
        TextView Description;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            subName = (TextView)itemView.findViewById(R.id.subject);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            session = (ImageView)itemView.findViewById(R.id.session);
            studentName = (TextView)itemView.findViewById(R.id.studentName) ;
            Description = (TextView)itemView.findViewById(R.id.Description) ;
        }
    }

    List<Pending> list;
    Context context;

    PendingAdapter(List<Pending> events, Context context){
        this.list = events;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_pending, viewGroup, false);
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




        eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context, "Index position is: "+i, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
