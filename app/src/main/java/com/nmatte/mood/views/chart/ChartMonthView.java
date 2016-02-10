package com.nmatte.mood.views.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nmatte.mood.controllers.chart.CloseEditEntryEvent;
import com.nmatte.mood.controllers.chart.OpenEditEntryEvent;
import com.nmatte.mood.database.BoolItemTableHelper;
import com.nmatte.mood.database.ChartEntryTableHelper;
import com.nmatte.mood.database.NumItemTableHelper;
import com.nmatte.mood.models.BoolItem;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.NumItem;
import com.nmatte.mood.moodlog.R;

import org.joda.time.DateTime;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;

public class ChartMonthView extends Fragment {
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    DateTime startDate;
    DateTime endDate;

    LinearLayout horizontalLayout;
    RevealFrameLayout backgroundLayout;
    HorizontalScrollView horizontalScrollView;



    boolean editEntryViewIsOpen = false;

    ChartColumn editEntryColumn;
    ChartColumn openedColumn = null;


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


    /**
     * Refreshes the entries to be shown in the specified date range.
     *
     * @param startDate The first day to be shown
     * @param endDate The last day to be shown, inclusive
     */
    public void refreshColumns(DateTime startDate, DateTime endDate) {
        horizontalLayout.removeAllViews();
        numItems = NumItemTableHelper.getAll(getActivity());
        boolItems = BoolItemTableHelper.getAll(getActivity());
        this.startDate = startDate;
        this.endDate = endDate;
        editEntryColumn.refresh(getActivity());

        ArrayList<ChartEntry> newList = ChartEntryTableHelper.getGroupWithBlanks(getActivity(), startDate, endDate);

        if (newList.size() > 0) {
            for (final ChartEntry entry : newList) {
                SelectorWrapper wrapper = new SelectorWrapper(getActivity());
                wrapper.setOnLongClickListener(getColumnLongClickListener(wrapper.getColumn()));
                wrapper.getColumn().refresh(getActivity(), entry, boolItems, numItems);
                horizontalLayout.addView(wrapper);
            }
        }
        horizontalLayout.invalidate();
        horizontalScrollView.invalidate();
        backgroundLayout.invalidate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_month_view, container);
        horizontalLayout = (LinearLayout) fragmentLayout.findViewById(R.id.columnLayout);
        horizontalLayout.setClickable(true);
        horizontalLayout.setLongClickable(true);
        editEntryColumn= (ChartColumn) fragmentLayout.findViewById(R.id.editColumn);
        backgroundLayout = (RevealFrameLayout) fragmentLayout.findViewById(R.id.backgroundLayout);
        horizontalScrollView = (HorizontalScrollView) fragmentLayout.findViewById(R.id.horizontalScrollView);

        return fragmentLayout;
    }

    private View.OnLongClickListener getColumnLongClickListener(final ChartColumn column){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean isTodayOrEarlier =
                        column.getEntry().getLogDate().getDayOfYear() <= DateTime.now().getDayOfYear();
                if (!editEntryViewIsOpen && isTodayOrEarlier)
                    openColumn(column);
                return false;
            }
        };


    }


    private View.OnClickListener getClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    /**
     * Gets the x coordinate of the center of the column.
     * If the left or right side would be clipped by the parent layout,
     * the x should be adjusted to align with parent instead.
     * @param column The column to find the center of.
     * @return The center x coordinate of the column.
     */
    private int getCenterX(ChartColumn column){
        // get absolute position of the column and its parent
        int [] columnArgs = new int [2];
        column.getLocationOnScreen(columnArgs);
        int [] layoutArgs = new int[2];
        backgroundLayout.getLocationOnScreen(layoutArgs);


        // get column's x coord relative to its parent
        int columnRelativeX = columnArgs[0] - layoutArgs[0];
        // now the relative x coord of the column's center
        int columnCenter = columnRelativeX + (column.getWidth()/2);

        // starting from center of original column, subtracting half of the
        // edit view's width from that coordinate gives the x coord for centering it.
        int newX = columnCenter - editEntryColumn.getWidth()/2;
        // newX would be clipped on the left
        if (newX < 0)
            newX = 0;
            // the parent layout's width is the right bound, and newX plus the width is the
            // view's right x coord.
        else if (newX + editEntryColumn.getWidth() > backgroundLayout.getWidth())
            newX = backgroundLayout.getWidth() - editEntryColumn.getWidth();

        return newX;
    }

    public void onEvent(CloseEditEntryEvent event){
        try {
            ChartEntryTableHelper.addOrUpdateEntry(getActivity(), editEntryColumn.getEntry());

            openedColumn.setEntry(editEntryColumn.getEntry());
            openedColumn.refresh(getActivity());
            horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            editEntryColumn.setVisibility(View.INVISIBLE);
            editEntryViewIsOpen = false;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isEditEntryViewOpen() {
        return editEntryViewIsOpen;
    }



    private void openColumn(ChartColumn column){
        editEntryViewIsOpen = true;
        openedColumn = column;
        editEntryColumn.setEntry(column.getEntry());
        editEntryColumn.setNumItems(numItems);
        editEntryColumn.setBoolItems(boolItems);
        editEntryColumn.refresh(getActivity());
        editEntryColumn.setX(getCenterX(column));

        int cx = (int) column.getLastXtouch();
        int cy = (int) column.getLastYtouch();
        int finalRadius = Math.max(editEntryColumn.getWidth(), editEntryColumn.getHeight());
        SupportAnimator animator =
                ViewAnimationUtils.createCircularReveal(editEntryColumn, cx, cy, 0, finalRadius);
        animator.setDuration(700);
        editEntryColumn.setVisibility(View.VISIBLE);
        animator.start();

        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        EventBus.getDefault().post(new OpenEditEntryEvent());

    }


}
