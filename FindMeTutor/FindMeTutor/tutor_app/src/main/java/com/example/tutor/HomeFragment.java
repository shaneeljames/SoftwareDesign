package com.example.tutor;

/**
 * Created by Jadon on 09-Aug-16.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment implements tutor_AsyncResponse{

    List<tutor_Sessions> list = new ArrayList<tutor_Sessions>();
    SharedPreferences myprefs;
    tutor_getSessions connect2server ;
    RecyclerView rv ;

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
              //  Intent requestTutor = new Intent(getActivity(),RequestActivity.class); //this is how to start an activity from a fragment
               // startActivity(requestTutor);
            }
        });


        //Handle the card and recycle view

        rv = (RecyclerView) rootView.findViewById(R.id.rvHome);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        myprefs= getContext().getSharedPreferences("user", MODE_PRIVATE);
        String tutor_id = myprefs.getString("tutor_id", null) ;

        connect2server = new tutor_getSessions(this.getActivity(),tutor_id,list);
        connect2server.delegate = this;
        connect2server.execute() ;


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


    public void processFinish(String output) {
        String temp = output.substring(0,2);


        if(temp.equals("[]"))
        {
            Toast.makeText(getContext(), "No subjects Ass some", Toast.LENGTH_SHORT).show();
        }
        else{


            Toast.makeText(getContext(), "On post LIST : "+list.get(1).subjectName, Toast.LENGTH_SHORT).show();

            list = connect2server.getList();

            CardViewAdapter adapter = new CardViewAdapter(list, this.getContext());
             rv.setAdapter(adapter);
        }



    }


}
