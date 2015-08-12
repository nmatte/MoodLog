package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.ChartEntryTableHelper;
import com.nmatte.mood.logbookentries.editentry.CloseEditEntryEvent;
import com.nmatte.mood.logbookentries.editentry.EditEntryLayout;
import com.nmatte.mood.logbookentries.editentry.OpenEditEntryEvent;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

public class ChartMainFragment extends Fragment {
    LinearLayout horizontalLayout;
    boolean editEntryViewIsOpen = false;
    int indexOfOpenEntry = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_month_view, null);
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
            for (final ChartEntry entry : newList) {
                final ReadonlyColumn column = new ReadonlyColumn(getActivity(), entry, startDate, numItems, boolItems);
                column.setClickable(true);
                column.setLongClickable(true);
                column.setDuplicateParentStateEnabled(true);
                column.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!editEntryViewIsOpen) {
                            editEntryViewIsOpen = true;
                            indexOfOpenEntry = horizontalLayout.indexOfChild(column);

                            horizontalLayout.removeView(column);
                            horizontalLayout.addView(new EditEntryLayout(getActivity(), entry, numItems, boolItems), indexOfOpenEntry);

                            EventBus.getDefault().post(new OpenEditEntryEvent());
                            return true;
                        } else
                            return false;
                    }
                });
                horizontalLayout.addView(column);
            }
        }


    }

    public void onEvent(CloseEditEntryEvent event){

    }


}



