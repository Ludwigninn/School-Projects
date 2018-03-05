package com.example.ludwi.sensorproject1;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.List;



    /**
     * Controller that communicates between the classes.
     * Created by Ludwig Ninn on 2017-01-20.
     */

    public class Controller {
        private MainActivity activity;
        private ListFragment listFragment;
        private ListAdapter adapter;
        private SensorHolder sensorHolder;
        private Sensor mSensor;
        private List<Sensor> mSensorsList;
        private SensorManager mSensorManager;

        public Controller(MainActivity activity,List<Sensor> mSensorsList,SensorManager mSensorManager) {
            this.activity=activity;
            this.mSensorManager = mSensorManager;
            SensorHolder[] sensorHolder = new SensorHolder[mSensorsList.size()];
            for (int i = 0; i < mSensorsList.size(); i++) {
                mSensor = mSensorsList.get(i);
                sensorHolder[i] = new SensorHolder(mSensor);

            }
            adapter = new ListAdapter(activity, sensorHolder);
            listFragment = new ListFragment();
            listFragment.setAdapter(adapter);
            listFragment.setController(this);

            activity.setFragment(listFragment, false);
        }

        /**
         * Sets the current sensor that was pressed in the list.
         * @param Sensor
         */
        public void setItempos(SensorHolder Sensor) {
            sensorHolder = Sensor;

        }

        /**
         * Returns the current sensor.
         * @return
         */
        public SensorHolder getItemPos() {
            return sensorHolder;
        }

        /**
         * Initialises the Sensorfragment. Also its values as prefixes and value types.
         */
        public void initSensorFragment(){

            SensorFragment sensorFragment= new SensorFragment();
            sensorFragment.setmSensor(sensorHolder.getSensor());
            sensorFragment.setmSensorManager(mSensorManager);
            sensorFragment.setType(sensorHolder.getValueType());
            sensorFragment.setAxis(sensorHolder.isPrefix());
            activity.setFragment(sensorFragment, true);
        }

    }


