package com.nmatte.mood.chart;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.chart.cell.TextCellViewBuilder;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;


public class ChartLabelFragment extends Fragment {
    LinearLayout mainLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainLayout = new LinearLayout(getActivity());
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        refreshView();

        return mainLayout;
    }

    private void refreshView(){
        mainLayout.removeAllViews();
        mainLayout.addView(new TextCellViewBuilder(getActivity()).setText("Date").build());

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(settings.getBoolean(PreferencesContract.LARGE_MOOD_MODULE_ENABLED,true))
            addLargeMoodModule();


        int whiteColor = getResources().getColor(R.color.white);
        int grayColor = getResources().getColor(R.color.gray_cell_bg);

        boolean grayToggle = addNumItems(whiteColor,grayColor,false);
        addBoolItems(whiteColor,grayColor,grayToggle);
    }

    private void addLargeMoodModule(){
        String [] moodLabels = getResources().getStringArray(R.array.mood_labels);
        int[] moodColors = getResources().getIntArray(R.array.mood_colors);

        int i = 0;
        for (String label : moodLabels){
            TextCellViewBuilder b = new TextCellViewBuilder(getActivity());
            if (i < moodColors.length){
                mainLayout.addView(b
                        .setText(label)
                        .setBackgroundColor(moodColors[i])
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                        .build());
                i++;
            } else {
                mainLayout.addView(b.setText(label).build());
            }
        }

    }

    /**
     *
     * @param whiteColor The first color to be used (default white).
     * @param grayColor The second color to be used (default gray).
     * @param grayToggle Whether to start with white or gray; true is gray, false is white.
     * @return The last value of grayToggle, for use in other functions such as addBoolItems().
     */
    private boolean addNumItems(int whiteColor, int grayColor, boolean grayToggle){
        int color;
        for (NumItem numItem : NumItemTableHelper.getAll(getActivity())){
            color = grayToggle ? grayColor : whiteColor;
            grayToggle = !grayToggle;

            TextCellView textView = new TextCellViewBuilder(getActivity())
                    .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                    .setBackgroundColor(color)
                    .setText(numItem.getName()).build();
            mainLayout.addView(textView);
        }
        return grayToggle;
    }

    /**
     *
     * @param whiteColor The first color to be used (default white).
     * @param grayColor The second color to be used (default gray).
     * @param grayToggle Whether to start with white or gray; true is gray, false is white.
     * @return The last value of grayToggle, for use in other functions such as addNumItems().
     */
    private boolean addBoolItems(int whiteColor, int grayColor, boolean grayToggle){
        int color;
        for (BoolItem m : BoolItemTableHelper.getAll(getActivity())){
            color = grayToggle ? grayColor : whiteColor;
            grayToggle = !grayToggle;

            mainLayout.addView(new TextCellViewBuilder(getActivity())
                    .setText(m.getName())
                    .setBackgroundColor(color)
                    .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                    .build());
        }
        return grayToggle;
    }

    public void refresh(){
        refreshView();
    }
}
