package com.nmatte.mood.logbookentries.editentry;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemList;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemList;
import com.nmatte.mood.moodlog.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class EditEntryLayout extends LinearLayout {
    private Context context;
    ChartEntry entry;
    TextCellView dateView;
    MoodList moodList;
    NumItemList numItemList;
    BoolItemList boolItemList;


    public EditEntryLayout(Context context,
                           ChartEntry initialValues,
                           ArrayList<NumItem> numItems,
                           ArrayList<BoolItem> boolItems) {
        super(context);
        this.context = context;
        this.entry = initialValues;
        initWidgets();
        setNumItemList(numItems);
        setBoolItemList(boolItems);
        setEntryValues();
    }


    public EditEntryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initWidgets();
    }


    private void initWidgets() {
        setOrientation(VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout main = (LinearLayout) inflater.inflate(R.layout.layout_edit_single_entry, this).findViewById(R.id.mainLayout);
        dateView = (TextCellView) main.findViewById(R.id.dateHeader);
        moodList = (MoodList) main.findViewById(R.id.moodList);
        numItemList = (NumItemList) main.findViewById(R.id.numItemList);
        boolItemList = (BoolItemList) main.findViewById(R.id.boolItemList);
    }



    public void setBoolItemList(ArrayList<BoolItem> items){
        boolItemList.setItems(items);
    }

    public void setNumItemList(ArrayList<NumItem> items){
        numItemList.setItems(items);
    }

    public void setEntryValues(){
        moodList.setCheckedRows(entry.getMoods());
        numItemList.setItemValues(entry.getNumItems());
        boolItemList.setItemValues(entry.getBoolItems());
        DateTimeFormatter fmt = DateTimeFormat.shortDate();

        if (entry.getLogDate().toLocalDate().getDayOfYear() == DateTime.now().toLocalDate().getDayOfYear())
            dateView.setText("Today");
        else
            dateView.setText(entry.getLogDate().toString(fmt));
    }

    public ChartEntry getEntry(){
        entry.setMoods(moodList.getCheckedItems());
        entry.setBoolItems(boolItemList.getValues());
        entry.setNumItems(numItemList.getValues());
        return entry;
    }
}
