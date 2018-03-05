package com.example.ludwi.sensorproject1;


import android.content.Context;
import android.app.Fragment;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 *
 * A simple {@link Fragment} subclass. Inflates the list with textviews that holds the sensor name.
 *  Created by Ludwig Ninn on 2017-01-20.
 */
public class ListAdapter extends ArrayAdapter<SensorHolder> {
    private LayoutInflater inflater;
    private Sensor mSensor;

    public ListAdapter(Context context, SensorHolder[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Inflates the listview.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvSensor;
        ViewHolder holder;
        if (convertView == null) {
            convertView = (LinearLayout) inflater.inflate(R.layout.fragment_list_adapter, parent, false);
             holder = new ViewHolder();
             holder.tvSensor = (TextView) convertView.findViewById(R.id.tvSensor);
             convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        mSensor =this.getItem(position).getSensor();
        holder.tvSensor = (TextView) convertView.findViewById(R.id.tvSensor);
        holder.tvSensor.setText(mSensor.getName());

        return convertView;
    }

    /**
     * Holds the textview that is Initialised from before
     */
    class ViewHolder{
        TextView tvSensor;
    }
}
