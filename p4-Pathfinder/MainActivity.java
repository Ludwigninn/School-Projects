package com.example.ludwi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * MainAcitivty is the main thread that shows the UI and also checks for event regarding a shake. But its main focus
 * is in the compas emulator.
 * Created by Ludwig Ninn on 2017-02-09.
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener, ChangeListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor, mMagnetometerSensor;
    private ImageView mCompass;
    private boolean mLastAccelerometerSet, mLastMagnetometerSet;
    private  float[] mLastAccelerometer = new float[3];
    private   float[] mLastMagnetometer = new float[3];
    private float[] mOrientation = new float[3];
    private float[] mRotationMatrix = new float[16];
    private long lastUpdateTime, updateTime;
    private float mCurrentDegree;
    private  int mStepCountPerSec;
    private float x, y, z, last_x, last_y, last_z;
    private float shakeThreshold = 50;
    private boolean isFirstValue;
    private TextView tvSteps, tvPerSec;
    private StepsDBHelper mStepsDBHelper;
    private Context context;
    private CharSequence text = "Shake detected";
    private int duration = Toast.LENGTH_SHORT;


    // public variables for the Service connection.
    MyServiceConnection mConnection;
    boolean mBound;
    StepsService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateTime = System.currentTimeMillis();
        mConnection = new MyServiceConnection(this);
        Intent stepsIntent = new Intent(this, StepsService.class);
        boolean res = bindService(stepsIntent, mConnection, Context.BIND_AUTO_CREATE);
        Log.d("test", "" + res);
        mStepsDBHelper = new StepsDBHelper(this);
        context = getApplicationContext();


        //Toast to let the user know how to reset the steps
        Toast toast = Toast.makeText(context, "Press compass for reset", duration);
        toast.show();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            mMagnetometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
        tvPerSec = (TextView) findViewById(R.id.tvPerSec);
        mCompass = (ImageView) findViewById(R.id.compass_image);
        tvSteps = (TextView) findViewById(R.id.tvSteps);
        mCompass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mStepsDBHelper.resetDatabase();
                update();
                Toast toast = Toast.makeText(context, "Reset", duration);
                toast.show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if (event.sensor == mAccelerometerSensor) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometerSensor) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;

        }
        //Emulates a compass
        //only 4 times in 1 second
        if (mLastAccelerometerSet && mLastMagnetometerSet && System.currentTimeMillis() - lastUpdateTime > 250) {

            SensorManager.getRotationMatrix(mRotationMatrix, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mRotationMatrix, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float) (Math.toDegrees(azimuthInRadians) + 360) % 360;
            RotateAnimation mRotateAnimation = new RotateAnimation(mCurrentDegree, -azimuthInDegress, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mRotateAnimation.setDuration(250);
            mRotateAnimation.setFillAfter(true);
            mCompass.startAnimation(mRotateAnimation);
            mCurrentDegree = -azimuthInDegress;
            lastUpdateTime = System.currentTimeMillis();
        }
        // Checks for shakes done by the user and rotates the compass 0-360 if a shake is detected.
        if (mLastAccelerometerSet) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            if (isFirstValue) {
                float deltaX = Math.abs(last_x - x);
                float deltaY = Math.abs(last_y - y);
                float deltaZ = Math.abs(last_z - z);

                if ((deltaX > shakeThreshold && deltaY > shakeThreshold) || (deltaX > shakeThreshold && deltaZ > shakeThreshold) || (deltaY > shakeThreshold && deltaZ > shakeThreshold)) {
                    mStepsDBHelper.createStepsEntry();

                    Log.d("Sound value x", String.valueOf(deltaX));
                    Log.d("Sound value y", String.valueOf(deltaY));
                    Log.d("Sound value z", String.valueOf(deltaZ));

                    RotateAnimation mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    mRotateAnimation.setDuration(200);
                    mRotateAnimation.setFillAfter(true);
                    mCompass.startAnimation(mRotateAnimation);


                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
            last_x = x;
            last_y = y;
            last_z = z;
            isFirstValue = true;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mMagnetometerSensor, SensorManager.SENSOR_DELAY_UI);
        update();

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometerSensor);
        mSensorManager.unregisterListener(this, mMagnetometerSensor);
        Toast toast = Toast.makeText(context, "UnregisterListeners", duration);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
            Toast toast = Toast.makeText(context, "Unbind", duration);
            toast.show();
        }
        Log.v("Pedometer", "service unbound");
    }

    @Override
    public void update() {
        mStepCountPerSec++;
        if (System.currentTimeMillis() - updateTime > 1000) {
            tvPerSec.setText(String.valueOf(mStepCountPerSec) + "/s");
            Log.d("updatetime", String.valueOf(updateTime));
            mStepCountPerSec = 0;
            updateTime = System.currentTimeMillis();

        }

        tvSteps.setText(String.valueOf(mStepsDBHelper.readStepsEntries()));
        Log.d("mStepsDBHelper", String.valueOf(mStepsDBHelper.readStepsEntries()));

    }
}
