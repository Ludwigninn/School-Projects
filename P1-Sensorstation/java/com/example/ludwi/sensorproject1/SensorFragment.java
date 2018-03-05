package com.example.ludwi.sensorproject1;


import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass. Shows the information from the sensor and its readings.
 * Created by Ludwig Ninn on 2017-01-20.
 */
public class SensorFragment extends Fragment implements SensorEventListener {
    private TextView tvInformation;
    private TextView tvTimeStamp;
    private TextView tvValuesText;
    private TextView tvTitleaccuracy;
    private TextView tvValueAccuracy;
    private TextView tvValues;
    private Switch switchReg;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private String type = "";
    private boolean axis;
    private SimpleDateFormat format;
    private String DateToStr;
    private Date curDate;

    public SensorFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        format = new SimpleDateFormat("hh:mm:ss");

        tvValuesText = (TextView) view.findViewById(R.id.tvValuesText);
        tvInformation = (TextView) view.findViewById(R.id.tvInformation);
        tvValues = (TextView) view.findViewById(R.id.tvValues);
        tvTimeStamp = (TextView) view.findViewById(R.id.tvTimeStamp);
        tvTitleaccuracy = (TextView) view.findViewById(R.id.tvTitleaccuracy);
        tvValueAccuracy = (TextView) view.findViewById(R.id.tvValueAccuracy);

        tvInformation.append("\n" + "Name:" + " " + mSensor.getName());
        tvInformation.append("\n" + "Vendor:" + " " + mSensor.getVendor());
        tvInformation.append("\n" + "Version:" + " " + mSensor.getVersion());
        tvInformation.append("\n" + "Power:" + " " + mSensor.getPower());
        tvInformation.append("\n" + "Power:" + " " + mSensor.getResolution());

        switchReg = (Switch) view.findViewById(R.id.switchReg);
        switchReg.setChecked(true);
        switchReg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onResume();
                    Toast.makeText(getActivity(), "Register a Listener", Toast.LENGTH_LONG).show();
                } else {
                    onPause();
                    Toast.makeText(getActivity(), "UnRegister a Listener", Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        try {
            curDate = new Date(event.timestamp / 1000000);

            DateToStr = "Timestamp:" + " " + format.format(curDate);
            tvTimeStamp.setText(DateToStr);
            String value = "";
            if (event.sensor.getType() == mSensor.getType()) {
                for (int i = 0; i < event.values.length; i++) {
                    if(axis==false){
                        value += String.valueOf(event.values[i] + " " + type + "\n");
                    }
                    if (axis && i== 0) {
                        value += String.valueOf("X:" + " " + event.values[i] + " " + type + "\n");
                    }else if (axis && i== 1) {
                        value += String.valueOf("Y:" + " " + event.values[i] + " " + type + "\n");
                    }else if (axis &&  i== 2) {
                        value += String.valueOf("Z:" + " " + event.values[i] + " " + type + "\n");
                    }

                }
            }


            tvValues.setText(value);

        } catch (Exception e)
        {
            Toast.makeText(getActivity(), "Sensor does not exist", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (accuracy == 1) {
            tvValueAccuracy.setText("Low accuracy");
        } else if (accuracy == 2) {
            tvValueAccuracy.setText("Medium accuracy");
        } else if (accuracy == 3) {
            tvValueAccuracy.setText("High accuracy");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager = null;
        mSensor = null;
    }

    /**
     * Sets the sensormanger that was declared in controller for the listview.
     * @param mSensorManager
     */
    public void setmSensorManager(SensorManager mSensorManager) {
        this.mSensorManager = mSensorManager;
    }

    /**
     * Sets the sensor that was clicked in the listview.
     * @param mSensor
     */
    public void setmSensor(Sensor mSensor) {
        this.mSensor = mSensor;
    }

    /**
     * Set the type of the sensor value such as lux or Seconds.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Checks if the sensor has values that needs prefixes such as X,Y,Z.
     * @param axis
     */
    public void setAxis(boolean axis) {
        this.axis = axis;
    }
}
