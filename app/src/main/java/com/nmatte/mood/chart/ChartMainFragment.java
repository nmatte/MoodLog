package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.FlexibleLogbookEntry;
import com.nmatte.mood.logbookentries.FlexibleLogbookEntryTableHelper;
import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.LogbookEntryTableHelper;
import com.nmatte.mood.logbookentries.SingleEntryDialog;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class ChartMainFragment extends Fragment {
    ArrayList<LogbookEntry> entryList;
    LinearLayout horizontalLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entryList = new ArrayList<>();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        horizontalLayout = new LinearLayout(getActivity());
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return horizontalLayout;
    }

    public void refreshColumns(Calendar startDate, Calendar endDate) {
        horizontalLayout.removeAllViews();
        horizontalLayout.setClickable(true);
        horizontalLayout.setLongClickable(true);


        ArrayList<FlexibleLogbookEntry> newList = FlexibleLogbookEntryTableHelper.getGroupWithBlanks(getActivity(), startDate, endDate);
        if (newList.size() > 0) {
            ArrayList<NumItem> numItems = NumItemTableHelper.getAll(getActivity());
            ArrayList<BoolItem> boolItems = BoolItemTableHelper.getAll(getActivity());
            for (final FlexibleLogbookEntry entry : newList) {
                final ReadonlyColumn column = new ReadonlyColumn(getActivity(), entry, startDate, numItems, boolItems);
                column.setClickable(true);
                column.setLongClickable(true);
                column.setDuplicateParentStateEnabled(true);
                column.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        SingleEntryDialog dialog = new SingleEntryDialog();
                        Bundle b = new Bundle();
                        b.putParcelable(FlexibleLogbookEntry.PARCEL_TAG,new FlexibleLogbookEntry());
                        dialog.setArguments(b);
                        dialog.show(getFragmentManager(),"Single Entry Dialog");
                        return true;
                    }
                });

                horizontalLayout.addView(column);
            }
        }


    }


}



