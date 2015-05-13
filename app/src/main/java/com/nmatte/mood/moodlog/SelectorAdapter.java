package com.nmatte.mood.moodlog;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SelectorAdapter extends BaseAdapter {

    String [] labels;
    int [] colors;
    Activity activity;
    CheckBox[] boxes;

    public SelectorAdapter (String [] l, int [] c, Activity a){
        labels = l;
        colors = c;
        activity = a;
        boxes = new CheckBox[c.length];

    }

    @Override
    public int getCount() {
        return labels.length;
    }

    @Override
    public Object getItem(int position) {
        return boxes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String currentLabel = labels[position];
        final int currentColor = colors[position];

        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.selector_row,null);





        final TextView textView = (TextView) rowView.findViewById(R.id.checkedTextView);
        final int originalTextColor = textView.getCurrentTextColor();

        final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
        checkBox.setVisibility(View.INVISIBLE);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setBackgroundColor((checkBox.isChecked()) ? 0xFF000000 : currentColor);
                textView.setTextColor(checkBox.isChecked() ? 0xFFFFFFFF : originalTextColor);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
                textView.setBackgroundColor((checkBox.isChecked()) ? 0xFF000000 : currentColor);
                textView.setTextColor(checkBox.isChecked() ? 0xFFFFFFFF : originalTextColor);
            }
        });

        boxes[position] = checkBox;


        textView.setBackgroundColor(currentColor);
        textView.setText(currentLabel);


        ViewGroup parentParent = (ViewGroup) activity.findViewById(R.id.mainActivityLayout);
        int newHeight = ((parentParent.getMeasuredHeight()
                - parentParent.getPaddingBottom())
                - parentParent.getPaddingTop())/boxes.length;

        newHeight *= (activity
                .getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE)? 8 : 9;
        newHeight /= 10;
        textView.getLayoutParams().height = newHeight;



        notifyDataSetChanged();

        int foo = parent.getMeasuredHeight();
        int bar = parent.getHeight();


        return rowView;
    }

    public int getNumChecked(){
        int result = 0;
        for (int i = 0; i < boxes.length; i++){
            if(boxes[i].isChecked()){
                result++;
            }
        }
        return result;
    }

    /*
    Array of boolean values to be represented as array of indexes with 'true'.
        e.g. {true,false,true} --> {0,2}
    Array must be converted to String to be stored in database.
        e.g. {0,2} --> "0002"
    */

    public String getCheckedItems(){
        String result = "";
        for (int i = 0; i < boxes.length; i++){
            String temp = "";
            if(boxes[i].isChecked()){
                if(i < 10) temp += "0"; // '0' is added to distinguish single-digit numbers.
                temp += i;
            }
            result += temp;
        }

        return result;
    }


}
