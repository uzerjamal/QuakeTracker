package com.uzerjamal.quaketracker;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class EarthquakeListAdapter extends ArrayAdapter<EarthquakeList> {

    public EarthquakeListAdapter(Activity context, ArrayList<EarthquakeList> earthquakelist){
        super(context,0,earthquakelist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        EarthquakeList currentEarthquakeList = getItem(position);

        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude_view);
        magnitudeView.setText(currentEarthquakeList.GetMagnitude() + "");

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquakeList.GetMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        TextView nearbyView = (TextView) listItemView.findViewById(R.id.nearby_view);
        nearbyView.setText(currentEarthquakeList.GetNearBy());

        TextView locationView = (TextView) listItemView.findViewById(R.id.location_view);
        locationView.setText(currentEarthquakeList.GetLocation());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date_view);
        dateView.setText(currentEarthquakeList.GetDate());

        TextView timeView = (TextView) listItemView.findViewById(R.id.time_view);
        timeView.setText(currentEarthquakeList.GetTime());

        return listItemView;
    }


    private int getMagnitudeColor(String Magnitude){
        int magnitudeColorResourceId=0;
        float magnitudeF = Float.parseFloat(Magnitude);
        int magnitude = Math.round(magnitudeF);
        switch(magnitude){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 10:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }
}
