package com.aittaleb.abdelhamid.taximobile;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by abdelhamid on 20/05/17.
 */

public class MyService extends Service {

    private final IBinder mBinder = new LocalBinder();
    //Binder given to client
    //Local binder is a class which instantiats a Myservice Object
    private final Random mGenerator = new Random();

    private boolean mAllowedRebind;
    //indicates wheter rebind Should be used or not


    @Override
    public void onCreate()
    {
        //called on the creation of the service

        Toast.makeText(getApplicationContext(),"Service Created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //called when invoking a bindService

        return mBinder;//returns the object above
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        //called when service is stopped
        Toast.makeText(this,"Service Unbind",Toast.LENGTH_SHORT).show();

        return mAllowedRebind;


    }
    @Override
    public void onRebind(Intent intent)
    {
        Toast.makeText(this,"Service rebind",Toast.LENGTH_SHORT).show();


    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this,"Service destroyed",Toast.LENGTH_SHORT).show();


    }


    //inner class for instatiating objects of MyService
    public class LocalBinder extends Binder {
        //returns this instance of the LocalService so clients can use public methods
        MyService getService()
        {
            return MyService.this;
        }
    }


    public int getRandomNumber()
    {
        return mGenerator.nextInt(100);
    }
}
