package com.nmatte.mood.chart;

import android.animation.LayoutTransition;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.ChartEntryTableHelper;
import com.nmatte.mood.logbookentries.EditEntryLayout;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ChartMainFragment extends Fragment {
    LinearLayout horizontalLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_month_view,null);
        horizontalLayout = (LinearLayout) fragmentLayout.findViewById(R.id.columnLayout);

        return fragmentLayout;
    }

    public void refreshColumns(Calendar startDate, Calendar endDate) {
        horizontalLayout.removeAllViews();
        horizontalLayout.setClickable(true);
        horizontalLayout.setLongClickable(true);


        ArrayList<ChartEntry> newList = ChartEntryTableHelper.getGroupWithBlanks(getActivity(), startDate, endDate);
        if (newList.size() > 0) {
            final ArrayList<NumItem> numItems = NumItemTableHelper.getAll(getActivity());
            final ArrayList<BoolItem> boolItems = BoolItemTableHelper.getAll(getActivity());
            ArrayList<String> numItemStrings = new ArrayList<>();
            ArrayList<String> boolItemStrings = new ArrayList<>();
            for (NumItem item : numItems){
                numItemStrings.add(item.toString());
            }
            for (BoolItem item : boolItems){
                boolItemStrings.add(item.toString());
            }
            for (final ChartEntry entry : newList) {
                final ReadonlyColumn column = new ReadonlyColumn(getActivity(), entry, startDate, numItems, boolItems);
                column.setClickable(true);
                column.setLongClickable(true);
                column.setDuplicateParentStateEnabled(true);
                column.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int index = horizontalLayout.indexOfChild(column);


                        horizontalLayout.removeView(column);
                        horizontalLayout.addView(new EditEntryLayout(getActivity(), entry, numItems,boolItems),index);

                        return true;
                    }
                });

                horizontalLayout.addView(column);
            }
        }


    }


}



