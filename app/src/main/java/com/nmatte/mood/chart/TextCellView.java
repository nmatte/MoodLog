package com.nmatte.mood.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nmatte.mood.moodlog.R;

public class TextCellView extends TextView {


    public TextCellView(Context context){
        super(context);
        init();
    }

    public TextCellView(Context context, String text){
        super(context);
        setText(text);
        init();
    }

    public TextCellView(Context context, String text, int backgroundColor){
        super(context);
        setText(text);
        init();
    }

    public TextCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextCellView(Context context, int backgroundColor){
        super(context);
        init();
    }

    private void init(){
        setBackgroundResource(R.drawable.drop_shadow);
        setSingleLine();
    }



}
