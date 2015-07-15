package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.medications.MedTableHelper;
import com.nmatte.mood.medications.Medication;


public class ChartLabelFragment extends Fragment {
    final String [] labels = {"Date","Severe","","Moderate","","Mild","","Normal","","Mild","","Moderate",
            "","Severe","Anxiety","Irritability","Hours Slept"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout mainLayout = new LinearLayout(getActivity());
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        // TODO fix this mess
        int i = 0;
        for (String label : labels){
            if (i < MoodList.colors.length && !label.equals(labels[0])){
                mainLayout.addView(new TextCellView(getActivity(),label,MoodList.colors[i]));
                i++;
            } else {
                mainLayout.addView(new TextCellView(getActivity(), label));
            }
        }

        for (Medication m : MedTableHelper.getMedicationList(getActivity())){
            mainLayout.addView(new TextCellView(getActivity(),m.getName()));
        }


        return mainLayout;
    }
}
