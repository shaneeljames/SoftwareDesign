package com.example.tutor;

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


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    //Fragments work differently than activitues
    //you put your code in the onCreate View instead of the inCreate as with Activities
    //and refrence the rootView object
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent requestTutor = new Intent(getActivity(),RequestActivity.class); //this is how to start an activity from a fragment
                startActivity(requestTutor);
            }
        });


        //Handle the card and recycle view

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));



        Event event = new Event("Complex Analysis","Flower Hall","1 October","8am", R.drawable.session);
        event.initializeData();







        CardViewAdapter adapter = new CardViewAdapter(event.events, this.getContext());
        rv.setAdapter(adapter);



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
