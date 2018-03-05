package com.example.ludwi.sensorproject1;


import android.hardware.Sensor;

/**
 * Sensorholder holds a sensor with the prefix and suffix for the sensortype.
 * Created by Ludwig Ninn on 2017-01-18.
 */
public class SensorHolder {
    private Sensor mSensor;
    private String valueType="";
    private boolean prefix;

    public SensorHolder(Sensor mSensorHolder) {
        this.mSensor = mSensorHolder;
        prefix=false;
        typeValue();

    }

    /**
     * Returns current sensor
     * @return
     */
    public Sensor getSensor() {
        return mSensor;
    }

    /**
     * Returns a boolean that dictates if the sensor should hade a prefix.
     * @return
     */
    public boolean isPrefix() {
        return prefix;
    }

    /**
     * Sets the sensor.
     * @param mSensorHolder
     */
    public void setSensor(Sensor mSensorHolder) {
        this.mSensor = mSensorHolder;
    }

    /**
     * Set the suffix of the sensor.
     */
    public void typeValue() {


        int value = mSensor.getType();
        switch (value) {
            case Sensor.TYPE_ACCELEROMETER:
                valueType = "m/s2";
                prefix=true;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                prefix=true;

                break;
            case Sensor.TYPE_GYROSCOPE:
                valueType = "rad/s";
                prefix=true;
                break;
            case Sensor.TYPE_PRESSURE:
                valueType = "mbar";
                prefix=false;
                break;
            case Sensor.TYPE_PROXIMITY:
                prefix=false;
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                valueType = "%";
                prefix=false;
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                valueType = "°C";
                prefix=false;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                prefix=true;
                break;
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                prefix=false;
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                prefix=false;
                break;
            case Sensor.TYPE_STEP_COUNTER:
                prefix=false;
                break;
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                valueType = "rad/s";
                prefix=true;
                break;
            case Sensor.TYPE_ORIENTATION:
                prefix=false;
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                prefix=true;
                break;
            case Sensor.TYPE_GRAVITY:
                valueType = "m/s2";
                prefix=true;
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                valueType = "m/s2";
                prefix=true;
                break;


        }
    }

    /**
     * Returns the suffix of the sensor.
     * @return
     */
    public String getValueType() {

        return valueType;
    }

}


