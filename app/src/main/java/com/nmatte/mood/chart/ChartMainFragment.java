package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;

import java.util.ArrayList;

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
        updateEntryList();
    }

    public void updateEntryList(){
        horizontalLayout.removeAllViews();
        if(entryList.size() > 0) {
            int startDate = entryList.get(0).getDate();
            int endDate = entryList.get(entryList.size()-1).getDate();
            int totalDays = endDate + 1 - startDate;
            int entryListIndex = 0;
            for (int i = 0; i < totalDays; i++){
                if(entryListIndex < entryList.size()){
                    LogbookEntry entry = entryList.get(entryListIndex);
                    ReadonlyColumn c = null;
                    if ((entry.getDate() - startDate) > i)
                    {
                        c = new ReadonlyColumn(getActivity(),null,i+1);
                    } else {
                        c = new ReadonlyColumn(getActivity(),entry,i+1);
                    }
                    horizontalLayout.addView(c);
                    entryListIndex++;

                } else{
                    horizontalLayout.addView(new ReadonlyColumn(getActivity(),null,i+1));
                }
            }
        }
    }
}
