package com.example.jared.findmetutor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class SubjectsViewAdapter extends RecyclerView.Adapter<SubjectsViewAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView lv;
        TextView subject;
        TextView code;
        ImageView icon;
        Button removeBtn;

        EventViewHolder(View itemView) {
            super(itemView);
            lv = (CardView) itemView.findViewById(R.id.cv_subjects);
            subject = (TextView)itemView.findViewById(R.id.subjectTxt);
            code = (TextView)itemView.findViewById(R.id.codeTxt);
            icon = (ImageView)itemView.findViewById(R.id.icon);
            removeBtn=(Button)itemView.findViewById(R.id.removeBtn);
        }
    }

    List<Subjects> list;
    Context context;

    removeSubject remove;





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
        //handle onclick here
        eventViewHolder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Index position is: "+ i+" "+ eventViewHolder.subject.getText().toString(), Toast.LENGTH_SHORT).show();

             /*   if(i ==0) //Selected subjects card
                {
                    Intent goToSubjects = new Intent(context,SubjectSettingsActivity.class);
                    context.startActivity(goToSubjects);
                }*/
            }
        });

        eventViewHolder.removeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences myprefs =  context.getSharedPreferences("user", MODE_PRIVATE);
                String id= myprefs.getString("student_id", null);
                Toast.makeText(context, "Remove "+ eventViewHolder.subject.getText().toString()+" id "+id, Toast.LENGTH_SHORT).show();


                remove = new removeSubject(context,id, list.get(i).subjID.toString() );
                remove.execute();

                eventViewHolder.removeBtn.setEnabled(false);


            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
