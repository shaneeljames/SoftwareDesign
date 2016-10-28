package com.example.tutor.tests;

import android.app.Activity;
import android.test.InstrumentationTestCase;

import com.example.tutor.LoginActivity;
import com.example.tutor.login;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by admin on 15-Oct-16.
 */

public class LoginInstrumentalTest extends InstrumentationTestCase {
    public LoginInstrumentalTest() {


    }

    Activity a ;
    LoginActivity lA ;
    login l ;


    @Override
    protected void setUp() throws Exception{
        super.setUp();

    }

    @Test
    public void testLogin() throws Throwable
    {

       // final NetworkTasks networkTasks = new NetworkTasks(context, new GetMediaListener(signal));

        runTestOnUiThread(new Runnable() {
            public void run() {

                lA = new LoginActivity();
                l = (login) new login(lA, "b@b.b", "ranjith", 5);
                l.execute() ;


            }

        });

        l.signal.await(5, java.util.concurrent.TimeUnit.SECONDS);
        assertEquals(78,l.Test);
      //  l.delegate = lA ;


       //// int check =l.getStuff() ;

    }

    @Test
    public void testL() throws Exception
    {
        assertNotEquals("Error",78,l.Test);
    }




    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
