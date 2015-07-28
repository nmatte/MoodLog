package com.nmatte.mood.chart;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.R;


public class ChartLabelFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout mainLayout = new LinearLayout(getActivity());
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        int i = 0;
        Resources res = getResources();
        int[] colors = res.getIntArray(R.array.mood_colors);
        String [] moodLabels = res.getStringArray(R.array.mood_labels);
        mainLayout.addView(new TextCellView(getActivity(),"Date"));
        for (String label : moodLabels){
            if (i < colors.length){
                mainLayout.addView(new TextCellView(getActivity(),label,colors[i]));
                i++;
            } else {
                mainLayout.addView(new TextCellView(getActivity(), label));
            }
        }

        for (NumItem numItem : NumItemTableHelper.getAll(getActivity())){
            mainLayout.addView(new TextCellView(getActivity(),numItem.getName()));
        }

        for (BoolItem m : BoolItemTableHelper.getAll(getActivity())){
            mainLayout.addView(new TextCellView(getActivity(),m.getName()));
        }

        return mainLayout;
    }
}
