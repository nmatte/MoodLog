package com.nmatte.mood.logbookentries;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmatte.mood.medications.MedList;
import com.nmatte.mood.moodlog.CustomNumberPicker;
import com.nmatte.mood.moodlog.R;

public class MainFragment extends Fragment  {
    CustomNumberPicker irrPicker;
    CustomNumberPicker anxPicker;
    CustomNumberPicker hoursPicker;
    MedList medList;
    MoodList moodList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        irrPicker = (CustomNumberPicker) rootView.findViewById(R.id.irrPicker);
        anxPicker = (CustomNumberPicker) rootView.findViewById(R.id.anxPicker);
        hoursPicker = (CustomNumberPicker) rootView.findViewById(R.id.hoursPicker);
        medList = (MedList) rootView.findViewById(R.id.medList);
        moodList = (MoodList) rootView.findViewById(R.id.selectorLayout);
        return rootView;

    }

    public LogbookEntry setEntry(LogbookEntry entry){
        anxPicker.setCurrentNum(entry.getAnxValue());
        irrPicker.setCurrentNum(entry.getIrrValue());
        hoursPicker.setCurrentNum(entry.getHoursSleptValue());
        medList.updateList(getActivity());
        medList.setCheckedMeds(entry.getMedications());
        moodList.setCheckedItems(entry);
        return entry;
    }

    public LogbookEntry getEntry(){
        LogbookEntry entry = new LogbookEntry();
        entry.setAnxValue(anxPicker.getCurrentNum());
        entry.setIrrValue(irrPicker.getCurrentNum());
        entry.setHoursSleptValue(hoursPicker.getCurrentNum());
        entry.setMedications(medList.getCheckedMeds());
        entry.setMoods(moodList.getCheckedItems());
        return entry;

    }


}
