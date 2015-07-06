package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        for (String label : labels){
            mainLayout.addView(new TextCellView(getActivity(), label));
        }

        for (Medication m : MedTableHelper.getMedicationList(getActivity())){
            mainLayout.addView(new TextCellView(getActivity(),m.getName()));
        }

        mainLayout.post(new Runnable() {
            @Override
            public void run() {
                mainLayout.getLayoutParams().width = 250;
            }
        });




        return mainLayout;





    }
}
