package com.example.ludwi.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Stepsservice is a service that counts the steps take by the user and store is it the database.
 * Created by Ludwig Ninn on 2017-02-09.
 */
public class StepsService extends Service implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    ChangeListener mListener;
    LocalBinder mBinder;
    boolean isSensorPresent;
    private StepsDBHelper mStepsDBHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new LocalBinder();
        mSensorManager = (SensorManager)
                this.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        Log.d("StepsService", "connected");
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        } else {
            isSensorPresent = false;
        }
        mStepsDBHelper = new StepsDBHelper(this);
        Log.d("StepsService", "connected");

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        if(isSensorPresent) {
            Log.d("StepsService", "connected");
            mSensorManager.registerListener(this, mStepDetectorSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            return mBinder;
        }
        return mBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        mStepsDBHelper.createStepsEntry();
        if(mListener!=null) {
            mListener.update();
        }
        Log.d("Stepcreated", "Steppcreated");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Returns a instance of the service for the serviceConnection.
     */
    public class LocalBinder extends Binder {
        StepsService getService() {

            return StepsService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isSensorPresent) {
            mSensorManager.unregisterListener(this);
        }
    }

    /**
     * Set the activity that wants to read steps. The Activity implements a interface called ChangeListner to change the activity.
     * @param listener
     */
    public void setListenerActivity(ChangeListener listener) {
        mListener = listener;
    }
}
