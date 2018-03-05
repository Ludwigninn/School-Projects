package com.example.ludwi.sensorproject1;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;


import java.util.List;

/**
 * Hold the fragmentmanger.
 *Created by Ludwig Ninn on 2017-01-20.
 */
public class MainActivity extends Activity  {
    private SensorManager mSensorManager;
    private List<Sensor> mSensorsList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager    = (android.hardware.SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        Controller controller = new Controller(this,mSensorsList,mSensorManager);
    }


    /**
     * Sets the fragment.
     * @param fragment
     * @param backstack
     */
    public void setFragment(Fragment fragment, boolean backstack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        if (backstack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}
