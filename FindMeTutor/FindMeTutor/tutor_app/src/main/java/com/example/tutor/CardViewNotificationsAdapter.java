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
import android.widget.Toast;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


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
        Button decline;

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
            decline = (Button)itemView.findViewById(R.id.btnDecline) ;
        }
    }

    List<Notifications> list;
    Context context;

    CardViewNotificationsAdapter(List<Notifications> events, Context context){
        this.list = events;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_notifcations, viewGroup, false);
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

        eventViewHolder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences myprefs;
                myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                String id = myprefs.getString("tutor_id", null) ;

            tutor_decline connect2server = new tutor_decline(context,list.get(i).tutor_student_id,id, list.get(i).student_id) ;
                connect2server.execute() ;
                eventViewHolder.decline.setText("Declined");
                //  Toast.makeText(context, "Index position is: "+i, Toast.LENGTH_SHORT).show();
            }
        });

        eventViewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Confirmed: "+i, Toast.LENGTH_SHORT).show();

                SharedPreferences myprefs;
                myprefs =  context.getSharedPreferences("user",MODE_PRIVATE ) ;
                String id = myprefs.getString("tutor_id", null) ;

               // Toast.makeText(context, "tut: "+list.get(i).tutor_student_id, Toast.LENGTH_SHORT).show();

                tutor_confirm connect2server = new tutor_confirm(context,list.get(i).tutor_student_id,id, list.get(i).student_id) ;
                connect2server.execute();

                Toast.makeText(context, "Your agreement has been sent to " + list.get(i).studentName +" " + list.get(i).studentSurname, Toast.LENGTH_SHORT).show();
                eventViewHolder.confirm.setText("confirmed");
                eventViewHolder.confirm.setClickable(false);





                //  tutor_getsubject2 connect2server2 = new tutor_getsubject2(context,id) ;


            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
