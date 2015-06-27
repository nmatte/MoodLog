package com.nmatte.mood.moodlog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;

import java.util.ArrayList;

public class SelectorLayout extends LinearLayout {
    final int [] colors = {0xFFFFE0D4,0xFFFFBBA7,0xFFFFE1C8,0xFFFFE0AD,0xFFFFF9B3,0xFFDDEDCF,
            0xFFC8E4B2, 0xFFAEDCB6,0xFFC5E7DB,0xFFB5E3E6,0xFFA3E0F7,0xFFABC2E3,0xFFE0DBEC};
    final String [] labels = {"Severe","","Moderate","","Mild","","Normal","","Mild","","Moderate",
            "","Severe"};

    public SelectorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
        int length = (colors.length < labels.length) ? colors.length : labels.length;
        for(int i = 0; i < length; i++) {
            SelectorRow row = new SelectorRow(getContext(),null,labels[i], colors[i]);
            this.addView(row);
        }
    }




    public ArrayList<Boolean> getCheckedItems(){
        ArrayList<Boolean> checkedItems = new ArrayList<>();
        for (int i = 0; i < colors.length; i++){
            SelectorRow row = (SelectorRow) this.getChildAt(i);
            checkedItems.add(row.getValue());
        }
        return checkedItems;
    }

    public void setCheckedItems(LogbookEntry e){
        ArrayList<Boolean> moods = e.getMoods();
        for (int i = 0; i < moods.size(); i++){
            SelectorRow row = (SelectorRow) this.getChildAt(i);
            row.setValue(moods.get(i));
        }
    }

    public LogbookEntry updateEntry (LogbookEntry e){
        e.setMoods(getCheckedItems());
        return e;
    }
}
