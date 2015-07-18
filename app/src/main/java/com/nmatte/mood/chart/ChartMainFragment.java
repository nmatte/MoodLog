package com.nmatte.mood.chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.LogbookEntryTableHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class ChartMainFragment extends Fragment {
    ArrayList<LogbookEntry> entryList;
    LinearLayout horizontalLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entryList = new ArrayList<>();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        horizontalLayout = new LinearLayout(getActivity());
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return horizontalLayout;
    }

    public void refreshColumns(Calendar startDate, Calendar endDate) {
        horizontalLayout.removeAllViews();
        horizontalLayout.setClickable(true);
        horizontalLayout.setLongClickable(true);
        horizontalLayout.setDuplicateParentStateEnabled(true);


        ArrayList<LogbookEntry> newList = LogbookEntryTableHelper.getGroupWithBlanks(getActivity(),startDate,endDate);
        if (newList.size() > 0) {
            for (LogbookEntry e : newList) {
                final ReadonlyColumn column = new ReadonlyColumn(getActivity(), e, startDate);
                column.setClickable(true);
                column.setLongClickable(true);
                column.setDuplicateParentStateEnabled(true);
                column.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        Toast.makeText(getActivity(), "long clicked", Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(column.makeIntent());
                        return true;
                    }
                });
                horizontalLayout.addView(column);
            }
        }


        }

    class LongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (view instanceof ReadonlyColumn){
                ReadonlyColumn column = (ReadonlyColumn) view;
                startActivity(column.makeIntent());
                return true;
            } else {
                return false;
            }
        }



    }
    }



