package com.example.jared.findmetutor;

/**
 * Created by Jadon on 09-Aug-16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent requestTutor = new Intent(getActivity(),RequestActivity.class);
                startActivity(requestTutor);
            }
        });

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rvSettings);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        Settings sets = new Settings("h", 0);
        sets.initializeData();



        ListViewAdapter adapter = new ListViewAdapter(sets.set);
        rv.setAdapter(adapter);


        //ListViewAdapter adapter = new ListViewAdapter(sets.set);

        //rv.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.sett, this.directoryEntries));

        //rv.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
