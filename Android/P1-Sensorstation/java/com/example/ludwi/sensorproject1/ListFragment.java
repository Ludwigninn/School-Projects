package com.example.ludwi.sensorproject1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * ListFragment represents a List. Takes the item that was clicked on and saves it in the controller.
 * Created by Ludwig Ninn on 2017-01-20.
 */
public class ListFragment extends Fragment {

    private ListAdapter adapter;
    private Controller controller;


    public ListFragment()
    {
        // Required empty public constructor
    }

    /**
     * Listens from the listview and sends it forward to the controller class.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView lv = (ListView)view.findViewById(R.id.main_Lista);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new ItemListener());
        return view;

    }
    private class ItemListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SensorHolder sensorHolder =  adapter.getItem(position);
            controller.setItempos(sensorHolder);
            controller.initSensorFragment();

        }
    }

    /**
     * Sets the adapter.
     * @param adapter
     */
    public void setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Sets the controller.
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

}
