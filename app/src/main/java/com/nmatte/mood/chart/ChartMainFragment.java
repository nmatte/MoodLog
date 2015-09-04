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

import org.joda.time.DateTime;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ChartMainFragment extends Fragment {
    LinearLayout horizontalLayout;
    boolean editEntryViewIsOpen = false;
    int indexOfOpenEntry = 0;
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    DateTime startDate;
    DateTime endDate;

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

        numItems = NumItemTableHelper.getAll(getActivity());
        boolItems = BoolItemTableHelper.getAll(getActivity());

        return fragmentLayout;
    }

    public void refreshColumns(DateTime startDate, DateTime endDate) {
        horizontalLayout.removeAllViews();
        horizontalLayout.setClickable(true);
        horizontalLayout.setLongClickable(true);

        this.startDate = startDate;
        this.endDate = endDate;

        ArrayList<ChartEntry> newList = ChartEntryTableHelper.getGroupWithBlanks(getActivity(), startDate, endDate);
        if (newList.size() > 0) {
            for (final ChartEntry entry : newList) {
                final ReadonlyColumn column = new ReadonlyColumn(getActivity(), entry, numItems, boolItems);
                column.setDuplicateParentStateEnabled(true);
                column.setOnLongClickListener(getColumnLongClickListener(column));
                horizontalLayout.addView(column);
            }
        }
    }

    private View.OnLongClickListener getColumnLongClickListener(final ReadonlyColumn column){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!editEntryViewIsOpen) {
                    editEntryViewIsOpen = true;
                    indexOfOpenEntry = horizontalLayout.indexOfChild(column);
                    horizontalLayout.removeView(column);
                    horizontalLayout.addView(new EditEntryLayout(getActivity(), column.getEntry(), numItems, boolItems), indexOfOpenEntry);
                    EventBus.getDefault().post(new OpenEditEntryEvent());
                    return true;
                } else
                    return false;
            }
        };
    }

    public void onEvent(CloseEditEntryEvent event){
        try {
            EditEntryLayout editEntryLayout = (EditEntryLayout) horizontalLayout.getChildAt(indexOfOpenEntry);
            ReadonlyColumn newColumn = new ReadonlyColumn(
                    getActivity(),
                    editEntryLayout.getEntry(),
                    numItems,
                    boolItems
                    );
            ChartEntryTableHelper.addOrUpdateEntry(getActivity(),editEntryLayout.getEntry());
            newColumn.setOnLongClickListener(getColumnLongClickListener(newColumn));
            horizontalLayout.removeView(editEntryLayout);
            horizontalLayout.addView(newColumn,indexOfOpenEntry);
            editEntryViewIsOpen = false;
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}



