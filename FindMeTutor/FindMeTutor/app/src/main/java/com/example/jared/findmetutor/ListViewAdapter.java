package com.example.jared.findmetutor;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jadon on 30-Aug-16.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView lv;
        TextView setting;
        ImageView icon;

        EventViewHolder(View itemView) {
            super(itemView);
            lv = (CardView) itemView.findViewById(R.id.cv2);
            setting = (TextView)itemView.findViewById(R.id.settingTxt);
            icon = (ImageView)itemView.findViewById(R.id.icon);
        }
    }

    List<Settings> set;



    ListViewAdapter(List<Settings> events){
        this.set = events;
    }

    @Override
    public int getItemCount() {
        return set.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.settings_listview, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        eventViewHolder.setting.setText( set.get(i).sName.toString());
        eventViewHolder.icon.setImageResource(set.get(i).photoId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
