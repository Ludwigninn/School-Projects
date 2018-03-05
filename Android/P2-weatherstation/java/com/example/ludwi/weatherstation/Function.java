package com.example.ludwi.weatherstation;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Gets API request from Openweather. Takes out values from the JSON object and sends it to the MainAcitivty.
 * Created by Ludwig Ninn on 01/25/2017
 */
public class Function {


    private static final String OPEN_WEATHER_MAP_URL =
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";

    private static final String OPEN_WEATHER_MAP_API = "13c92f2599c8369dc8f9349cfb7bc62e";





    public interface AsyncResponse {

        void processFinish(String output1, String output2, String output3, String output4);
    }

    /**
     * Gets the json from the api.
     */
    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;//Call back interface

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonWeather = null;
            try {
                jsonWeather = getWeatherJSON(params[0], params[1]);
            } catch (Exception e) {
                Log.d("Error", "Cannot process JSON results", e);
            }


            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if(json != null){

                    JSONObject main = json.getJSONObject("main");
                    String city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
                    String temperature = String.format("%.2f", main.getDouble("temp"))+ "°";
                    String humidity = main.getString("humidity") + "%";
                    String pressure = main.getString("pressure") + " hPa";

                    delegate.processFinish(city, temperature, humidity, pressure);

                }
            } catch (JSONException e) {
                //Log.e(LOG_TAG, "Cannot process JSON results", e);
            }



        }
    }


    /**
     * Handeles the Json from the api.
     * @param lat
     * @param lon
     * @return
     */
    public static JSONObject getWeatherJSON(String lat, String lon){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, lat, lon));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }




}