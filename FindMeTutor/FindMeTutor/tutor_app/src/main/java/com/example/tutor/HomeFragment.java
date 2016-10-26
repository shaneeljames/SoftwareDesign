package com.example.tutor;

/**
 * Created by Jadon on 09-Aug-16.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment implements tutor_AsyncResponse{

    List<tutor_Sessions> list = new ArrayList<tutor_Sessions>();
    SharedPreferences myprefs;
    tutor_getSessions connect2server ;
    tutor_getSessions refresh2server ;
    RecyclerView rv ;
    String tutor_id ;
    String password ;
    TextView empty ;
    SwipeRefreshLayout swipeRefreshLayout ;

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
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swiperefreshlayout4) ;


        empty = (TextView) rootView.findViewById(R.id.txtEmpty1)  ;



        //Handle the card and recycle view

        rv = (RecyclerView) rootView.findViewById(R.id.rvHome);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        myprefs= getContext().getSharedPreferences("user", MODE_PRIVATE);
         tutor_id = myprefs.getString("tutor_id", null) ;

        connect2server = new tutor_getSessions(this.getActivity(),tutor_id,list);
        connect2server.delegate = this;
        connect2server.execute() ;

       swipeRefreshLayout.setColorSchemeColors(
                 getActivity().getResources().getColor(android.R.color.holo_blue_bright)
                , getActivity().getResources().getColor(android.R.color.holo_green_light)
                , getActivity().getResources().getColor(android.R.color.holo_orange_light)
                , getActivity().getResources().getColor(android.R.color.holo_red_light));


        Toast.makeText(getContext(), "No " + list.size(), Toast.LENGTH_SHORT).show();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems() ;
                Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });


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

    @Override
    public void processFinish(String output) {


            list = connect2server.getList();

            CardViewAdapter adapter = new CardViewAdapter(list, this.getContext(), this.getActivity());
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);
           onItemsLoadComplete();


        }
    public void refreshItems() {



        list.clear();
        refresh2server =new tutor_getSessions(this.getActivity(),tutor_id,list);
        refresh2server.delegate = this ;
        refresh2server.execute()  ;

    }

    public void onItemsLoadComplete()
    {
        swipeRefreshLayout.setRefreshing(false);

    }

    }



