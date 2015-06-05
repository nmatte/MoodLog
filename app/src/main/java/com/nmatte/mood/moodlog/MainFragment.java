package com.nmatte.mood.moodlog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.medications.MedList;
import com.nmatte.mood.medications.MedTableHelper;
import com.nmatte.mood.medications.Medication;

import java.util.ArrayList;

public class MainFragment extends Fragment  {
    CustomNumberPicker irrPicker;
    CustomNumberPicker anxPicker;
    CustomNumberPicker hoursPicker;
    MedList medList;
    MedTableHelper MTHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        irrPicker = (CustomNumberPicker) rootView.findViewById(R.id.irrPicker);
        anxPicker = (CustomNumberPicker) rootView.findViewById(R.id.anxPicker);
        hoursPicker = (CustomNumberPicker) rootView.findViewById(R.id.hoursPicker);
        medList = (MedList) rootView.findViewById(R.id.medList);

        MTHelper = new MedTableHelper(getActivity());

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
        ArrayList<Medication> tempList = medList.checkedMedications();
        MTHelper.deleteMedication(name);
        medList.setMedicationList(MTHelper.getMedicationList());
        medList.setChecked(tempList);
    }

    public void addMed (String name){
        ArrayList<Medication> tempList = medList.checkedMedications();
        MTHelper.addMedication(name);
        medList.setMedicationList(MTHelper.getMedicationList());
        medList.setChecked(tempList);
    }


    public void setValues(LogbookEntry e) {
        anxPicker.setCurrentNum(e.getAnxValue());
        irrPicker.setCurrentNum(e.getIrrValue());
        hoursPicker.setCurrentNum(e.getHoursSleptValue());
        medList.setChecked(e.getMedications());
    }
}
