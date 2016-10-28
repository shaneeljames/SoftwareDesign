package com.example.jared.findmetutor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceLocation extends TutorStudentFragment implements ConnectionCallbacks, OnConnectionFailedListener{

    public static final String TAG = LoadLocationActivity.class.getSimpleName();

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    public Location mLastLocation;

    // Google client to interact with Google API
    public GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    public boolean mRequestingLocationUpdates = false;

    public LocationRequest mLocationRequest;

    // Location updates intervals in sec
    public static int UPDATE_INTERVAL = 10000; // 10 sec
    public static int FATEST_INTERVAL = 5000; // 5 sec
    public static int DISPLACEMENT = 10; // 10 meters

    // UI elements
    public TextView lblLocation;
    public Button btnShowLocation, btnStartLocationUpdates;

    double latitude, longitude;

    Context context;
    Activity activity;
    TutorStudentFragment tutorStudentFragment;



    public DeviceLocation(){


    }

    public void setStuff(Context con, Activity act, TutorStudentFragment tsf){
        context = con;
        activity = act;
        tutorStudentFragment = tsf;
        doChecks();
    }


    public void doChecks(){
        if (checkPlayServices()) {

// Building the GoogleApi client
            buildGoogleApiClient();
        }


    }

    /**
     * Method to display the location on UI
     * */
    public void displayLocation() {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            tutorStudentFragment.getLoco(latitude,longitude);

            //lblLocation.setText(latitude + ", " + longitude);

        } else {

            //lblLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    /**
     * Creating google api client object
     * */
    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode,activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                activity.finish();
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
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    public double getLongatude(){
        return  longitude;
    }

    public  double getLatatude(){
        return  latitude;
    }

}
