package com.example.tutor.tests;

import android.app.Activity;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.tutor.LoginActivity;
import com.example.tutor.login;
import com.example.tutor.tutor_AsyncResponse;

import junit.framework.TestCase;

/**
 * Created by admin on 15-Oct-16.
 */

public class LoginUnitTest extends TestCase {

    Activity a ;
    LoginActivity lA ;
    login l ;

    @Override
    protected void setUp() throws Exception {

        super.setUp();
         lA = new LoginActivity() ;
         l = (login) new login(lA,"b@b.b","ranjith",0);
        //
    }

    @SmallTest
    public void testLogin()
    {

       // l.delegate = (tutor_AsyncResponse) this;
       // String c =  l.execute().toString() ;
        l.execute() ;
        l.delegate = (tutor_AsyncResponse) this;
        String check =l.getStuff() ;
        assertNull(check);
    }



    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
