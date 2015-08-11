package com.nmatte.mood.logbookentries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemList;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemList;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;
import java.util.Calendar;

public class EditEntryLayout extends LinearLayout {
    private Context context;
    Calendar date;
    TextCellView dateView;
    MoodList moodList;
    NumItemList numItemList;
    BoolItemList boolItemList;


    public EditEntryLayout(Context context,
                           ChartEntry initialValues,
                           ArrayList<NumItem> numItems, ArrayList<BoolItem> boolItems) {
        super(context);
        this.context = context;
        this.date = initialValues.getDate();
        initWidgets();
        setNumItemList(numItems);
        setBoolItemList(boolItems);
        setEntryValues(initialValues);
    }


    public EditEntryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initWidgets();
    }


    private void initWidgets() {

        setOrientation(VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout main = (LinearLayout) inflater.inflate(R.layout.layout_edit_single_entry, null).findViewById(R.id.mainLayout);
        dateView = (TextCellView) main.findViewById(R.id.dateHeader);
        moodList = (MoodList) main.findViewById(R.id.moodList);
        numItemList = (NumItemList) main.findViewById(R.id.numItemList);
        boolItemList = (BoolItemList) main.findViewById(R.id.boolItemList);

        addView(main);


    }



    public void setBoolItemList(ArrayList<BoolItem> items){
        boolItemList.setItems(items);
    }

    public void setNumItemList(ArrayList<NumItem> items){
        numItemList.setItems(items);
    }

    public void setEntryValues(ChartEntry entry){
        dateView.setText("Today");
        moodList.setCheckedRows(entry.getMoods());
        numItemList.setItemValues(entry.getNumItems());
        boolItemList.setItems(entry.getBoolItems());
    }

    public ChartEntry getEntry(){
        return new ChartEntry(date,
                moodList.getCheckedItems(),
                numItemList.getValues(),
                boolItemList.getValues());
    }
}
