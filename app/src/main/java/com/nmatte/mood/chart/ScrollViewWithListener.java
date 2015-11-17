package com.nmatte.mood.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class ScrollViewWithListener extends ScrollView {
    ScrollListener listener;

    public interface ScrollListener{
        void onScrollUp();
        void onScrollDown();
    }
    public ScrollViewWithListener(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollListener (ScrollListener listener){
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (t < oldt)
            listener.onScrollUp();
        else
            listener.onScrollDown();
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void fling(int velocityY) {
        if (velocityY > 0) {
            Log.i("Scrollview Fling", "Positive fling");
            listener.onScrollDown();
        }
        if (velocityY < 0){
            Log.i("Scrollview Fling", "Negative fling");
            listener.onScrollUp();
        }

        super.fling(velocityY);
    }

}
