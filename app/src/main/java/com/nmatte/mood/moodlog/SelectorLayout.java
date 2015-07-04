package com.nmatte.mood.moodlog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.CheckableCellView;
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
            CheckableCellView row = new CheckableCellView(getContext(),true,colors[i]);
            this.addView(row);
        }
    }

    public ArrayList<Boolean> getCheckedItems(){
        ArrayList<Boolean> checkedItems = new ArrayList<>();
        for (int i = 0; i < colors.length; i++){
            CheckableCellView row = (CheckableCellView) this.getChildAt(i);
            checkedItems.add(row.isChecked());
        }
        return checkedItems;
    }

    public void setCheckedItems(LogbookEntry e){
        ArrayList<Boolean> moods = e.getMoods();
        for (int i = 0; i < moods.size(); i++){
            CheckableCellView row = (CheckableCellView) this.getChildAt(i);
            row.setChecked(moods.get(i));
        }
    }
}
