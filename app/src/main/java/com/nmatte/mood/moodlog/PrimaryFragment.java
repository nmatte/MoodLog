package com.nmatte.mood.moodlog;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nmatte.mood.medications.MedListAdapter;
import com.nmatte.mood.medications.MedTableHelper;

public class PrimaryFragment extends Fragment {
    CustomNumberPicker irrPicker;
    CustomNumberPicker anxPicker;
    CustomNumberPicker hoursPicker;
    ListView listView;

    MedListAdapter medAdapter;
    MedTableHelper MTHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        irrPicker = (CustomNumberPicker) rootView.findViewById(R.id.irrPicker);
        anxPicker = (CustomNumberPicker) rootView.findViewById(R.id.anxPicker);
        hoursPicker = (CustomNumberPicker) rootView.findViewById(R.id.hoursPicker);
        listView = (ListView) rootView.findViewById(R.id.listView);

        MTHelper = new MedTableHelper(getActivity());

        // Listener for long press on an item in the medication list (to delete)
        AdapterView.OnItemLongClickListener l = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
       //         deleteMedAtPosition(position);
                return true;
            }
        };
        listView.setOnItemLongClickListener(l);
        View v = getActivity().getLayoutInflater().inflate(R.layout.medication_list_footer,null);
        listView.addFooterView(v);
        medAdapter = new MedListAdapter(MTHelper.getMedications(),getActivity());
        listView.setAdapter(medAdapter);

        return rootView;

    }
}
