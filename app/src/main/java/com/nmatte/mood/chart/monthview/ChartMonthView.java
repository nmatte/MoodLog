package com.nmatte.mood.chart.monthview;

import android.app.Fragment;
import android.os.Bundle;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;

import org.joda.time.DateTime;

import java.util.ArrayList;

public abstract class ChartMonthView extends Fragment {
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    DateTime startDate;
    DateTime endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Refreshes the entries to be shown in the specified date range.
     *
     * @param startDate The first day to be shown
     * @param endDate The last day to be shown, inclusive
     */
    abstract public void refreshColumns(DateTime startDate, DateTime endDate);
}
