package com.nmatte.mood.logbookentries;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.CheckableCellView;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class MoodList extends LinearLayout {
    final boolean isEnabled;


    public MoodList(Context context, AttributeSet attrs) {
        super(context, attrs);
        isEnabled = true;
        init(context);
    }

    public MoodList(Context context, boolean isEnabled, ArrayList<Boolean> values){
        super(context);
        this.isEnabled = isEnabled;
        init(context);
        setCheckedRows(values);
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

    public void setCheckedRows(ArrayList<Boolean> checkedRows){
        for (int i = 0; i < checkedRows.size(); i++) {
            CheckableCellView row = (CheckableCellView) this.getChildAt(i);
            row.setChecked(checkedRows.get(i));
        }
    }

    public static ArrayList<CheckableCellView> getCellViews(Context context, ArrayList<Boolean> cellValues){
        ArrayList<CheckableCellView> result = new ArrayList<>();
        Resources res = context.getResources();
        int [] colors = res.getIntArray(R.array.mood_colors);
        for (int i = 0; i < colors.length; i++){
            CheckableCellView newCell = new CheckableCellView(context,false,colors[i]);
            if (i < cellValues.size())
                newCell.setChecked(cellValues.get(i));
            else
                newCell.setChecked(false);
            result.add(newCell);
        }
        return result;
    }
}
