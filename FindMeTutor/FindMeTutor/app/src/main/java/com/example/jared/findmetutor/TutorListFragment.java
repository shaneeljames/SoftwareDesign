package com.example.jared.findmetutor;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TutorListFragment extends Fragment implements AsyncResponse {

    Toolbar toolbar;

    public TutorListFragment() {
        // Required empty public constructor
    }

    Fragment mContent;
    SharedPreferences myprefs;

    getTutors connect2server;
    List<Tutors> list = new ArrayList<>();

    RecyclerView rv;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View rootView = inflater.inflate(R.layout.fragment_tutor_list, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rvTutors);
       // rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        TutorsViewAdapter adapter = new TutorsViewAdapter(list, this.getContext(), this);
        rv.setAdapter(adapter);

        String id = this.getArguments().getString("session");
        Toast.makeText(getContext(),"Session id "+id,Toast.LENGTH_SHORT);

        connect2server = new getTutors(this.getActivity(), id, list);

        connect2server.delegate = this;
        connect2server.execute();




        return rootView;
    }

    @Override
    public void processFinish(String output) {

        String temp = output.substring(0,2);


            list = connect2server.getList();

             Toast.makeText(getContext(), "On post LIST : "+output, Toast.LENGTH_SHORT).show();


            TutorsViewAdapter adapter = new TutorsViewAdapter(list, this.getContext(), this);
            rv.setAdapter(adapter);


    }

    @Override
    public void processFinish2(String out) {

    }
}
