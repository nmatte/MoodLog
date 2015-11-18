package com.nmatte.mood.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
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
        else if (t > oldt)
            listener.onScrollDown();
        // Grab the last child placed in the ScrollView, we need it to determinate the bottom position.
        View view = (View) getChildAt(getChildCount()-1);

        // Calculate the scrolldiff
        int diff = (view.getBottom()-(getHeight()+getScrollY()));

        // if diff is zero, then the bottom has been reached
        if( diff == 0 )
        {
            // notify that we have reached the bottom
            Log.i("ScrollView", "MyScrollView: Bottom has been reached" );
            listener.onScrollDown();
        }
        else if (this.getTop() == t){
            Log.i("ScrollView", "MyScrollView: Top has been reached" );
            listener.onScrollUp();
        }


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

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (scrollY < 0)
            listener.onScrollDown();
        else if (scrollY > 0)
            listener.onScrollUp();
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

}
