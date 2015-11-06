package com.nmatte.mood.chart.cell;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.moodlog.R;


/* base class to draw background and borders */
public class CellView extends View {

    Paint blackPaint;
    protected int leftTransparentBound = -1;
    protected int rightTransparentBound = -1;
    protected int topTransparentBound = -1;
    protected int bottomTransparentBound = -1;
    int shadowID = VERTICAL_SHADOW_BG_ID;
    static final int DEFAULT_BG_ID = R.drawable.drop_shadow3;
    static final int VERTICAL_SHADOW_BG_ID = R.drawable.drop_shadow_vertical;
    static final int HORIZONTAL_SHADOW_BG_ID=  R.drawable.drop_shadow_horizontal;
    int backgroundColor = -1;



    static final int WHITE = 0xFFFFFFFF;
    static final int BLACK = 0xFF000000;


    public void setBg(int id) {
        this.shadowID = id;
        invalidate();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    public CellView(Context context) {
        super(context);
        init();
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CellView(Context context, int backgroundColor){
        super(context);
        init();
        this.backgroundColor = backgroundColor;
    }

    private void init(){
        blackPaint = new Paint();
        blackPaint.setColor(BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Resources res = getResources();

        int desiredHeight = (int) res.getDimension(R.dimen.chart_cell_height);
        int desiredWidth = (int) res.getDimension(R.dimen.chart_cell_width);
        if (res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            desiredHeight = desiredHeight/2;
            desiredWidth = (desiredWidth * 2)/3;
            this.setPadding(1,1,1,1);
        }

        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw default color (white)
        if (backgroundColor == -1)
            backgroundColor = WHITE;
        canvas.drawColor(backgroundColor);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                leftTransparentBound = 0;
                rightTransparentBound = getWidth();
                topTransparentBound = 0;
                bottomTransparentBound = getHeight();
            } else {
                NinePatchDrawable bg = (NinePatchDrawable) getResources().getDrawable(shadowID);

                if (bg != null) {
                    bg.setBounds(0, 0, getWidth(), getHeight());
                    bg.draw(canvas);
                    Rect bounds = bg.getTransparentRegion().getBounds();
                    leftTransparentBound = bounds.left;
                    rightTransparentBound = bounds.right;
                    topTransparentBound = bounds.top;
                    bottomTransparentBound = bounds.bottom;
                } else {
                    leftTransparentBound = 0;
                    rightTransparentBound = getWidth();
                    topTransparentBound = 0;
                    bottomTransparentBound = getHeight();
                }
            }


    }



    protected float getAdjustedTextSize (String text){
        blackPaint.setTextSize(getHeight());
        while(blackPaint.measureText(text) > getWidth() * 0.8){
            blackPaint.setTextSize(blackPaint.getTextSize() * (float) 0.7);
        }
        return blackPaint.getTextSize();
    }
}
