package com.example.tutor.tests;

import android.app.Activity;

import com.example.tutor.LoginActivity;
import com.example.tutor.login;

import junit.framework.TestCase;

/**
 * Created by admin on 15-Oct-16.
 */

public class LoginUnitTest extends TestCase {

    Activity a ;
    LoginActivity lA ;
    login l ;
    String Email ;
    String Password ;
    String result ;
    int Test ;

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        Email = "b@b.b";
        Password = "ranjith" ;

        //
    }

    public void testLogin() throws Throwable
    {
        lA = new LoginActivity() ;
        l =  new login(lA,"b@b.b","rajith",0);
        l.delegate = this.lA ;
        l.signal.await(10, java.util.concurrent.TimeUnit.SECONDS);
        l.execute() ;

        //l.onPostExecute(l.doInBackground()); ;


        assertEquals(0,l.Test);
    }




    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
