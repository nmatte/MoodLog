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
            for (LogbookEntry e : entryList) {
                final ColumnView c = new ColumnView(getActivity(), e, startDate);


                horizontalLayout.addView(c);
            }
        }
    }
}
