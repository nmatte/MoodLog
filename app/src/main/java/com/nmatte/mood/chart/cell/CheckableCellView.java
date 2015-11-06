package com.nmatte.mood.chart.cell;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class CheckableCellView extends CheckboxCellView {

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
