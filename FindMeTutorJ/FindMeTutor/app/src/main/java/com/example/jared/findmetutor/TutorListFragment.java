package com.example.jared.findmetutor;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    getTutors refresh;
    List<Tutors> list = new ArrayList<>();

    RecyclerView rv;

    String id;
    SwipeRefreshLayout swipeRefreshLayout;
    Button cancel;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View rootView = inflater.inflate(R.layout.fragment_tutor_list, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rvTutors);
       // rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);


         id = this.getArguments().getString("session");
        Toast.makeText(getContext(),"Session id "+id,Toast.LENGTH_SHORT);

        connect2server = new getTutors(this.getActivity(), id, list);

        connect2server.delegate = this;
        connect2server.execute();


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



        return rootView;
    }


    public void refreshItems() {
        // Load items
        // ...

        list.clear();
        Toast.makeText(getContext(), "refresh items" + list.size() , Toast.LENGTH_SHORT).show();
        refresh = new getTutors(this.getActivity(), id, list);

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
    public void processFinish(String output) {

        String temp = output.substring(0,2);


            list = connect2server.getList();

             //Toast.makeText(getContext(), "On post LIST : "+output, Toast.LENGTH_SHORT).show();


            TutorsViewAdapter adapter = new TutorsViewAdapter(list, this.getContext(), this, id);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);
            onItemsLoadComplete();


    }

    @Override
    public void processFinish2(String out) {

    }

    @Override
    public void processFinish3(String outp) {

    }

    public void switchContent() {
        mContent = new HomeFragment();

        Bundle bundle=new Bundle();
        //bundle.putString("session", id);

       // mContent.setArguments(bundle);

        //Toast.makeText(getContext(),"Session id "+id,Toast.LENGTH_SHORT);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container_body, mContent);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
