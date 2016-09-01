package com.example.jared.findmetutor;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity  implements FragmentDrawer.FragmentDrawerListener{

    private String name, email;
    private TextView test;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //test
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //get extras from previous screen
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             name = extras.getString("name");

            //The key argument here must match that used in the other activity
        }

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
