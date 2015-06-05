package com.nmatte.mood.moodlog;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;

import java.util.ArrayList;

public class SelectorFragment extends Fragment {
    final int [] colors = {0xFFFFE0D4,0xFFFFBBA7,0xFFFFE1C8,0xFFFFE0AD,0xFFFFF9B3,0xFFDDEDCF,
            0xFFC8E4B2, 0xFFAEDCB6,0xFFC5E7DB,0xFFB5E3E6,0xFFA3E0F7,0xFFABC2E3,0xFFE0DBEC};
    final String [] labels = {"Severe","","Moderate","","Mild","","Normal","","Mild","","Moderate",
            "","Severe"};

    LinearLayout mainLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (colors.length != labels.length){
            Log.e("selector fragment","selector fragment color array length doesn't match label length");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int length = (colors.length < labels.length) ? colors.length : labels.length;
        View rootView = inflater.inflate(R.layout.selector_fragment,container,false);
        mainLayout = (LinearLayout) rootView.findViewById(R.id.mainLayout);
        for(int i = 0; i < length; i++) {
            SelectorRow row = new SelectorRow(getActivity(),null,labels[i], colors[i]);
            mainLayout.addView(row);
        }
        return rootView;

    }

    public ArrayList<Boolean> getCheckedItems(){
        ArrayList<Boolean> checkedItems = new ArrayList<>();
        for (int i = 0; i < colors.length; i++){
            SelectorRow row = (SelectorRow) mainLayout.getChildAt(i);
            checkedItems.add(row.getValue());
        }
        return checkedItems;
    }

    public void setCheckedItems(LogbookEntry e){
        ArrayList<Boolean> moods = e.getMoods();
        for (int i = 0; i < moods.size(); i++){
            SelectorRow row = (SelectorRow) mainLayout.getChildAt(i);
            row.setValue(moods.get(i));
        }
    }

    public LogbookEntry updateEntry (LogbookEntry e){
        e.setMoods(getCheckedItems());
        return e;
    }
}
