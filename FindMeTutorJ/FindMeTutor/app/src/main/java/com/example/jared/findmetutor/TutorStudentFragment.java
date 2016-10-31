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
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
public class TutorStudentFragment extends Fragment implements AsyncResponse,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

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
    TextView infoOut;
    TextView cordsOut;


    LocationManager locationManager;
    LocationListener locationListener;

    String tutStdNum;
    String tutorID;
    String subjName;

    String sessionId;

    int status =0;

    double myLat;
    double myLong;

    TutorStudentFragment tutorStudentFragment;

    GetLocation2 getLocation;

    studentTutor getTutorinfo;

    List<Tutors> tutorr = new ArrayList<Tutors>();
    List<StudentTutorSession> sesh = new ArrayList<StudentTutorSession>();
    getTutorStudentSession getSession;
    getTutorStudentSession getSessionRefresh;

    Double latt,longg;

    Button cancel;

    String fname;
    String lname;


    //Stuff for GPS

    private static final String TAG = TutorStudentFragment.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    // UI elements
    private TextView lblLocation;
    private Button btnShowLocation, btnStartLocationUpdates;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        tutorStudentFragment = this;


        final View rootView = inflater.inflate(R.layout.fragment_tutor_student, container, false);
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        tutorDp = (ImageView)rootView.findViewById(R.id.tutorProPic);
        requestGps = (Button) rootView.findViewById(R.id.gpsRequest);
        cords = (TextView) rootView.findViewById(R.id.cordsTxt);
        rating = (RatingBar)rootView.findViewById(R.id.ratingBar);
        num = (TextView)rootView.findViewById(R.id.tutorNumber);
        email=(TextView)rootView.findViewById(R.id.tutorEmail);
        name = (TextView)rootView.findViewById(R.id.tutorName);
        lblLocation =(TextView)rootView.findViewById(R.id.lblLocation);
        check = (CardView)rootView.findViewById(R.id.cv3);
        checkOut = (Button)rootView.findViewById(R.id.checkOut);
        infoOut =(TextView)rootView.findViewById(R.id.infoOutText);
        cordsOut =(TextView)rootView.findViewById(R.id.cordsOutTxt);

        cancel = (Button)rootView.findViewById(R.id.cancel_session);

        tutStdNum = this.getArguments().getString("tutor_student_num");
        sessionId = this.getArguments().getString("sessionID");
        tutorID = this.getArguments().getString("tutorID");
        subjName = this.getArguments().getString("subject");

        toolbar.setTitle(subjName);


        getTutorinfo = new studentTutor(this.getActivity(), tutorID, tutorr);
        getTutorinfo.delegate = this;
        getTutorinfo.execute();
       // SharedPreferences myprefs ;

        check.setVisibility(View.INVISIBLE);

        myprefs = getContext().getSharedPreferences("user", MODE_PRIVATE);


        studentid = myprefs.getString("student_id", null);
        fname = myprefs.getString("student_fname",null);
        lname = myprefs.getString("student_lname",null);

        getSession = new getTutorStudentSession(getActivity(), sessionId, sesh);
        getSession.delegate = this;
        getSession.execute();

        //Toast.makeText(getContext(), tutStdNum+ " Session id :"+sessionId,Toast.LENGTH_SHORT).show();

        try{
            Picasso.with(getContext()).load("http://neural.net16.net/pictures/t"+tutStdNum+"JPG").into(tutorDp);
        }catch (Exception e){

        }

       // we need to check availability of play services
        if (checkPlayServices()) {

// Building the GoogleApi client
            buildGoogleApiClient();
        }




        requestGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //status = 0;
                //getLocation = new GetLocation2(getActivity(), sessionId, status, tutorStudentFragment  );

                //Intent i = new Intent(getActivity(), LoadLocationActivity.class);
                //getActivity().startActivity(i);



                //Toast.makeText(getContext(),  deviceLocation.getLatatude()+" : "+deviceLocation.getLongatude(),Toast.LENGTH_SHORT).show();
                if(sesh.get(0).studentCin.length()==0){

                    displayLocation();

                    check.setVisibility(View.VISIBLE);
                    cancel.setEnabled(true);
                }
                else{
                    requestGps.setText("Checked In");
                    requestGps.setEnabled(false);
                    check.setVisibility(View.VISIBLE);
                    cancel.setEnabled(false);
                }



                //getLoco();

            }
        });

        checkOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(sesh.get(0).studentCout.length()==0){

                    displayLocationOut();
                    showLoadDialog("Checking out");
                }
                else{
                    Toast.makeText(getContext(),  "Waiting for tutor to out",Toast.LENGTH_SHORT).show();
                    checkOut.setText("Checked Out");
                    checkOut.setEnabled(false);
                    check.setVisibility(View.VISIBLE);
                }

                //Toast.makeText(getContext(),  " check out :"+sessionId,Toast.LENGTH_SHORT).show();


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cancel only  if you havent checked in
                if(sesh.get(0).studentCin.length() == 0 ){
                    showInputDialog();
                }
                else if(sesh.get(0).studentCin.length() >0){
                    Toast.makeText(getContext(),"Unable to cancel - Session in progress",Toast.LENGTH_SHORT).show();
                }
            }
        });








        return rootView;
    }



    public void showLoadDialog(String msg) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.load_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        // myprefs =  getContext().getSharedPreferences("user", MODE_PRIVATE);

        final ProgressBar pgBar;


        pgBar =(ProgressBar) promptView.findViewById(R.id.pgbar);

        final TextView textView = (TextView)promptView.findViewById(R.id.textView);
        textView.setText(msg);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
                        //Toast.makeText(getContext(), "Hello, " + editText.getText(), Toast.LENGTH_LONG).show();

                        String fromEmail = "FindmetutorSD@gmail.com";
                        String fromPassword = "findmetutors";
                        String toEmails = tutorr.get(0).tutorEmail ; //Sessions.get(i).studentEmail.toString() ;
                        String adminEmail = "admin@gmail.com";
                        String emailSubject = "Find Me Tutor - Session Request";
                        String adminSubject = "App Registration Mail";
                        String emailBody =
                                "Dear " + tutorr.get(0).TutorName +"<br>"
                                        +"We're sorry but, "+fname+ " "+ lname +" has cancelled the session for the following reason , "+"<br>"
                                        +editText.getText()
                                        + "<br><br>"
                                        +"From FindMeTutor";

                        String adminBody = "Your message";
                        new SendMailTask(getContext()).execute(fromEmail,
                                fromPassword, toEmails, emailSubject, emailBody);

                        student_cancel can = new student_cancel(getContext(),sessionId,studentid) ;
                        can.execute();

                        String email=myprefs.getString("student_email",null);
                        String pass= myprefs.getString("student_password", null);
                        ProgressBar tmp = null;
                        login in = new login(getActivity(), email, pass, tmp);
                        in.execute();
                        showLoadDialog("Cancelling");
                        //sendEmails();
                    }
                }).setNegativeButton("Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
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
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public void getLoco(double lat, double lon){





        // String add = getCompleteAddressString(lat, lon);
        //Toast.makeText(getContext(), lat+" LOCO", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(getContext(),"Coder is present!",Toast.LENGTH_LONG);
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

        Toast.makeText(getContext(),"Done writing",Toast.LENGTH_LONG);

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

        sesh = getSession.getList();

        if(sesh.get(0).studentCin.length()==0){
            requestGps.setEnabled(true);
            requestGps.setVisibility(View.VISIBLE);

            check.setVisibility(View.INVISIBLE);
        }
        else if(sesh.get(0).studentCin.length()>0){
            requestGps.setEnabled(false);
            requestGps.setText("Checked In");

            check.setVisibility(View.VISIBLE);

        }

        if(sesh.get(0).studentCin.length()>0 && sesh.get(0).studentCout.length()==0){
            requestGps.setEnabled(false);
            requestGps.setText("Checked In");

            check.setVisibility(View.VISIBLE);

        }

        else if(sesh.get(0).studentCout.length()>0){
            checkOut.setEnabled(true);
            checkOut.setText("Checked Out");
        }

    }



    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();


            String add = getAddressString(latitude, longitude);

            //Check in
                student_checkin chkIn = new student_checkin(getActivity(), sessionId, "Lat: " + latitude + " Long: " + longitude);
                chkIn.execute();
                lblLocation.setText(latitude + ", " + longitude);
                cords.setText(add);



        } else {

            lblLocation
                    .setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    private void displayLocationOut() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();


            String add = getAddressString(latitude, longitude);


            infoOut.setText(add);
            cordsOut.setText(latitude + ", " + longitude);


            student_checkout chkOut = new student_checkout(getActivity(), sessionId, "Lat: " + latitude + " Long: " + longitude);
            chkOut.delegate = this;
            chkOut.execute();



        } else {

            infoOut
                    .setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

// Once connected with google api, get the location
        //displayLocation();

        Toast.makeText(getContext(),"Connected",Toast.LENGTH_LONG);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }



}
