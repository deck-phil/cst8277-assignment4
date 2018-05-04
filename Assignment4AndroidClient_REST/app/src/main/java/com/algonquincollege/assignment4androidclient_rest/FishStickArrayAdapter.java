/* File: StuffArrayAdapter.java
 * Author: Stanley Pieda
 * Based strongly on work by Deitel et. al. 2015
 * Reference:
 * Paul Deitel, Harvey Deitel, Alexander Wald. (2015). Android™ 6 for Programmers: An App-Driven Approach, Third Edition
 * Prentice Hall, ISBN: 0-13-428936-6. Chapter 7 WeatherViewer App pp256-285
 */
package com.algonquincollege.assignment4androidclient_rest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by spieda on 2018-03-13.
 */

public class FishStickArrayAdapter extends ArrayAdapter<FishStick> {
    // class for reusing views as list items scroll off and onto the screen
    private static class ViewHolder {
        TextView IDTextView;
        TextView recordNumberTextView;
        TextView omegaTextView;
        TextView lambdaTextView;
        TextView uuidTextView;
    }

    // constructor to initialize superclass inherited members
    public FishStickArrayAdapter(Context context, List<FishStick> stuffs) {
        super(context, -1, stuffs);
    }

    // creates the custom views for the ListView's items
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        try {
            // get Stuff object for this specified ListView position
            FishStick fishStickItem = getItem(position);

            ViewHolder viewHolder; // object that reference's list item's views

            // check for reusable ViewHolder from a ListView item that scrolled
            // offscreen; otherwise, create a new ViewHolder
            if (convertView == null) { // no reusable ViewHolder, so create one
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView =
                        inflater.inflate(R.layout.list_item, parent, false);
                viewHolder.IDTextView =
                        (TextView) convertView.findViewById(R.id.IDTextView);
                viewHolder.recordNumberTextView =
                        (TextView) convertView.findViewById(R.id.recordNumberTextView);
                viewHolder.omegaTextView =
                        (TextView) convertView.findViewById(R.id.omegaTextView);
                convertView.setTag(viewHolder);
                viewHolder.lambdaTextView =
                        (TextView) convertView.findViewById(R.id.lambdaTextView);
                convertView.setTag(viewHolder);
                viewHolder.uuidTextView =
                        (TextView) convertView.findViewById(R.id.uuidTextView);
                convertView.setTag(viewHolder);
            } else { // reuse existing ViewHolder stored as the list item's tag
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // get other data from Stuff object and place into views
            Context context = getContext(); // for loading String resources
            viewHolder.IDTextView.setText(context.getString(
                    R.string.fishStick_id, fishStickItem.id));
            viewHolder.recordNumberTextView.setText(
                    context.getString(R.string.fishStick_recordNumber, fishStickItem.recordNumber));
            viewHolder.omegaTextView.setText(
                    context.getString(R.string.fishStick_omega, fishStickItem.omega));
            viewHolder.lambdaTextView.setText(
                    context.getString(R.string.fishStick_lambda, fishStickItem.lambda));
            viewHolder.uuidTextView.setText(
                    context.getString(R.string.fishStick_uuid, fishStickItem.uuid));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return convertView; // return completed list item to display
    }
}
