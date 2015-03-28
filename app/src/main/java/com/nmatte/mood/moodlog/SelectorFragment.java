package com.nmatte.mood.moodlog;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by Nathan on 3/27/2015.
 */
public class SelectorFragment extends ListFragment {
    final int [] colors = {0xFFFFE0D4,0xFFFFBBA7,0xFFFFE1C8,0xFFFFE0AD,0xFFFFF9B3,0xFFDDEDCF,
            0xFFC8E4B2, 0xFFAEDCB6,0xFFC5E7DB,0xFFB5E3E6,0xFFA3E0F7,0xFFABC2E3,0xFFE0DBEC};
    final String [] labels = {"Severe","","Moderate","","Mild","","Normal","","Mild","","Moderate",
            "","Severe"};

    SelectorAdapter adapter;
    String resultData  = "";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new SelectorAdapter(labels,colors,getActivity());
        this.setListAdapter(adapter);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

}
