package com.nmatte.mood.moodlog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectorFragment extends Fragment {
    final int [] colors = {0xFFFFE0D4,0xFFFFBBA7,0xFFFFE1C8,0xFFFFE0AD,0xFFFFF9B3,0xFFDDEDCF,
            0xFFC8E4B2, 0xFFAEDCB6,0xFFC5E7DB,0xFFB5E3E6,0xFFA3E0F7,0xFFABC2E3,0xFFE0DBEC};
    final String [] labels = {"Severe","","Moderate","","Mild","","Normal","","Mild","","Moderate",
            "","Severe"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int length = (colors.length < labels.length) ? colors.length : labels.length;
        View rootView = inflater.inflate(R.layout.selector_fragment,container,false);
        LinearLayout mainLayout = (LinearLayout) rootView.findViewById(R.id.mainLayout);
        for(int i = 0; i < length; i++) {
            View row = inflater.inflate(R.layout.selector_row, null);
            TextView rowText = (TextView) row.findViewById(R.id.checkedTextView);
            rowText.setText(labels[i]);
            rowText.setBackgroundColor(colors[i]);
            mainLayout.addView(row);
        }
        return mainLayout;

    }

}
