package com.nmatte.mood.logbookitems.boolitems;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.monthview.ChartColumn;
import com.nmatte.mood.chart.cell.CheckboxCellView;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class BoolItemList extends LinearLayout {
    Context context;
    final private boolean chartStyle;
    final private boolean isEnabled;

    public BoolItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.BoolItemList,0,0);
        this.chartStyle = a.getBoolean(R.styleable.BoolItemList_style_chart,false);
        this.isEnabled = a.getBoolean(R.styleable.BoolItemList_is_enabled,true);
        a.recycle();
        init(context);
    }

    public BoolItemList(Context context, boolean chartStyle){
        super(context);
        this.chartStyle = chartStyle;
        this.isEnabled = true;
        init(context);
    }

    public BoolItemList(Context context, boolean chartStyle, boolean isEnabled){
        super(context);
        this.chartStyle = chartStyle;
        this.isEnabled = isEnabled;
        init(context);
    }
    private void init(Context context){
        this.setOrientation(VERTICAL);
        this.context = context;
        this.setClickable(isEnabled);
        this.setLongClickable(isEnabled);
    }

    public SimpleArrayMap<BoolItem,Boolean> getValues(){
        SimpleArrayMap<BoolItem,Boolean> result = new SimpleArrayMap<>();
        for (int i = 0; i < getChildCount(); i++){
            CheckboxCellView row = (CheckboxCellView) getChildAt(i);
            result.put(row.getBoolItem(),row.isChecked());
        }
        return result;
    }

    public void setItems(ArrayList<BoolItem> items){
        removeAllViews();
        for (BoolItem item : items){
            CheckboxCellView checkBox = new CheckboxCellView(context, ChartColumn.Mode.ENTRY_EDIT);
            checkBox.setBoolItem(item);
            addView(checkBox);
        }
    }

    public void setItemValues(SimpleArrayMap<BoolItem,Boolean> itemMap){
        for (int i = 0; i < getChildCount(); i++){
            final CheckboxCellView checkBox = (CheckboxCellView) getChildAt(i);
            if (itemMap.containsKey(checkBox.getBoolItem())){
                checkBox.setChecked(itemMap.get(checkBox.getBoolItem()));

                checkBox.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isEnabled)
                            checkBox.setChecked(!checkBox.isChecked());
                    }
                });
            }
        }
    }



}
