package com.nmatte.mood.views.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nmatte.mood.adapters.EntryAdapter;
import com.nmatte.mood.controllers.chart.ChartEvents;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.columns.ChartColumn;
import com.nmatte.mood.views.chart.columns.EditView;
import com.nmatte.mood.views.chart.columns.ReadView;

import de.greenrobot.event.EventBus;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
import rx.Observable;

public class MonthFragment extends Fragment {
    LinearLayout horizontalLayout;
    RevealFrameLayout backgroundLayout;
    HorizontalScrollView horizontalScrollView;
    EntryAdapter adapter;
    boolean editEntryViewIsOpen = false;

    EditView editEntryColumn;
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
     * Refreshes the entries to be shown.
     *
     */
    public void refreshColumns(Observable<ChartEntry> entries, ModuleConfig config) {
        horizontalLayout.removeAllViews();
        adapter = new EntryAdapter(config);

        entries
                .map(entry -> adapter.getReadView(getActivity(),entry))
                .doOnNext(view -> {
                    view.setOnClickListener(this::beginEditEntry);
                    horizontalLayout.addView(view);
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();

        horizontalLayout.invalidate();
        horizontalScrollView.invalidate();
        backgroundLayout.invalidate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_month, container);
        horizontalLayout = (LinearLayout) fragmentLayout.findViewById(R.id.columnLayout);
        horizontalLayout.setClickable(true);
        horizontalLayout.setLongClickable(true);
        editEntryColumn = (EditView) fragmentLayout.findViewById(R.id.editColumn);
        backgroundLayout = (RevealFrameLayout) fragmentLayout.findViewById(R.id.backgroundLayout);
        horizontalScrollView = (HorizontalScrollView) fragmentLayout.findViewById(R.id.horizontalScrollView);

        return fragmentLayout;
    }

    private void beginEditEntry(View view) {
        Log.i("WTF", "Not this shit again");
        if (view.getClass() == ReadView.class) {
            ReadView readView = (ReadView) view;
            horizontalScrollView.smoothScrollTo(readView.getLeft(), 0);
            horizontalScrollView.setOnTouchListener((v, event) -> true);

            openEditView(readView.getEntry());
            int cy = (int) readView.getLastYtouch();
            animateEditOpen(0, cy);

//            editEntryViewIsOpen = true;
//            ChartEvents.OpenEditEntryEvent e = new ChartEvents.OpenEditEntryEvent();
//            EventBus.getDefault().post(e);
        }
    }

    private void animateEditOpen(int cx, int cy) {
        int finalRadius = Math.max(editEntryColumn.getWidth(), editEntryColumn.getHeight());

        SupportAnimator animator =
                ViewAnimationUtils.createCircularReveal(editEntryColumn, cx, cy, 0, finalRadius);
        animator.setDuration(700);

        editEntryColumn.setVisibility(View.VISIBLE);
        animator.start();
    }

    private void openEditView(ChartEntry entry) {
        editEntryColumn.setEntry(adapter, entry);
    }

    /**
     * Gets the x coordinate of the center of the column.
     * If the left or right side would be clipped by the parent layout,
     * the x should be adjusted to align with parent instead.
     * @param readView The column to find the center of.
     * @return The center x coordinate of the column.
     */
    private int getCenterX(ReadView readView){
        // get absolute position of the column and its parent
        int [] columnArgs = new int [2];
        readView.getLocationOnScreen(columnArgs);
        int [] layoutArgs = new int[2];
        backgroundLayout.getLocationOnScreen(layoutArgs);


        // get column's x coord relative to its parent
        int columnRelativeX = columnArgs[0] - layoutArgs[0];
        // now the relative x coord of the column's center
        int columnCenter = columnRelativeX + (readView.getWidth()/2);

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

    public void onEvent(ChartEvents.CloseEditEntryEvent event){
        try {
            // TODO fix!!
//            ChartEntryTable.addOrUpdateEntry(getActivity(), editEntryColumn.getEntry());
//
//            openedColumn.setEntry(editEntryColumn.getEntry());
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
}
