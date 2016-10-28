package com.example.tutor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class SubjectsViewAdapter extends RecyclerView.Adapter<SubjectsViewAdapter.EventViewHolder> {

    SharedPreferences myprefs ;
    String id ;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView lv;
        TextView subject;
        TextView code;
        ImageView icon;
        Button btnRemove ;

        EventViewHolder(View itemView) {
            super(itemView);
            lv = (CardView) itemView.findViewById(R.id.cv_subjects);
            subject = (TextView)itemView.findViewById(R.id.subjectTxt);
            code = (TextView)itemView.findViewById(R.id.codeTxt);
            icon = (ImageView)itemView.findViewById(R.id.icon);
            btnRemove = (Button) itemView.findViewById(R.id.btnRemove) ;
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
        eventViewHolder.code.setText(list.get(i).code.toString());
        eventViewHolder.icon.setImageResource(list.get(i).icon);

         myprefs =  context.getSharedPreferences("user", MODE_PRIVATE);
        id = myprefs.getString("tutor_id", null);

        //handle onclick here
        eventViewHolder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Index position is: "+ i+" "+ eventViewHolder.subject.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        eventViewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  Toast.makeText(context, "remove: "+ i+ eventViewHolder.subject.getText().toString(), Toast.LENGTH_SHORT).show();
                tutor_removeSubject connect2server = new tutor_removeSubject(context,id, list.get(i).subjID) ;
                connect2server.execute() ;

                eventViewHolder.btnRemove.setText("Removed");
                eventViewHolder.btnRemove.setClickable(false);


            }
        });



    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
