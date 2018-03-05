package com.example.ludwi.weatherstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Sensorevent listener class. Gets sensor readings from three diffrent sensors Temperature, Pressure and Humidty. It also gets
 * readings from openweather API. Altitude is also calculated from sea level pressure and current pressure.
 * Created by Ludwig Ninn on 01/25/2017
 */
public class MainActivity extends Activity implements SensorEventListener {
    private Function.placeIdTask asyncTask;
    private SensorManager mSensorManager;
    private Sensor mSensorTemperature, mSensorHumidity, mSensorPressure;
    private TextView timestap,cityField, currentTemperature_Field, altitude, humidity_field, pressure_field, temperatureSensorReading, humiditySensorReading, pressureSensorReading;
    public static final String LATITUDE_MALMO = "55.6050";
    public static final String LONGITUDE_MALMO = "13.0038";
    public ArrayList mSelectedItems;
    private String value;
    private float seaLevelAltitude;
    private ImageView mountain,humidity,city,pressure,temperature;
    private SimpleDateFormat format;
    private String DateToStr;
    private Date curDate;
    String key = "13c92f2599c8369dc8f9349cfb7bc62e ";
    String url = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mountain = (ImageView) findViewById(R.id.mountain);
        humidity = (ImageView) findViewById(R.id.humidity);
        city = (ImageView) findViewById(R.id.city);
        pressure = (ImageView) findViewById(R.id.pressure);
        temperature = (ImageView) findViewById(R.id.temperature);
        format = new SimpleDateFormat("hh:mm:ss");

        mSensorManager = (android.hardware.SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        cityField = (TextView) findViewById(R.id.city_field);
        currentTemperature_Field = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        altitude = (TextView) findViewById(R.id.altitude);
        temperatureSensorReading = (TextView) findViewById(R.id.tempSensor);
        humiditySensorReading = (TextView) findViewById(R.id.humiditySensor);
        pressureSensorReading = (TextView) findViewById(R.id.pressureSensor);
        timestap = (TextView) findViewById(R.id.Timestap);


        new AlertDialog.Builder(this)
                .setTitle(R.string.DIALOG_TITLE)
                .setMessage(R.string.DIALOG_MESSAGE)
                .setPositiveButton(R.string.VOLLEY_RETRIVER, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        volleyRequest();
                    }
                })
                .setNegativeButton(R.string.ASYNC_RETRIVER, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Asyncchoice();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


        seaLevelAltitude = mSensorManager.PRESSURE_STANDARD_ATMOSPHERE;

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
            mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == sensorEvent.sensor.TYPE_PRESSURE) {
            value = String.valueOf(Math.round(sensorEvent.values[0]));
            pressureSensorReading.setText("Pressure: " + value + "hpa");
            altitude.setText(String.valueOf("Altitude: "+" "+mSensorManager.getAltitude(seaLevelAltitude, sensorEvent.values[0])));
        }
        if (sensorEvent.sensor.getType() == sensorEvent.sensor.TYPE_AMBIENT_TEMPERATURE) {
            value = String.valueOf(Math.round(sensorEvent.values[0]));
            temperatureSensorReading.setText("Temperature: " + value + "°");
        }
        if (sensorEvent.sensor.getType() == sensorEvent.sensor.TYPE_RELATIVE_HUMIDITY) {
            value = String.valueOf(Math.round(sensorEvent.values[0]));
            humiditySensorReading.setText("Humidity: " + value + "%");
        }
        curDate = new Date(sensorEvent.timestamp / 1000000);
        DateToStr = "Timestamp:" + " " + format.format(curDate);
        timestap.setText(DateToStr);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mSensorHumidity);
        mSensorManager.unregisterListener(this, mSensorTemperature);
        mSensorManager.unregisterListener(this, mSensorPressure);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager = null;
        mSensorTemperature = null;
        mSensorHumidity = null;
        mSensorPressure = null;
    }

    /**
     * Starts the asynctask that calls on the api Openweather.
     */
    public void Asyncchoice() {
        asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_temperature, String weather_humidity, String weather_pressure) {

                String humidity = "Humidity: " + weather_humidity + "\n";
                String Pressure = "Pressure: " + weather_pressure + "\n";
                String Temperature = "Temperature: " + weather_temperature + "\n";
                cityField.setText(weather_city);
                currentTemperature_Field.setText(Temperature);
                humidity_field.setText(humidity);
                pressure_field.setText(Pressure);

            }
        });

        asyncTask.execute(LATITUDE_MALMO, LONGITUDE_MALMO);
    }

    /**
     * Starts the volley that calls on the api Opernweather.
     */
    private void volleyRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String key = "13c92f2599c8369dc8f9349cfb7bc62e ";
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=55.6050&lon=13.0038&APPID=13c92f2599c8369dc8f9349cfb7bc62e&units=metric";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jsonResponse(response);
                        Log.d("JSONTEST", response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    /**
     * Handels the json request from the volley.
     * @param response
     */
    public void jsonResponse(String response) {

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONObject main = jsonObj.getJSONObject("main");
            String city = jsonObj.getString("name").toUpperCase(Locale.US) + ", " + jsonObj.getJSONObject("sys").getString("country");
            String temperature = String.format("%.2f", main.getDouble("temp")) + "°";
            String humidity = main.getString("humidity") + "%";
            String pressure = main.getString("pressure") + " hPa";
            cityField.setText(city);
            currentTemperature_Field.setText("Temperature: " + temperature);
            humidity_field.setText("Humidity: " + humidity);
            pressure_field.setText("Pressure: " + pressure);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
