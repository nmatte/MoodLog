package com.nmatte.mood.moodlog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectorRow extends RelativeLayout{
    TextView textView;

    boolean value = false;
    int BLACK = 0xFF000000;
    int WHITE = 0xFFFFFFFF;
    int color;


    public SelectorRow(Context context, AttributeSet attrs) {
        super(context, attrs);

        View v = inflate(context,R.layout.selector_row,null);

        textView = (TextView) v.findViewById(R.id.textView);
        addView(v);
    }

    public SelectorRow(Context context, AttributeSet attrs, String text, final int color) {
        super(context, attrs);
        View v = inflate(context,R.layout.selector_row,null);
        textView = (TextView) v.findViewById(R.id.textView);
        textView.setText(text);
        textView.setTextColor(BLACK);
        this.color = color;
        textView.setBackgroundColor(color);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                value = !value;
                textView.setBackgroundColor(value ? BLACK : color);
                textView.setTextColor(value ? WHITE : BLACK);
            }
        });
        addView(v);
    }

    public boolean getValue(){
        return value;
    }

    public void setValue(boolean newVal){
        this.value = newVal;
        textView.setBackgroundColor(value ? BLACK : this.color);
        textView.setTextColor(value ? WHITE : BLACK);
    }
}
