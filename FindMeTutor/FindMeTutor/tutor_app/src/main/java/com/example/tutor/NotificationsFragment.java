package com.example.tutor;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NotificationsFragment extends Fragment {

    public NotificationsFragment() {
        // Required empty public constructor
    }

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
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        Events_notifications event = new Events_notifications("test","test2","test3","test4",3,"hello","hello");
        event.initializeData();



       // Bundle extras = getActivity().getIntent().getExtras();

       // Bundle intent = getActivity().getIntent().getExtras();
      //  String jsonString = intent.getString("subject");

      //  Toast.makeText(NotificationsFragment.this.getActivity().getApplicationContext(),jsonString , Toast.LENGTH_SHORT).show();




        CardViewNotificationsAdapter adapter = new CardViewNotificationsAdapter(event.events, this.getContext());
        rv.setAdapter(adapter);



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


}

