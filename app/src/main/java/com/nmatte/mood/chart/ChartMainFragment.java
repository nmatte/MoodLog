package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.LogbookEntryTableHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class ChartMainFragment extends Fragment {
    ArrayList<LogbookEntry> entryList;
    LinearLayout horizontalLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entryList = new ArrayList<>();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        horizontalLayout = new LinearLayout(getActivity());
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return horizontalLayout;

    }

    public void setEntryList(ArrayList<LogbookEntry> entryList){
        this.entryList = entryList;
    }

    public void refreshColumns(Calendar startDate, Calendar endDate) {
        horizontalLayout.removeAllViews();
        ArrayList<LogbookEntry> newList = LogbookEntryTableHelper.getGroupWithBlanks(getActivity(),startDate,endDate);
        if (newList.size() > 0) {
            for (LogbookEntry e : newList) {
                horizontalLayout.addView(new ReadonlyColumn(getActivity(), e, startDate));
            }
        }
    }

    private ArrayList<LogbookEntry> entryListWithBlanks(){
        ArrayList<LogbookEntry> result = new ArrayList<>();
        if (entryList.size() == 1){
            result.add(entryList.get(0));
        } else if(entryList.size() > 1) {
            Calendar dateCursor = entryList.get(0).getDate();
            Calendar lastDate = entryList.get(entryList.size() -1).getDate();
            Iterator iterator = entryList.iterator();
            LogbookEntry currentEntry = (LogbookEntry) iterator.next();

            for(; dateCursor.getTimeInMillis() <= lastDate.getTimeInMillis(); dateCursor.roll(Calendar.DAY_OF_YEAR,1)){
                int dayOfYear = currentEntry.getDate().get(Calendar.DAY_OF_YEAR);
                int year = currentEntry.getDate().get(Calendar.YEAR);
                int cursorDay = dateCursor.get(Calendar.DAY_OF_YEAR);
                int cursorYear = dateCursor.get(Calendar.YEAR);

                if (dayOfYear == cursorDay && year == cursorYear){
                    result.add(currentEntry);
                    if (iterator.hasNext())
                        currentEntry = (LogbookEntry) iterator.next();
                } else {
                    result.add(null);
                }
            }
        }
        return result;
    }
}
