package com.nmatte.mood.logbookitems.numitems;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nmatte.mood.moodlog.CustomNumberPicker;

import java.util.ArrayList;

public class NumItemList extends LinearLayout {
    Context context;

    public NumItemList(Context context){
        super(context);
        this.context = context;
        init();
    }

    public NumItemList(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context = context;
        init();
    }

    private void init(){
        setOrientation(VERTICAL);
        setClickable(true);
    }

    public void setItems(ArrayList<NumItem> items){
        removeAllViews();
        for (NumItem item : items){
            CustomNumberPicker numPicker = new CustomNumberPicker(context,item);
            addView(numPicker);
        }
    }

    public void setItems(SimpleArrayMap<NumItem,Integer> itemMap){
        removeAllViews();
        for (int i = 0; i < itemMap.size(); i++){
            CustomNumberPicker numPicker = new CustomNumberPicker(context,itemMap.keyAt(i));
            numPicker.setCurrentNum(itemMap.valueAt(i));
            addView(numPicker);
        }
    }

    public SimpleArrayMap<NumItem,Integer> getValues(){
        SimpleArrayMap<NumItem,Integer> result = new SimpleArrayMap<>();
        for (int i = 0; i < getChildCount(); i++){
            CustomNumberPicker numPicker = (CustomNumberPicker) getChildAt(i);
            result.put(numPicker.getNumItem(),numPicker.getCurrentNum());
        }
        return result;
    }
}
