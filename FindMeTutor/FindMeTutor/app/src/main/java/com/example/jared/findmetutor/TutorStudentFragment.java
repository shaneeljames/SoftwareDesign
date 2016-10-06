package com.example.jared.findmetutor;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.*;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TutorStudentFragment extends Fragment implements AsyncResponse {

    Toolbar toolbar;

    public TutorStudentFragment() {
        // Required empty public constructor
    }

    Fragment mContent;
    SharedPreferences myprefs;

    getTutors connect2server;
    List<Tutors> list = new ArrayList<>();

    RecyclerView rv;

    String id;

    Button requestGps;
    TextView cords;
    ImageView tutorDp;

    LocationManager locationManager;
    LocationListener locationListener;

    String tutStdNum;

    String sessionId;
    int status =0;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_tutor_student, container, false);
        tutorDp = (ImageView)rootView.findViewById(R.id.tutorProPic);
        requestGps = (Button) rootView.findViewById(R.id.gpsRequest);
        cords = (TextView) rootView.findViewById(R.id.cordsTxt);

  /*      locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                cords.setText("\n "+location.getLatitude() + " "+location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
            }else{

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            }
*/
        tutStdNum = this.getArguments().getString("tutor_student_num");
        sessionId = this.getArguments().getString("sessionID");

        Toast.makeText(getContext(), tutStdNum,Toast.LENGTH_SHORT).show();

        try{
            Picasso.with(getContext()).load("http://neural.net16.net/pictures/t"+tutStdNum+"JPG").into(tutorDp);
        }catch (Exception e){

        }

        requestGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetLocation2 getLocation = new GetLocation2(getActivity(), sessionId, status  );
            }
        });








        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    configButton();

                return;
        }
    }

    public void configButton(){
        requestGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void processFinish(String output) {




    }

    @Override
    public void processFinish2(String out) {

    }
}
