package com.nmatte.mood.logbookentries;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.CheckableCellView;
import com.nmatte.mood.chart.cell.CheckboxCellView;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class MoodModule extends LinearLayout {
    final boolean isEnabled;


    public MoodModule(Context context, AttributeSet attrs) {
        super(context, attrs);
        isEnabled = true;
        init(context);
    }

    public MoodModule(Context context, boolean isEnabled, ArrayList<Boolean> values){
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
            if (isEnabled){
                CheckableCellView row = new CheckableCellView(context,colors[i]);
                addView(row);
            } else {
                CheckboxCellView row = new CheckboxCellView(context,colors[i]);
                addView(row);
            }

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

    public static ArrayList<CheckboxCellView> getCellViews(Context context, ArrayList<Boolean> cellValues){
        ArrayList<CheckboxCellView> result = new ArrayList<>();
        Resources res = context.getResources();
        int [] colors = res.getIntArray(R.array.mood_colors);
        for (int i = 0; i < colors.length; i++){
            CheckboxCellView newRow = new CheckboxCellView(context,colors[i]);
            if (i < cellValues.size())
                newRow.setChecked(cellValues.get(i));
            else
                newRow.setChecked(false);
            result.add(newRow);
        }
        return result;
    }
}
