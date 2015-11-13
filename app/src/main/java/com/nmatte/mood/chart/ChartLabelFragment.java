package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;


public class ChartLabelFragment extends Fragment {
    ChartColumn labelColumn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        labelColumn = new ChartColumnBuilder()
                .setContext(getActivity())
                .setMode(ChartColumn.Mode.LABEL)
                .setBoolItems(BoolItemTableHelper.getAll(getActivity()))
                .setNumItems(NumItemTableHelper.getAll(getActivity()))
                .build();

        return labelColumn;
    }








    public void refresh(){
        labelColumn = new ChartColumnBuilder()
                .setContext(getActivity())
                .setMode(ChartColumn.Mode.LABEL)
                .setBoolItems(BoolItemTableHelper.getAll(getActivity()))
                .setNumItems(NumItemTableHelper.getAll(getActivity()))
                .build();
    }
}
