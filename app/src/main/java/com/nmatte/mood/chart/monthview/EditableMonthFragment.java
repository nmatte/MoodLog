package com.nmatte.mood.chart.monthview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.ChartColumn;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.database.ChartEntryTableHelper;
import com.nmatte.mood.logbookentries.editentry.CloseEditEntryEvent;
import com.nmatte.mood.logbookentries.editentry.EditEntryLayout;
import com.nmatte.mood.logbookentries.editentry.OpenEditEntryEvent;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.R;

import org.joda.time.DateTime;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class EditableMonthFragment extends ChartMonthView {


    LinearLayout horizontalLayout;
    FrameLayout backgroundLayout;
    HorizontalScrollView horizontalScrollView;
    boolean editEntryViewIsOpen = false;

    EditEntryLayout editEntryView;

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
        View fragmentLayout = inflater.inflate(R.layout.fragment_month_view, container);
        horizontalLayout = (LinearLayout) fragmentLayout.findViewById(R.id.columnLayout);
        horizontalLayout.setClickable(true);
        horizontalLayout.setLongClickable(true);
        editEntryView = (EditEntryLayout) fragmentLayout.findViewById(R.id.editEntryView);
        backgroundLayout = (FrameLayout) fragmentLayout.findViewById(R.id.backgroundLayout);
        horizontalScrollView = (HorizontalScrollView) fragmentLayout.findViewById(R.id.horizontalScrollView);




        return fragmentLayout;
    }


    public void refreshColumns(DateTime startDate, DateTime endDate) {
        horizontalLayout.removeAllViews();
        numItems = NumItemTableHelper.getAll(getActivity());
        boolItems = BoolItemTableHelper.getAll(getActivity());
        editEntryView.setNumItemList(numItems);
        editEntryView.setBoolItemList(boolItems);
        this.startDate = startDate;
        this.endDate = endDate;

        ArrayList<ChartEntry> newList = ChartEntryTableHelper.getGroupWithBlanks(getActivity(), startDate, endDate);

        if (newList.size() > 0) {
            for (final ChartEntry entry : newList) {
                final ChartColumn column = new ChartColumn(getActivity(), entry, numItems, boolItems);
                column.setDuplicateParentStateEnabled(true);
                column.setOnLongClickListener(getColumnLongClickListener(column));
                horizontalLayout.addView(column);
            }
        }
        horizontalLayout.invalidate();
        horizontalScrollView.invalidate();
        backgroundLayout.invalidate();
    }

    private View.OnLongClickListener getColumnLongClickListener(final ChartColumn column){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean isTodayOrEarlier =
                        column.getEntry().getLogDate().getDayOfYear() <= DateTime.now().getDayOfYear();
                if (!editEntryViewIsOpen && isTodayOrEarlier) {
                    openColumn(column);
                    return true;
                } else
                    return false;
            }
        };
    }

    private void openColumn(ChartColumn column){
        editEntryViewIsOpen = true;

        editEntryView.setEntry(column.getEntry());
        editEntryView.setX(getCenterX(column));


        // animate opening the layout
        if (Build.VERSION.SDK_INT >= 21){
            int cx = (int) column.getLastXtouch();
            int cy = (int) column.getLastYtouch();
            int finalRadius = Math.max(editEntryView.getWidth(), editEntryView.getHeight());
            Animator animator =
                    ViewAnimationUtils.createCircularReveal(editEntryView, cx, cy, 0, finalRadius);
            animator.setDuration(700);
            editEntryView.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.expand);
            animator.setTarget(editEntryView);
            //editEntryView.setPivotX(column.getLastXtouch());
            editEntryView.setVisibility(View.VISIBLE);
            animator.start();
        }


        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        addShadows(0);

        EventBus.getDefault().post(new OpenEditEntryEvent());

    }

    private int getCenterX(ChartColumn column){
        /*
                    find appropriate x coord for editEntryView:
                    prefer centering on the center of the column to be edited.
                    However, if the left or right side would be clipped by the parent layout,
                    the x should be adjusted to align with parent instead.
                     */

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
        int newX = columnCenter - editEntryView.getWidth()/2;
        // newX would be clipped on the left
        if (newX < 0)
            newX = 0;
            // the parent layout's width is the right bound, and newX plus the width is the
            // view's right x coord.
        else if (newX + editEntryView.getWidth() > backgroundLayout.getWidth())
            newX = backgroundLayout.getWidth() - editEntryView.getWidth();

        return newX;
    }

    private void addShadows(long duration){
        /*
        for(int i = 0; i < horizontalLayout.getChildCount(); i++){
            if (i != indexOfOpenEntry){
                Animator anim = AnimatorInflater.loadAnimator(getActivity(),R.animator.fade_out);
                anim.setTarget(horizontalLayout.getChildAt(i));
                anim.setStartDelay(duration);
                anim.start();
            }
        }
*/
        Animator anim = AnimatorInflater.loadAnimator(getActivity(),R.animator.fade_out);
        anim.setTarget(horizontalLayout);
        anim.setStartDelay(duration);
        anim.start();
    }

    private void clearShadows(long duration){
        /*
        for(int i = 0; i < horizontalLayout.getChildCount(); i++){
            if (i != indexOfOpenEntry){
                Animator anim = AnimatorInflater.loadAnimator(getActivity(),R.animator.fade_in);
                anim.setTarget(horizontalLayout.getChildAt(i));
                anim.setStartDelay(duration);
                anim.start();
            }
        }*/
        Animator anim = AnimatorInflater.loadAnimator(getActivity(),R.animator.fade_in);
        anim.setTarget(horizontalLayout);
        anim.setStartDelay(duration);
        anim.start();
    }

    public void onEvent(CloseEditEntryEvent event){
        try {
            ChartEntryTableHelper.addOrUpdateEntry(getActivity(), editEntryView.getEntry());

            horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            clearShadows(0);
            editEntryView.setVisibility(View.INVISIBLE);
            editEntryViewIsOpen = false;
        } catch (Exception e){
            e.printStackTrace();
        }


    }


}



