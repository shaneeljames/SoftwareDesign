package com.example.jared.findmetutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity  implements FragmentDrawer.FragmentDrawerListener{

    private String name, email;
    private TextView test;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    String student_id, student_password, student_lname, student_fname, student_student_num, student_email, student_contact_number, student_current_balance;
    ImageView img;

    SharedPreferences myprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //test
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //get extras from previous screen
        //get extras from login

        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("user");
       // Toast.makeText(getApplicationContext(), "Login Successful "+jsonString, Toast.LENGTH_SHORT).show();

        //conv json to strings

        try {

            JSONArray jsonArr = new JSONArray(jsonString);
           // Toast.makeText(getApplicationContext(), "Login Successful "+jsonString, Toast.LENGTH_SHORT).show();
            //Subjects pass = null;
            //Subjects subjects = new Subjects("h", 0, parent, pass);

                JSONObject jsObj = jsonArr.getJSONObject(0);//get rood json obj
                student_id = jsObj.getString("student_id");
                student_student_num = jsObj.getString("student_student_number");
                student_fname = jsObj.getString("student_fname");
                student_lname = jsObj.getString("student_lname");
                student_contact_number = jsObj.getString("student_contact_num");
                student_email = jsObj.getString("student_email");
                student_password = jsObj.getString("student_password");
                student_current_balance = jsObj.getString("student_current_balance");



        } catch (JSONException e) {
            e.printStackTrace();
        }

       // Toast.makeText(getApplicationContext(), student_id+" fname: "+student_fname+student_password+student_email+student_contact_number, Toast.LENGTH_LONG).show();

        //Set Shared prefrences so we can access usr data throughout activities

        myprefs= this.getSharedPreferences("user", MODE_PRIVATE);
        myprefs.edit().putString("student_id", student_id).apply();
        myprefs.edit().putString("student_password", student_password).apply();
        myprefs.edit().putString("student_lname", student_lname).apply();
        myprefs.edit().putString("student_fname", student_fname).apply();
        myprefs.edit().putString("student_student_num", student_student_num).apply();
        myprefs.edit().putString("student_email",student_email).apply();
        myprefs.edit().putString("student_contact_number", student_contact_number).apply();
        myprefs.edit().putString("student_current_balance", student_current_balance).apply();

        //set up nav and toolBar

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        TextView n = (TextView)drawerFragment.getView().findViewById(R.id.nameTxt);
        TextView c = (TextView)drawerFragment.getView().findViewById(R.id.creditsTxt);
        img = (ImageView)drawerFragment.getView().findViewById(R.id.iv);

        n.setText(student_fname+ " "+student_lname);
        c.setText(student_current_balance);

        try {
            Picasso.with(getApplicationContext()).load("http://neural.net16.net/pictures/s" + student_student_num + "JPG" ).into(img);
        }catch (Exception e)
        {

            Toast.makeText(getApplicationContext(), "No profile picture", Toast.LENGTH_SHORT).show();
            img.setImageResource(R.drawable.ic_profile_greenp);

        }


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
   /*  int count = 0 ;
    double start ;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (count == 0)) {
            start = System.currentTimeMillis() ;
            Toast.makeText(getApplicationContext(), "Press back button again to log out", Toast.LENGTH_SHORT).show();
            count++;
        }
        else if(System.currentTimeMillis()- start <2000) //timer to make sure double back button is quick
        {
            //Remove all stored user data
            /*SharedPreferences.Editor editor = myprefs.edit();
            editor.clear();
            editor.commit();

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
    }*/


    private void displayView(int position) {
        Fragment fragment = null;
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
                //Press logout, takes you back to login page
                //Remove all stored user data

               Intent home = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivity(home);
                /*SharedPreferences.Editor editor = myprefs.edit();
                editor.clear();
                editor.commit();*/

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
