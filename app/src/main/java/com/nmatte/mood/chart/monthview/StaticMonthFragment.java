package com.nmatte.mood.chart.monthview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.database.ChartEntryTableHelper;
import com.nmatte.mood.moodlog.R;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class StaticMonthFragment extends ChartMonthView {

    LinearLayout horizontalList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_static_month,container);
        horizontalList = (LinearLayout) fragmentLayout.findViewById(R.id.horizontalList);

        return fragmentLayout;
    }

    @Override
    public void refreshColumns(DateTime startDate, DateTime endDate) {

        horizontalList.removeAllViews();
        this.startDate = startDate;
        this.endDate = endDate;

        ArrayList<ChartEntry> newList = ChartEntryTableHelper.getGroupWithBlanks(getActivity(), startDate, endDate);


        if (newList.size() > 0) {
            for (final ChartEntry entry : newList) {
                final ChartColumn column = new ChartColumn(getActivity(), entry, numItems, boolItems, ChartColumn.Mode.ENTRY_READ);
                column.setMode(ChartColumn.Mode.ENTRY_READ);
                horizontalList.addView(column);
            }
        }
    }
}
