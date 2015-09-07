package com.nmatte.mood.chart;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Fragment;
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
    FrameLayout backgroundLayout;
    HorizontalScrollView horizontalScrollView;
    boolean editEntryViewIsOpen = false;
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    DateTime startDate;
    DateTime endDate;

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
        View fragmentLayout = inflater.inflate(R.layout.fragment_month_view, null);
        horizontalLayout = (LinearLayout) fragmentLayout.findViewById(R.id.columnLayout);
        editEntryView = (EditEntryLayout) fragmentLayout.findViewById(R.id.editEntryView);
        backgroundLayout = (FrameLayout) fragmentLayout.findViewById(R.id.backgroundLayout);
        horizontalScrollView = (HorizontalScrollView) fragmentLayout.findViewById(R.id.horizontalScrollView);
        numItems = NumItemTableHelper.getAll(getActivity());
        boolItems = BoolItemTableHelper.getAll(getActivity());

        editEntryView.setNumItemList(numItems);
        editEntryView.setBoolItemList(boolItems);

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

                    editEntryView.setEntry(column.getEntry());


                    int [] columnArgs = new int [2];
                    column.getLocationOnScreen(columnArgs);
                    int [] layoutArgs = new int[2];
                    backgroundLayout.getLocationOnScreen(layoutArgs);


                    editEntryView.setX(columnArgs[0] - layoutArgs[0]);


                    if (Build.VERSION.SDK_INT == 21){
                        int cx = editEntryView.getWidth() / 2;
                        cx = 0;
                        int cy = editEntryView.getHeight() / 2;
                        int finalRadius = Math.max(editEntryView.getWidth(), editEntryView.getHeight());
                        Animator animator =
                                ViewAnimationUtils.createCircularReveal(editEntryView, cx, cy, 0, finalRadius);
                        animator.setDuration(700);
                        editEntryView.setVisibility(View.VISIBLE);
                        animator.start();
                    } else {
                        Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.expand);
                        animator.setTarget(editEntryView);
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
                    return true;
                } else
                    return false;
            }
        };
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



