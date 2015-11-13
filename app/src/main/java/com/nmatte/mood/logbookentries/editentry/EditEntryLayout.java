package com.nmatte.mood.logbookentries.editentry;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.MoodModule;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemList;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemList;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class EditEntryLayout extends LinearLayout {
    private Context context;
    ChartEntry entry;
    TextCellView dateView;
    MoodModule moodModule;
    NumItemList numItemList;
    BoolItemList boolItemList;


    public EditEntryLayout(Context context,
                           ChartEntry initialValues,
                           ArrayList<NumItem> numItems,
                           ArrayList<BoolItem> boolItems) {
        super(context);
        this.context = context;
        initWidgets();
        setNumItemList(numItems);
        setBoolItemList(boolItems);
        setEntry(initialValues);
    }


    public EditEntryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initWidgets();
    }


    private void initWidgets() {
        setOrientation(VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout main = (LinearLayout) inflater.inflate(R.layout.layout_chartentry_edit, this).findViewById(R.id.mainLayout);
        dateView = (TextCellView) main.findViewById(R.id.dateHeader);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        moodModule = (MoodModule) main.findViewById(R.id.moodList);
        if(!settings.getBoolean(PreferencesContract.LARGE_MOOD_MODULE_ENABLED,true)) {
            moodModule.setVisibility(GONE);
            moodModule = null;

        }
        numItemList = (NumItemList) main.findViewById(R.id.numItemList);
        boolItemList = (BoolItemList) main.findViewById(R.id.boolItemList);
    }



    public void setBoolItemList(ArrayList<BoolItem> items){
        boolItemList.setItems(items);
    }

    public void setNumItemList(ArrayList<NumItem> items){
        numItemList.setItems(items);
    }

    public void setEntry(ChartEntry newEntry){
        this.entry = newEntry;
        if (moodModule != null)
            moodModule.setCheckedRows(entry.getMoods());
        numItemList.setItemValues(entry.getNumItems());
        boolItemList.setItemValues(entry.getBoolItems());
        DateTimeFormatter fmt = DateTimeFormat.shortDate();

        if (entry.getLogDate().toLocalDate().getDayOfYear() == DateTime.now().toLocalDate().getDayOfYear())
            dateView.setText("Today");
        else
            dateView.setText(entry.getLogDate().toString(fmt));
    }

    public ChartEntry getEntry(){
        if (moodModule != null)
            entry.setMoods(moodModule.getCheckedItems());
        entry.setBoolItems(boolItemList.getValues());
        entry.setNumItems(numItemList.getValues());
        return entry;
    }
}
