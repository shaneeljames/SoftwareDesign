package com.example.jared.findmetutor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    double longa;
    double lat;


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
                        lat = location.getLatitude();
                        longa= location.getLongitude();
                        //Toast.makeText(parent.getApplicationContext(), "Lat: " + lat+ " Long: " + longa, Toast.LENGTH_SHORT).show();
                        student_checkin connect2server = new student_checkin(parent, TutorStudentID, "Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
                        connect2server.execute();
                    }
                    if (status == 1) {
                        Toast.makeText(parent.getApplicationContext(), "Lat: " + location.getLatitude() + " Long: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        student_checkout connect2server1 = new student_checkout(parent, TutorStudentID, "Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
                        connect2server1.execute();
                    }

                    count[0]++;
                }


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

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
            locationManager.requestLocationUpdates("gps", -1, 0, locationListener);
            // count++ ;
            // }

            //  configureButton();
            // btnLocation.performClick();
            //  locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // int count = 0 ;
        switch (requestCode) {
            case 10:
                // if(count == 0 ) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(parent.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(parent.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates("gps", -1, 0, locationListener);
                      //  count++;

                    }
                //}


                //  configureButton();
        }

    }

    public double getLong (){
        return longa;
    }

    public  double getLat(){
        return lat;
    }

}






