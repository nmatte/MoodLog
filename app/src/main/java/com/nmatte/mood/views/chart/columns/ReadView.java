package com.nmatte.mood.views.chart.columns;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.moodlog.R;

public class ReadView extends LinearLayout {
    float lastXtouch;
    float lastYtouch;
    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            lastXtouch = event.getX();
            lastYtouch = event.getY();
            return false;
        }
    };

    public ReadView(Context context) {
        super(context);
        this.setBackground(context.getResources().getDrawable(R.drawable.drop_shadow_vertical));
    }



    /**
     * Returns the X coordinate of the most recent touch event on this view.
     * @return The last touch event's X coordinate.
     */
    public float getLastXtouch(){
        return lastXtouch;
    }
    /**
     * Returns the Y coordinate of the most recent touch event on this view.
     * @return The last touch event's Y coordinate.
     */
    public float getLastYtouch(){
        return lastYtouch;
    }
}
