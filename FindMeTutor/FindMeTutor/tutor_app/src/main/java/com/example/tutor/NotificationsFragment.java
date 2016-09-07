package com.example.tutor;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment implements tutor_AsyncResponse{

    public NotificationsFragment() {
        // Required empty public constructor
    }

    List<Notifications> list = new ArrayList<Notifications>() ;
    RecyclerView rv ;
    tutor_getsubject connect2server ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications1, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        SharedPreferences myprefs = this.getActivity().getSharedPreferences("user", MODE_PRIVATE) ;
        String id = myprefs.getString("tutor_id",null) ;

        connect2server = new tutor_getsubject((HomeActivity) this.getActivity(),id , list) ;
        connect2server.delegate = this ;
        connect2server.execute();



       // CardViewNotificationsAdapter adapter = new CardViewNotificationsAdapter(event.events, this.getContext());
      //  rv.setAdapter(adapter);



        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
     //   Button btn1 = (Button) getView().findViewById(R.id.btn1);
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void processFinish(String output) {

        CardViewNotificationsAdapter adapter = new CardViewNotificationsAdapter(connect2server.getList(),this.getActivity().getApplicationContext()) ;


    }
}

