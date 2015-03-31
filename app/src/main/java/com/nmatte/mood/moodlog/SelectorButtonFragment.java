package com.nmatte.mood.moodlog;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nathan on 3/31/2015.
 */
public class SelectorButtonFragment extends Fragment{

    GraphColumnView preview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selector_button_fragment,container,false);
        preview = (GraphColumnView) v.findViewById(R.id.view);
        return v;
    }

    public void setMoodString(String s){
        preview.setMoodString(s);
    }
}
