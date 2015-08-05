package com.nmatte.mood.logbookitems.numitems;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.nmatte.mood.moodlog.R;

public class EditNumItem extends RelativeLayout {
    public EditText itemName;
    public Button delButton;
    public EditText defaultNumText;
    public EditText maxNumText;
    Context context;

    public EditNumItem(Context context) {
        super(context);
        this.context = context;
        init();

    }

    private void init() {
        View mainLayout = inflate(context, R.layout.row_numitem_edit,null);
        itemName = (EditText) mainLayout.findViewById(R.id.nameEditText);
        delButton = (Button) mainLayout.findViewById(R.id.delButton);
        defaultNumText = (EditText) mainLayout.findViewById(R.id.defaultNumEditText);
        maxNumText = (EditText) mainLayout.findViewById(R.id.maxNumEditText);


        addView(mainLayout);
    }


}
