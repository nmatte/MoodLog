package com.nmatte.mood.moodlog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.LogbookEntryTableHelper;
import com.nmatte.mood.medications.MedList;
import com.nmatte.mood.medications.MedTableHelper;

public class MainFragment extends Fragment  {
    CustomNumberPicker irrPicker;
    CustomNumberPicker anxPicker;
    CustomNumberPicker hoursPicker;

    TextView debugView;

    MedList medList;
    MedTableHelper MTHelper;
    LogbookEntryTableHelper LEHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        irrPicker = (CustomNumberPicker) rootView.findViewById(R.id.irrPicker);
        anxPicker = (CustomNumberPicker) rootView.findViewById(R.id.anxPicker);
        hoursPicker = (CustomNumberPicker) rootView.findViewById(R.id.hoursPicker);
        medList = (MedList) rootView.findViewById(R.id.medList);
        debugView = (TextView) rootView.findViewById(R.id.debugView);
        debugView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity m = (MainActivity) getActivity();
                LogbookEntry e =  m.getE();
                LEHelper.addOrUpdateEntry(e);
                debugView.setText((CharSequence) e.getSummaryString());
            }
        });


        MTHelper = new MedTableHelper(getActivity());
        LEHelper = new LogbookEntryTableHelper(getActivity());

        View footer = inflater.inflate(R.layout.medication_list_footer,null);
        medList.setFooter(footer);
        medList.setMedicationList(MTHelper.getMedicationList());

        return rootView;

    }

    public LogbookEntry updateEntry(LogbookEntry entry){
        entry.setAnxValue(anxPicker.getCurrentNum());
        entry.setIrrValue(irrPicker.getCurrentNum());
        entry.setHoursSleptValue(hoursPicker.getCurrentNum());
        entry.setMedications(medList.checkedMedications());
        return entry;
    }

    public void deleteMed (String name){
        MTHelper.deleteMedication(name);
        medList.setMedicationList(MTHelper.getMedicationList());
    }

    public void addMed (String name){
        MTHelper.addMedication(name);
        medList.setMedicationList(MTHelper.getMedicationList());
    }

    public void updateDebugView(LogbookEntry e){
        debugView.setText("currentEntry:\n"+e.getSummaryString());

    }



}
