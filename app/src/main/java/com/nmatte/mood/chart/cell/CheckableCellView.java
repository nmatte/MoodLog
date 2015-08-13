package com.nmatte.mood.chart.cell;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;

public class CheckableCellView extends ReadonlyCheckbox {

    public CheckableCellView(Context context) {
        super(context);
        init();
    }

    public CheckableCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CheckableCellView(Context context, int backgroundColor) {
        super(context, backgroundColor);
        init();
    }
    private void init(){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    setChecked(!isChecked);
            }
        });
    }


}
