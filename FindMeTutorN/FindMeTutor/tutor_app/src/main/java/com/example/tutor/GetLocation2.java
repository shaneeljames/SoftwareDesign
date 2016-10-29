package com.example.tutor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by admin on 03-Oct-16.
 */


public class GetLocation2 {

    private LocationManager locationManager;
    private LocationListener locationListener;
    Activity parent;
    String TutorStudentID;
    int status;


    public GetLocation2(Activity par, String tsi, int i) {

        parent = par;
        TutorStudentID = tsi;
        final int[] count = {0};
        status = i;

        locationManager = (LocationManager) parent.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //  txtLocation.append("\n " + location.getLatitude() + " "+ location.getLongitude());
                if (count[0] == 0) {

                    if (status == 0) {
                        Toast.makeText(parent.getApplicationContext(), "Lat: " + location.getLatitude() + " Long: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        tutor_checkin connect2server = new tutor_checkin(parent, TutorStudentID, "Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
                        connect2server.execute();
                    }
                    if (status == 1) {
                        Toast.makeText(parent.getApplicationContext(), "Lat: " + location.getLatitude() + " Long: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        tutor_checkout connect2server1 = new tutor_checkout(parent, TutorStudentID, "Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
                        connect2server1.execute();
                    }

                    count[0]++;
                }

                Toast.makeText(parent.getApplicationContext(), "Lat: " + location.getLatitude() + " Long: " + location.getLongitude(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                //  onLocationChanged();


            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                parent.startActivity(intent);



            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                parent.requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
                return;
            }
        } else {

            //  if(count ==0) {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
           // locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            // count++ ;
            // }

            //  configureButton();
            // btnLocation.performClick();
            //  locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

        }

    }




    public void requestLocations() {

        if (ActivityCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);
      //  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }



}