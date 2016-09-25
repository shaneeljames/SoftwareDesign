package com.example.tutor;

import android.app.Activity;
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
import android.widget.Toast;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class SubjectsAddViewAdapter extends RecyclerView.Adapter<SubjectsAddViewAdapter.EventViewHolder> implements tutor_AsyncResponse {

   // SharedPreferences myprefs;
    addSubject connect;
    SubjectsAddViewAdapter temp = this;

    @Override
    public void processFinish(String output) {

    }

   //@Override
    public void processFinish2(String out) {

        Toast.makeText(context, out, Toast.LENGTH_SHORT).show();


    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        CardView lv;
        Button add;
        TextView subject;
        TextView code;
        ImageView icon;
        String subId;


        EventViewHolder(View itemView) {
            super(itemView);
            lv = (CardView) itemView.findViewById(R.id.cv_subjects);
            subject = (TextView)itemView.findViewById(R.id.subjectTxt);
            code = (TextView)itemView.findViewById(R.id.codeTxt);
            icon = (ImageView)itemView.findViewById(R.id.icon);
            add = (Button)itemView.findViewById(R.id.addSub);
        }
    }

    List<Subjects> list;
    Context context;
    String studentID;
    Activity activity;



    SubjectsAddViewAdapter(List<Subjects> events, String stdId, Activity act, Context context){
        this.list = events;
        this.studentID = stdId;
        this.activity =act;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_subject_cardview, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder eventViewHolder, final int i) {
        eventViewHolder.subId= list.get(i).subjID.toString();
        eventViewHolder.subject.setText( list.get(i).subject.toString());
        eventViewHolder.code.setText(list.get(i).code.toString());
        eventViewHolder.icon.setImageResource(list.get(i).icon);



       // String tutor_id = myprefs.getString("tutor_id", null);


        // Toast.makeText(context,"Adding " + id, Toast.LENGTH_SHORT).show();

        //addSubject connect = new addSubject(context,)

        //handle onclick here
        eventViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context,"Adding" +studentID + " "+eventViewHolder.subId, Toast.LENGTH_SHORT).show();
                SharedPreferences myprefs;
                myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                String id = myprefs.getString("tutor_id", null) ;
                Toast.makeText(context,"Adding " +eventViewHolder.subId + " " + id, Toast.LENGTH_SHORT).show();

              //  Toast.makeText(context,"Adding " + id, Toast.LENGTH_SHORT).show();


               connect = new addSubject(activity, id,eventViewHolder.subId);
                connect.delegate = temp;
                connect.execute();

                eventViewHolder.add.setClickable(false);
                eventViewHolder.add.setText("Added");


             /*   if(i ==0) //Selected subjects card
                {
                    Intent goToSubjects = new Intent(context,SubjectSettingsActivity.class);
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
