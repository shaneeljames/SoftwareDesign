package com.example.jared.findmetutor;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    RatingBar rating;
    TextView num;
    TextView email;
    TextView name;


    LocationManager locationManager;
    LocationListener locationListener;

    String tutStdNum;
    String tutorID;

    String sessionId;
    int status =0;

    double myLat;
    double myLong;

    TutorStudentFragment tutorStudentFragment;

    GetLocation2 getLocation;

    studentTutor getTutorinfo;

    List<Tutors> tutorr = new ArrayList<Tutors>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        tutorStudentFragment = this;


        final View rootView = inflater.inflate(R.layout.fragment_tutor_student, container, false);
        tutorDp = (ImageView)rootView.findViewById(R.id.tutorProPic);
        requestGps = (Button) rootView.findViewById(R.id.gpsRequest);
        cords = (TextView) rootView.findViewById(R.id.cordsTxt);
        rating = (RatingBar)rootView.findViewById(R.id.ratingBar);
        num = (TextView)rootView.findViewById(R.id.tutorNumber);
        email=(TextView)rootView.findViewById(R.id.tutorEmail);
        name = (TextView)rootView.findViewById(R.id.tutorName);

        tutStdNum = this.getArguments().getString("tutor_student_num");
        sessionId = this.getArguments().getString("sessionID");
        tutorID = this.getArguments().getString("tutorID");

        getTutorinfo = new studentTutor(this.getActivity(), tutorID, tutorr);
        getTutorinfo.delegate = this;
        getTutorinfo.execute();

        //Toast.makeText(getContext(), tutStdNum,Toast.LENGTH_SHORT).show();

        try{
            Picasso.with(getContext()).load("http://neural.net16.net/pictures/t"+tutStdNum+"JPG").into(tutorDp);
        }catch (Exception e){

        }

        requestGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation = new GetLocation2(getActivity(), sessionId, status, tutorStudentFragment  );

                //getLoco();

            }
        });







        return rootView;
    }

    public void getLoco(double lat, double lon){





        // String add = getCompleteAddressString(lat, lon);
        //Toast.makeText(getContext(), add, Toast.LENGTH_SHORT).show();
        cords.setText(lat + " "+lon);






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


        Toast.makeText(getContext(), out,Toast.LENGTH_SHORT).show();

        tutorr = getTutorinfo.getList();

        String rat = tutorr.get(0).Rating;
        int dotIndex = rat.indexOf(".");
        String st = rat.substring(0,dotIndex+2);
        float r = Float.parseFloat(st);
        rating.setStepSize(0.1f);
        rating.setRating(r);

        num.setText(tutorr.get(0).tutorContact);
        email.setText(tutorr.get(0).tutorEmail);
        name.setText(tutorr.get(0).TutorName);



    }

    @Override
    public void processFinish3(String outp) {

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction", "Canont get Address!");
        }
        return strAdd;
    }
}
