package com.nmatte.mood.logbookitems.boolitems;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmatte.mood.chart.cell.CheckableCellView;
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
            CheckableCellView row = (CheckableCellView) getChildAt(i);
            result.put(row.getBoolItem(),row.isChecked());
        }
        return result;
    }

    public void setItems(ArrayList<BoolItem> items){
        removeAllViews();
        for (BoolItem item : items){
            final CheckableCellView newRow = new CheckableCellView(context);
            newRow.setBoolItem(item);

            newRow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEnabled)
                        newRow.setChecked(!newRow.isChecked());
                }
            });

            addView(newRow);
        }
    }

    public void setItems(SimpleArrayMap<BoolItem,Boolean> itemMap){
        removeAllViews();
        for (int i = 0; i < itemMap.size(); i++){
            BoolItem item = itemMap.keyAt(i);
            final CheckableCellView newRow = new CheckableCellView(context);
            newRow.setBoolItem(item);
            newRow.setChecked(itemMap.get(item));

            newRow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEnabled)
                        newRow.setChecked(!newRow.isChecked());
                }
            });

            addView(newRow);
        }
    }



}
