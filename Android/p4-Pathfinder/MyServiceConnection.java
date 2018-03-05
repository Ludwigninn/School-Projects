package com.example.ludwi.myapplication;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * MyServiceConnection connects the activity with the service.
 * Created by Ludwig Ninn on 2017-02-09.
 */
public class MyServiceConnection  implements ServiceConnection {
    private final MainActivity mActivity;


    public MyServiceConnection(MainActivity a){
        mActivity = a;
    }

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {


        StepsService.LocalBinder binder = (StepsService.LocalBinder) service;

        mActivity.mService = binder.getService();
        mActivity.mBound = true;
        mActivity.mService.setListenerActivity(mActivity);
    }
    @Override
    public void onServiceDisconnected(ComponentName arg0) {
        mActivity.mBound = false;
    }
}
