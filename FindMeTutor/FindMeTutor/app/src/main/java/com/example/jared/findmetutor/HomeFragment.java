package com.example.jared.findmetutor;

/**
 * Created by Jadon on 09-Aug-16.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment implements AsyncResponse{


    Fragment mContent;
    SharedPreferences myprefs;

    getSessions connect2server;
    getSessionsRefresh refresh;
    List<Session> list = new ArrayList<>();
    List<Session> tmp = new ArrayList<>();

    RecyclerView rv;
    CardViewAdapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;

    String id;

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
        list = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        myprefs= getContext().getSharedPreferences("user", MODE_PRIVATE);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //get user info from sharedSettings

                String balance= myprefs.getString("student_current_balance", null);

                if(Integer.parseInt(balance)<=1)
                {
                    Toast.makeText(getContext(), "Insufficient Funds", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent requestTutor = new Intent(getActivity(),RequestActivity.class); //this is how to start an activity from a fragment
                    startActivity(requestTutor);
                }

            }
        });


        //Handle the card and recycle view

        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        id = myprefs.getString("student_id",null);
       // Toast.makeText(getContext(), "On post " + id , Toast.LENGTH_SHORT).show();

        connect2server = new getSessions(this.getActivity(), id, list);

        connect2server.delegate = this;
        connect2server.execute();




        //Session event = new Session("Complex Analysis","Flower Hall","1 October","8am", R.drawable.session);
        //event.initializeData();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               // Refresh items
               refreshItems();
           }
       });





        // Inflate the layout for this fragment
        return rootView;
    }

    public void refreshItems() {
        // Load items
        // ...

        list.clear();
        Toast.makeText(getContext(), "refresh items" + list.size() , Toast.LENGTH_SHORT).show();
        refresh = new getSessionsRefresh(this.getActivity(), id, list);

        refresh.delegate = this;
        refresh.execute();


        // Load complete
       // onItemsLoadComplete();
    }

    public void onItemsLoadComplete() {


        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
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
        String temp = output.substring(0,2);


        if(temp.equals("[]"))
        {
            Toast.makeText(getContext(), "No subjects Add some", Toast.LENGTH_SHORT).show();
        }
        else{
            list = connect2server.getList();

           // Toast.makeText(getContext(), "On post LIST : "+list.get(1).SubjectName, Toast.LENGTH_SHORT).show();


            adapter = new CardViewAdapter(list, this.getContext(), this);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);
            onItemsLoadComplete();
        }



    }

    //not being called
    @Override
    public void processFinish2(String out) {

        adapter.clear();
        adapter.notifyDataSetChanged();

        list=refresh.getList();
        //list = refresh.getList();
        Toast.makeText(getContext(), "Complete", Toast.LENGTH_SHORT).show();

        adapter = new CardViewAdapter(list, getContext(), this);

        rv.setAdapter(adapter);



        // Stop refresh animation


       // swipeRefreshLayout.setRefreshing(false);
        onItemsLoadComplete();

    }

    @Override
    public void processFinish3(String outp) {

    }

    public void switchContent(String id) {
        mContent = new TutorListFragment();

        Bundle bundle=new Bundle();
        bundle.putString("session", id);

        mContent.setArguments(bundle);

        //Toast.makeText(getContext(),"Session id "+id,Toast.LENGTH_SHORT);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container_body, mContent);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void switchContentSession(String id, String sessID, String tutID) {
        mContent = new TutorStudentFragment();

        Bundle bundle=new Bundle();
        bundle.putString("tutor_student_num", id);
        bundle.putString("sessionID", sessID);
        bundle.putString("tutorID",tutID);

        mContent.setArguments(bundle);

        //Toast.makeText(getContext(),"Session id "+id,Toast.LENGTH_SHORT);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container_body, mContent);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void removeItem(View v){
        int selectItemPos = rv.getChildPosition(v);
        adapter.notifyItemRemoved(selectItemPos);

    }


}
