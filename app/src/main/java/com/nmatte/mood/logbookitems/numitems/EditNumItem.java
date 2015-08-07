package com.nmatte.mood.logbookitems.numitems;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.nmatte.mood.moodlog.R;

public class EditNumItem extends RelativeLayout {
    NumItem numItem = null;
    public EditText itemName;
    public ImageButton delButton;
    public ImageButton saveButton;
    public ImageButton editButton;
    public EditText defaultNumText;
    public EditText maxNumText;
    Context context;

    public EditNumItem(Context context) {
        super(context);
        this.context = context;
        init();

    }

    public EditNumItem(Context context, NumItem numItem){
        super(context);
        this.context = context;
        this.numItem = numItem;
        init();
    }

    private void init() {
        View mainLayout = inflate(context, R.layout.row_numitem_edit,null);
        itemName = (EditText) mainLayout.findViewById(R.id.itemName);

        delButton = (ImageButton) mainLayout.findViewById(R.id.delButton);
        saveButton = (ImageButton) mainLayout.findViewById(R.id.saveButton);
        editButton = (ImageButton) mainLayout.findViewById(R.id.editButton);

        defaultNumText = (EditText) mainLayout.findViewById(R.id.defaultNumEditText);
        maxNumText = (EditText) mainLayout.findViewById(R.id.maxNumEditText);

        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditable(false);
            }
        });
        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditable(true);
            }
        });


        if (numItem != null){
            itemName.setText(numItem.getName());
            defaultNumText.setText(String.valueOf(numItem.getDefaultNum()));
            maxNumText.setText(String.valueOf(numItem.getMaxNum()));
        } else {
            setEditable(true);
        }


        addView(mainLayout);
    }

    public void setEditable(boolean isEditable){
        if (isEditable){
            delButton.setVisibility(VISIBLE);
            saveButton.setVisibility(VISIBLE);
            editButton.setVisibility(GONE);
            itemName.setEnabled(true);
        } else {
            delButton.setVisibility(GONE);
            saveButton.setVisibility(GONE);
            editButton.setVisibility(VISIBLE);
            itemName.setEnabled(false);
        }
    }



}
