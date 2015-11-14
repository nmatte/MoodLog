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

    public static ArrayList<CustomNumberPicker> getCells(Context context,
                                                         ArrayList<NumItem> items,
                                                         SimpleArrayMap<NumItem,Integer> itemMap){
        ArrayList<CustomNumberPicker> result = new ArrayList<>();
        for (NumItem item : items){
            CustomNumberPicker numPicker = new CustomNumberPicker(context,item);
            if (itemMap.containsKey(item))
                numPicker.setCurrentNum(itemMap.get(item));
            result.add(numPicker);
        }

        return result;
    }

    public void setItems(ArrayList<NumItem> items){
        removeAllViews();
        for (NumItem item : items){
            CustomNumberPicker numPicker = new CustomNumberPicker(context,item);
            addView(numPicker);
        }
    }

    public void setItemValues(SimpleArrayMap<NumItem,Integer> itemMap){
        for (int i = 0; i < getChildCount(); i++){
            CustomNumberPicker numPicker = (CustomNumberPicker) getChildAt(i);
            if (itemMap.containsKey(numPicker.getNumItem())){
                numPicker.setCurrentNum(itemMap.get(numPicker.getNumItem()));
            }
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
