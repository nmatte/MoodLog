package com.nmatte.mood.moodlog;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class GraphColumnView extends View{
    Paint paint;
    boolean [] moodChecked;
    final int [] colors = {0xFFFFE0D4,0xFFFFBBA7,0xFFFFE1C8,0xFFFFE0AD,0xFFFFF9B3,0xFFDDEDCF,
            0xFFC8E4B2, 0xFFAEDCB6,0xFFC5E7DB,0xFFB5E3E6,0xFFA3E0F7,0xFFABC2E3,0xFFE0DBEC};

    public GraphColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.GraphColumnView,0,0);
        String moodString = a.getString(R.styleable.GraphColumnView_moodString);
        if (moodString == null)
            moodString = "";
        moodChecked = parseMoodString(moodString);
        paint = new Paint();
    }



    public void setMoodString(String s){
        moodChecked = parseMoodString(s);
        invalidate();
    }

    private boolean [] parseMoodString (String moodString){
        boolean [] result = new boolean[colors.length];

        for (int i = 0; i < result.length; i++){
            if(moodString != null && moodString.length() < 2){
                result[i] = false;
            } else {
                String nextInt = moodString.substring(0,2);
                int indexOfTrue = Integer.parseInt(nextInt);
                if (i == indexOfTrue){
                    result[i] = true;
                    moodString = moodString.substring(2);
                } else {
                    result[i] = false;
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = 0;
        int cellWidth = getWidth()/13;
        int right = cellWidth;

        Rect smallRect = new Rect();
        smallRect.top = getHeight()/10;
        smallRect.bottom = getHeight() - smallRect.top;


        for (int i = 0; i < colors.length; i++){
            Rect r = new Rect();
            r.top = 0;
            r.bottom = getHeight();
            r.left = left;
            r.right = right;
            paint.setColor(colors[i]);
            canvas.drawRect(r,paint);

            if(moodChecked[i]){
                smallRect.left = left + cellWidth/10;
                smallRect.right = right - cellWidth/10;
                paint.setColor(0xFF000000);
                canvas.drawRect(smallRect,paint);
            }

            left += cellWidth;
            right += cellWidth;


        }
    }
}
