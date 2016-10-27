package com.example.jared.findmetutor;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.*;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;


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
    String studentid;
    getTutors connect2server;
    List<Tutors> list = new ArrayList<>();

    RecyclerView rv;

    String id;

    Button requestGps;
    CardView check;
    Button checkOut;
    TextView cords;
    ImageView tutorDp;
    RatingBar rating;
    TextView num;
    TextView email;
    TextView name;
    TextView info;


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

    Double latt,longg;

    Button cancel;




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
        info=(TextView)rootView.findViewById(R.id.infoText);
        check = (CardView)rootView.findViewById(R.id.cv3);
        checkOut = (Button)rootView.findViewById(R.id.checkOut);

        cancel = (Button)rootView.findViewById(R.id.cancel_session);

        tutStdNum = this.getArguments().getString("tutor_student_num");
        sessionId = this.getArguments().getString("sessionID");
        tutorID = this.getArguments().getString("tutorID");

        getTutorinfo = new studentTutor(this.getActivity(), tutorID, tutorr);
        getTutorinfo.delegate = this;
        getTutorinfo.execute();
       // SharedPreferences myprefs ;

        myprefs = getContext().getSharedPreferences("user", MODE_PRIVATE);


        studentid = myprefs.getString("student_id", null);
        Toast.makeText(getContext(), tutStdNum+ " Session id :"+sessionId,Toast.LENGTH_SHORT).show();

        try{
            Picasso.with(getContext()).load("http://neural.net16.net/pictures/t"+tutStdNum+"JPG").into(tutorDp);
        }catch (Exception e){

        }

        requestGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 0;
                getLocation = new GetLocation2(getActivity(), sessionId, status, tutorStudentFragment  );


                //getLoco();

            }
        });

        checkOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status =1; //to check out
                getLocation = new GetLocation2(getActivity(), sessionId,status,tutorStudentFragment);

                requestGps.setEnabled(false);


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });







        return rootView;
    }


    public void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
       // myprefs =  getContext().getSharedPreferences("user", MODE_PRIVATE);


        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "Hello, " + editText.getText(), Toast.LENGTH_LONG).show();
                        student_cancel can = new student_cancel(getContext(),sessionId,studentid) ;
                        can.execute();
                        //sendEmails();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void showRatingDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.rating_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        // myprefs =  getContext().getSharedPreferences("user", MODE_PRIVATE);


        final RatingBar ratingBar =(RatingBar)promptView.findViewById(R.id.ratingBarDiag) ;
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String rati = ratingBar.getRating()+"";

                        //sendEmails();
                        student_rateTutor rateTutor = new student_rateTutor(getContext(), sessionId, rati);
                        rateTutor.execute();

                        student_updatetutorrating updatetutorrating = new student_updatetutorrating(getContext(),tutorID );
                        updatetutorrating.execute();
                        //Go through the login to go to the home screen
                        String email=myprefs.getString("student_email",null);
                        String pass= myprefs.getString("student_password", null);
                        ProgressBar tmp = null;
                        login in = new login(getActivity(), email, pass, tmp);
                        in.execute();
                    }
                })
                .setNegativeButton("Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public void getLoco(double lat, double lon){





        // String add = getCompleteAddressString(lat, lon);
        //Toast.makeText(getContext(), add, Toast.LENGTH_SHORT).show();
        cords.setText(lat + " "+lon);

       // String dress = getCompleteAddressString(lat,lon);

       // latt = lat;
       // longg = lon;

        //Toast.makeText(getContext(), add, Toast.LENGTH_SHORT).show();

            //LocationAddress locationAddress = new LocationAddress();
            //locationAddress.getAddressFromLocation(38.898748, -77.037684
             //       , getContext(), new GeocoderHandler());

        //String add = getAddressString(lat,lon);
        //Toast.makeText(getContext(), add, Toast.LENGTH_LONG);
        //info.setText(add);

        check.setVisibility(View.VISIBLE);







    }

    public void sendEmails(List<TutorsEList> list){
/*
            String fromEmail = "FindmetutorSD3@gmail.com";
            String fromPassword = "findmetutors";
            String toEmails = list.get(i).tutorEmail.toString(); //Sessions.get(i).studentEmail.toString() ;
            String adminEmail = "admin@gmail.com";
            String emailSubject = "Find Me Tutor - Session Request";
            String adminSubject = "App Registration Mail";
            String emailBody =
                    "Dear " + list.get(i).TutorName
                            + "<br><br>";

            String adminBody = "Your message";
            new SendMailTask(getContext()).execute(fromEmail,
                    fromPassword, toEmails, emailSubject, emailBody);*/
    }

    private String getAddressString(double latitude, double longitude) {
        String strAddress = "";

        if(Geocoder.isPresent()) {
            Toast.makeText(getContext(),"Coder is present!",Toast.LENGTH_LONG);
            Geocoder geocoder = new Geocoder(getContext(), Locale.ENGLISH);
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (addresses != null) {
                    Address returnAddress = addresses.get(0);
                    StringBuilder strReturnAddress = new StringBuilder("");

                    for (int i = 0; i < returnAddress.getMaxAddressLineIndex(); i++) {
                        strReturnAddress
                                .append(returnAddress.getAddressLine(i)).append(
                                "\n");
                    }
                    strAddress = strReturnAddress.toString();
                    Log.w("address",
                            "" + strReturnAddress.toString());
                } else {
                    Log.w("address", "No Address found!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.w("address", "Can't get Address!");
            }
        }

        else {
            Toast.makeText(getContext(),"Coder is NOT present!",Toast.LENGTH_LONG);
            Log.w("address", "not here");
        }
        return strAddress;
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


        showRatingDialog();



    }

    @Override
    public void processFinish2(String out) {


       // Toast.makeText(getContext(), out,Toast.LENGTH_SHORT).show();

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
