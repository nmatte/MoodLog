package com.nmatte.mood.logbookitems;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.boolitems.EditBoolItem;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class LogbookEditorFragment extends Fragment {
    LinearLayout mainLayout;
    LinearLayout boolItemLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_logbook_editor,container);
        mainLayout = (LinearLayout) mainView.findViewById(R.id.logbookEditorLayout);
        boolItemLayout = (LinearLayout) mainView.findViewById(R.id.boolItemList);

        ArrayList<BoolItem> boolItems = BoolItemTableHelper.getAll(getActivity());
        for (final BoolItem item : boolItems){
            /*
            View rowView = inflater.inflate(R.layout.row_boolitem_edit,boolItemLayout);
            rowView.findViewById(R.id.delButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //BoolItemTableHelper.deleteBoolItemWithName(getActivity(),item);
                }
            });
            EditText nameField = (EditText) rowView.findViewById(R.id.itemName);
            nameField.setText(item.getName());
            */
            EditBoolItem rowView = new EditBoolItem(getActivity(),item);
            boolItemLayout.addView(rowView);
        }


        return mainView;
    }



    public void addNewBoolItem(){
        /*
        final View view = View.inflate(getActivity(),R.layout.row_boolitem_edit,null);
        Button delButton =(Button) view.findViewById(R.id.delButton);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolItemLayout.removeView(view);
            }
        });
        */
        final EditBoolItem rowView = new EditBoolItem(getActivity());
        rowView.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolItemLayout.removeView(rowView);
            }
        });
        rowView.itemName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){

                }

                return false;
            }
        });

        boolItemLayout.addView(rowView);
    }
}
