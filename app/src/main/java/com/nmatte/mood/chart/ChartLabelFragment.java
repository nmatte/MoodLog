package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ChartLabelFragment extends Fragment {
    final String [] labels = {"Date","Severe","","Moderate","","Mild","","Normal","","Mild","","Moderate",
            "","Severe","Anxiety","Irritability","Hours Slept"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ColumnView labelColumn = new ColumnView(getActivity(),labels);
        labelColumn.post(new Runnable() {
            @Override
            public void run() {
                labelColumn.getLayoutParams().width = 250;
            }
        });
        return labelColumn;

    }
}
