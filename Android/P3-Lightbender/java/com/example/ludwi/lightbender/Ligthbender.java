package com.example.ludwi.lightbender;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * Lightbender changes the brightness of your screen. Depending on settings made by the user it can change it brightness level
 * to match the current lux value sensed by the phone.
 * Created by Ludwig Ninn on 01/27/2017
 */
public class Ligthbender extends AppCompatActivity implements SensorEventListener {

    private float CURRENT_BRIGHTNESS_LEVEL = 0;
    private SensorManager mSensorManager;
    private CameraManager mCameraManager;
    private boolean isSensorPresentProximity, isFlashLightOn, isSensorPresentLight;
    private boolean isSystemSettingsTemporay = true;
    private Sensor mProximity_Sensor, mLight_Sensor;
    private String mCameraID;
    private CameraCharacteristics mParameters;
    private float distanceFromPhone;
    private ContentResolver mContentResolver;
    private Window mWindow;
    private float mBrightness;
    private TextView flux_level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flux_level = (TextView) findViewById(R.id.flux_level);

        initSensors();

    }

    /**
     * Swtich case for the current radiobutton clicked. Sets the max brightness value.
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.STARLIGHT:
                if (checked)

                    CURRENT_BRIGHTNESS_LEVEL = 1;

                Log.d("STARLIGHT", "STARLIGHT");
                break;
            case R.id.TWILIGHT:
                if (checked)
                    CURRENT_BRIGHTNESS_LEVEL = 10;

                Log.d("TWILIGHT", "TWILIGHT");
                break;
            case R.id.SUNRISE:
                if (checked)
                    CURRENT_BRIGHTNESS_LEVEL = 20;

                Log.d("SUNRISE", "SUNRISE");
                break;
            case R.id.OVERCAST:
                if (checked)
                    CURRENT_BRIGHTNESS_LEVEL = 50;

                Log.d("OVERCAST", "OVERCAST");
                break;
            case R.id.HighNoon:
                if (checked)
                    CURRENT_BRIGHTNESS_LEVEL = 100;

                Log.d("HighNoon", "HighNoon");
                break;
            case R.id.systemSettings:
                if (checked)
                    isSystemSettingsTemporay=false;

                Log.d("HighNoon", "HighNoon");
                break;
            case R.id.tempsyst:
                if (checked)
                    isSystemSettingsTemporay=true;

                Log.d("HighNoon", "HighNoon");
                break;
            case R.id.brightnessnormal:
                if (checked)
                    CURRENT_BRIGHTNESS_LEVEL = 0;

                Log.d("HighNoon", "HighNoon");
                break;
        }
    }

    /**
     * Initialised the sensors
     */
    public void initSensors() {
        initScreenBrightness();
        initCameraFlashLight();
        mSensorManager = (android.hardware.SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {

            mLight_Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            isSensorPresentLight = true;
        } else {
            isSensorPresentLight = false;
        }


        mSensorManager = (SensorManager) this.getSystemService
                (Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
                != null) {
            mProximity_Sensor = mSensorManager.getDefaultSensor
                    (Sensor.TYPE_PROXIMITY);
            isSensorPresentProximity = true;
        } else {
            isSensorPresentProximity = false;
        }
    }

    /**
     * Initialised the Camera
     */
    public void initCameraFlashLight() {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {

            mCameraID = mCameraManager.getCameraIdList()[0];
            mParameters = mCameraManager.getCameraCharacteristics(mCameraID);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == event.sensor.TYPE_LIGHT) {

            float light = event.values[0];
            flux_level.setText(String.valueOf(light));

            if (light > 0 && light < 1000 && CURRENT_BRIGHTNESS_LEVEL != 0) {
                if (light > CURRENT_BRIGHTNESS_LEVEL) {
                    light = CURRENT_BRIGHTNESS_LEVEL;
                }
                    changeScreenBrightness(1 / light);
            }else  if (light > 0 && light < 1000) {
                changeScreenBrightness(1 / light);
            }
        }

        if (event.sensor.getType() == event.sensor.TYPE_PROXIMITY) {
            distanceFromPhone = event.values[0];
            if (distanceFromPhone < mProximity_Sensor.getMaximumRange()) {
                if (!isFlashLightOn) {
                    turnTorchLightOn();
                }
            } else {
                if (isFlashLightOn) {
                    turnTorchLightOff();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSensorPresentLight) {
            mSensorManager.registerListener(this, mLight_Sensor, SensorManager.SENSOR_DELAY_NORMAL);

        }
        if (isSensorPresentProximity) {
            mSensorManager.registerListener(this, mProximity_Sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        if (isSensorPresentLight) {
            mSensorManager.unregisterListener(this, mLight_Sensor);
        }

        if (isSensorPresentProximity) {
            mSensorManager.unregisterListener(this);
            mSensorManager.unregisterListener(this, mProximity_Sensor);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager = null;
        mProximity_Sensor = null;
        mLight_Sensor = null;
    }

    /**
     * Turns the torchlight on.
     */
    public void turnTorchLightOn() {

            try {
                if (mParameters.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)!=null) {
                    mCameraManager.setTorchMode(mCameraID, true);
                }
            } catch (CameraAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
            isFlashLightOn = true;
        }

    /**
     * Turns the torchlight off.
     */
    public void turnTorchLightOff() {
        try {
            mCameraManager.setTorchMode(mCameraID, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        isFlashLightOn = false;
    }

    /**
     * Initialise getContentResolver and window.
     */
    public void initScreenBrightness() {
        mContentResolver = getContentResolver();
        mWindow = getWindow();
    }

    /**
     *
     * @param brightness
     */
    public void changeScreenBrightness(float brightness) {


        mBrightness =  brightness * 255;

        if (isSystemSettingsTemporay) {
            WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
            mLayoutParams.screenBrightness = brightness;
            mWindow.setAttributes(mLayoutParams);
        } else {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                startActivity(intent);
            } else {
                Log.d("Settings System", "SYSTEM");
                WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
                mLayoutParams.screenBrightness = brightness;
                mWindow.setAttributes(mLayoutParams);
                        Settings.System.putInt(mContentResolver, Settings.System.SCREEN_BRIGHTNESS, (int) mBrightness);
            }
        }
    }


}
