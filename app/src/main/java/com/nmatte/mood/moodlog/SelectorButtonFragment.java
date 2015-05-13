package com.nmatte.mood.moodlog;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmatte.mood.chart.ColumnView;
import com.nmatte.mood.logbookentries.LogbookEntry;

public class SelectorButtonFragment extends Fragment{

   // GraphColumnView preview;
    ColumnView button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selector_button_fragment,container,false);
       // preview = (GraphColumnView) v.findViewById(R.id.view);
        button = (ColumnView) v.findViewById(R.id.buttonView);
        button.setButtonMode();
        button.updateEntry(new LogbookEntry());
        return v;
    }

    public void updateEntry(LogbookEntry entry){
        button.updateEntry(entry);
    }

  //  public void setMoodString(String s){
    //   preview.setMoodString(s);
   // }
}
