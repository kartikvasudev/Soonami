package com.learning.Soonami;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

class EarthquakesListViewAdapter extends BaseAdapter {

    private ArrayList<EarthquakesModel> earthquakesModelArrayList;
    private Context context;

    public EarthquakesListViewAdapter(Context context, ArrayList<EarthquakesModel> earthquakesModelArrayList) {
        this.context = context;
        this.earthquakesModelArrayList = earthquakesModelArrayList;
    }

    @Override
    public int getCount() {
        return earthquakesModelArrayList.size();
    }

    @Override
    public EarthquakesModel getItem(int i) {
        return earthquakesModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_item,viewGroup,false);
        }
        TextView tv_earthquake_distance = (TextView) view.findViewById(R.id.tv_earthquake_place1);
        TextView tv_earthquake_place = (TextView) view.findViewById(R.id.tv_earthquake_place2);
        TextView tv_earthquake_time = (TextView) view.findViewById(R.id.tv_earthquake_time);
        TextView tv_earthquake_magnitude = (TextView) view.findViewById(R.id.tv_earthquake_magnitude);
        TextView tv_earthquake_date = (TextView) view.findViewById(R.id.tv_earthquake_date);

        tv_earthquake_time.setText(getItem(i).getTime());
        tv_earthquake_distance.setText(getItem(i).getDistance());
        tv_earthquake_place.setText(getItem(i).getPlace());
        tv_earthquake_date.setText(getItem(i).getDate());
        tv_earthquake_magnitude.setText(String.valueOf(getItem(i).getMagnitude()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) tv_earthquake_magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(getItem(i).getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return view;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
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
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }
}
