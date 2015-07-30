package com.nmatte.mood.logbookentries;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmatte.mood.logbookitems.boolitems.BoolItemList;
import com.nmatte.mood.logbookitems.numitems.NumItemList;
import com.nmatte.mood.moodlog.R;

import java.util.Calendar;

public class ChartEntryFragment extends Fragment {
    Calendar date;
    MoodList moodList;
    NumItemList numItemList;
    BoolItemList boolItemList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_entry,container,false);
        moodList = (MoodList) rootView.findViewById(R.id.moodList);
        numItemList = (NumItemList) rootView.findViewById(R.id.numItemList);
        boolItemList = (BoolItemList) rootView.findViewById(R.id.boolItemList);
        return rootView;
    }

    public void setEntry(ChartEntry entry){
        date = entry.getDate();
        moodList.setCheckedRows(entry.getMoods());
        boolItemList.setItems(entry.getBoolItems());
        numItemList.setItems(entry.getNumItems());
    }

    public ChartEntry getEntry(){
        return new ChartEntry(
                date,
                moodList.getCheckedItems(),
                numItemList.getValues(),
                boolItemList.getValues());
    }
}
