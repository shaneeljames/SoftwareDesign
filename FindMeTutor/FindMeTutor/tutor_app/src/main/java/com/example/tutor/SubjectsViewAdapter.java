package com.example.tutor;

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

public class SubjectsViewAdapter extends RecyclerView.Adapter<SubjectsViewAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView lv;
        TextView subject;
        ImageView icon;

        EventViewHolder(View itemView) {
            super(itemView);
            lv = (CardView) itemView.findViewById(R.id.cv_subjects);
            subject = (TextView)itemView.findViewById(R.id.subjectTxt);
            icon = (ImageView)itemView.findViewById(R.id.icon);
        }
    }

    List<Subjects> list;
    Context context;



    SubjectsViewAdapter(List<Subjects> events, Context context){
        this.list = events;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subjects_cardview, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder eventViewHolder, final int i) {
        eventViewHolder.subject.setText( list.get(i).subject.toString());
        eventViewHolder.icon.setImageResource(list.get(i).icon);

        //handle onclick here
        eventViewHolder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Index position is: "+ i+" "+ eventViewHolder.subject.getText().toString(), Toast.LENGTH_SHORT).show();

             /*   if(i ==0) //Selected subjects card
                {
                    Intent goToSubjects = new Intent(context,Tutor_SubjectSettingsActivity.class);
                    context.startActivity(goToSubjects);
                }*/
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
