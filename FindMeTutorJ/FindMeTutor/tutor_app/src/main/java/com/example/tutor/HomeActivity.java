package com.example.tutor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity  implements FragmentDrawer.FragmentDrawerListener{

    private String name, email;
    private TextView test;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    String tutor_id, tutor_password, tutor_lname, tutor_fname, tutor_student_num, tutor_email, tutor_contact_num, tutor_current_balance , tutor_qualifications;
    String tutor_rating ;
    SharedPreferences myprefs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //test
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("user");

        //conv json to strings

        try {

            JSONArray jsonArr = new JSONArray(jsonString);
          //  Toast.makeText(getApplicationContext(), "Login blah "+jsonString, Toast.LENGTH_SHORT).show();

            JSONObject jsObj = jsonArr.getJSONObject(0);//get rood json obj
            tutor_id = jsObj.getString("tutor_id");
            // Toast.makeText(parent.getApplicationContext(), code, Toast.LENGTH_SHORT).show();
            tutor_password = jsObj.getString("tutor_password");
            tutor_lname = jsObj.getString("tutor_lname");
            tutor_fname = jsObj.getString("tutor_fname");
            tutor_student_num = jsObj.getString("tutor_student_num");
            tutor_email = jsObj.getString("tutor_email");
            tutor_contact_num = jsObj.getString("tutor_contact_num");
            tutor_current_balance = jsObj.getString("tutor_balance");
            tutor_qualifications = jsObj.getString("tutor_qualifications") ;
            tutor_rating = jsObj.getString("tutor_rating") ;

            Toast.makeText(getApplicationContext(), "Login Qu "+tutor_qualifications, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        myprefs=this.getSharedPreferences("user", MODE_PRIVATE) ;
        myprefs.edit().putString("tutor_id", tutor_id).apply();
        myprefs.edit().putString("tutor_fname", tutor_fname).apply();
        myprefs.edit().putString("tutor_lname", tutor_lname).apply();
        myprefs.edit().putString("tutor_balance", tutor_current_balance).apply();
        myprefs.edit().putString("tutor_student_num", tutor_student_num).apply();
        myprefs.edit().putString("tutor_qualifications", tutor_qualifications).apply();
        myprefs.edit().putString("tutor_email", tutor_email).apply();
        myprefs.edit().putString("tutor_contact_num", tutor_contact_num).apply();
        myprefs.edit().putString("tutor_password", tutor_password).apply();
        myprefs.edit().putString("tutor_rating", tutor_rating).apply();



        Toast.makeText(getApplicationContext(), "Login blah "+tutor_lname, Toast.LENGTH_SHORT).show();





        //set up nav and toolBar

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        Bundle bundle = new Bundle();
        bundle.putString("str","String");

        displayView(0);



    } //onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    //back button implementation
    int count = 0 ;
    double start ;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (count == 0)) {
            start = System.currentTimeMillis() ;
            Toast.makeText(getApplicationContext(), "Press back button again to log out", Toast.LENGTH_SHORT).show();
            count++;
        }
        else if(System.currentTimeMillis()- start <2000) //timer to make sure double back button is quick
        {
            Intent home = new Intent(HomeActivity.this, LoginActivity.class);
            HomeActivity.this.startActivity(home);
            this.finish();
            count =0 ;
        }
        else
        {
            count = 0;
            Toast.makeText(getApplicationContext(), "Press back button again to log out", Toast.LENGTH_SHORT).show();
        }

        return true ;
    }


    private void displayView(int position) {
        Fragment fragment = null;
        Activity act = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new FriendsFragment();
                title = getString(R.string.title_friends);
                break;
            case 2:
                fragment = new SettingsFragment();
                title = getString(R.string.title_Settings);
                break;
            case 3:
                Intent noti = new Intent(HomeActivity.this, NotificationsActivity.class);
                HomeActivity.this.startActivity(noti);
                title = "Notifications";
                break;
            case 4:
                Intent pending = new Intent(HomeActivity.this, PendingSessionsActivity.class);
                HomeActivity.this.startActivity(pending);
                break;
            case 5:
                //Press logout, takes you back to login page
                Intent home = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivity(home);
                this.finish() ;
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
