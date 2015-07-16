package com.nmatte.mood.logbookentries;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.CheckableCellView;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class MoodList extends LinearLayout {
    final boolean isEnabled;


    public MoodList(Context context, AttributeSet attrs) {
        super(context, attrs);
        isEnabled = true;
        init(context);
    }

    public MoodList(Context context, boolean isEnabled, LogbookEntry e){
        super(context);
        this.isEnabled = isEnabled;
        init(context);
        setCheckedItems(e);
    }

    private void init(Context context){
        this.setOrientation(VERTICAL);
        Resources res = getResources();
        int [] colors = res.getIntArray(R.array.mood_colors);
        for(int i = 0; i < colors.length; i++){
            CheckableCellView row = new CheckableCellView(context,isEnabled,colors[i]);
            this.addView(row);
        }
    }

    public ArrayList<Boolean> getCheckedItems(){
        ArrayList<Boolean> checkedItems = new ArrayList<>();
        for (int i = 0; i < this.getChildCount(); i++){
            CheckableCellView row = (CheckableCellView) this.getChildAt(i);
            checkedItems.add(row.isChecked());
        }
        return checkedItems;
    }

    public void setCheckedItems(LogbookEntry e){
        if (e != null) {
            ArrayList<Boolean> moods = e.getMoods();
            for (int i = 0; i < moods.size(); i++) {
                CheckableCellView row = (CheckableCellView) this.getChildAt(i);
                row.setChecked(moods.get(i));
            }
        }
    }
}
