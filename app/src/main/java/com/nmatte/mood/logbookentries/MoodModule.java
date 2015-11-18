package com.nmatte.mood.logbookentries;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.monthview.ChartColumn;
import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.chart.cell.CheckboxCellView;
import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.chart.cell.TextCellViewBuilder;
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
                CheckboxCellView row = new CheckboxCellView(context,colors[i], ChartColumn.Mode.ENTRY_EDIT);
                addView(row);
            } else {
                CheckboxCellView row = new CheckboxCellView(context,colors[i], ChartColumn.Mode.ENTRY_READ);
                addView(row);
            }

        }
    }

    public ArrayList<Boolean> getCheckedItems(){
        ArrayList<Boolean> checkedItems = new ArrayList<>();
        for (int i = 0; i < this.getChildCount(); i++){
            CheckboxCellView row = (CheckboxCellView) this.getChildAt(i);
            checkedItems.add(row.isChecked());
        }
        return checkedItems;
    }

    public void setCheckedRows(ArrayList<Boolean> checkedRows){
        for (int i = 0; i < checkedRows.size(); i++) {
            CheckboxCellView row = (CheckboxCellView) this.getChildAt(i);
            row.setChecked(checkedRows.get(i));
        }
    }

    public static ArrayList<CheckboxCellView>
    getCellViews(Context context, ArrayList<Boolean> cellValues, ChartColumn.Mode mode){
        ArrayList<CheckboxCellView> result = new ArrayList<>();
        Resources res = context.getResources();
        int [] colors = res.getIntArray(R.array.mood_colors);
        for (int i = 0; i < colors.length; i++){
            CheckboxCellView newRow = new CheckboxCellView(context,colors[i],mode);
            newRow.setBackground(CellView.Background.VERTICAL);
            if (i < cellValues.size())
                newRow.setChecked(cellValues.get(i));
            else
                newRow.setChecked(false);
            result.add(newRow);
        }
        return result;
    }

    public static ArrayList<TextCellView> getLabelViews(Context context){
        ArrayList<TextCellView> result = new ArrayList<>();
        String [] moodLabels = context.getResources().getStringArray(R.array.mood_labels);
        int[] moodColors = context.getResources().getIntArray(R.array.mood_colors);

        int i = 0;
        for (String label : moodLabels){
            TextCellViewBuilder b = new TextCellViewBuilder(context);
            if (i < moodColors.length){
                result.add(b
                        .setText(label)
                        .setBackgroundColor(moodColors[i])
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                        .build());
                i++;
            } else {
                result.add(b.setText(label).build());
            }
        }
        return result;
    }
}
