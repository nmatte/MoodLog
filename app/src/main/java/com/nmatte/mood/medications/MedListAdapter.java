package com.nmatte.mood.medications;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MedListAdapter extends BaseAdapter {
    Medication [] medications;
    Activity activity;

    public MedListAdapter(Medication [] medications, Activity activity){
        this.medications = (medications == null)? new Medication[0] : medications;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return medications.length;
    }

    @Override
    public Object getItem(int position) {
        return medications[position];
    }

    @Override
    public long getItemId(int position) {
        return medications[position].getID();
    }



    public void setMedications(Medication [] medications){
        this.medications = (medications == null)? new Medication[0] : medications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(android.R.layout.simple_list_item_multiple_choice,null);
        TextView label = (TextView) rowView.findViewById(android.R.id.text1);
        label.setText(medications[position].getName());
        return rowView;
    }
}
