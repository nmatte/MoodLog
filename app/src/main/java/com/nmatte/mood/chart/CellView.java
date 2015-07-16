package com.nmatte.mood.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;



/* base class to draw background and borders */
public class CellView extends View {

    boolean highlighted  = false;

    Paint blackPaint;
    Paint highlightPaint;

    Rect highlightRect;


    static final int WHITE = 0xFFFFFFFF;
    static final int BLACK = 0xFF000000;
    static final int DESIRED_HEIGHT = 48;
    static final int DESIRED_WIDTH = 24;


    int backgroundColor = WHITE;

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
        highlightPaint = new Paint();
        highlightPaint.setColor(0x50878787);
        highlightRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DESIRED_WIDTH, getResources().getDisplayMetrics());
        int desiredHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DESIRED_HEIGHT, getResources().getDisplayMetrics());

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

        // draw BG color
        canvas.drawColor(backgroundColor);

        int cellTop = 0;
        int cellBottom = getHeight();
        int cellLeft = 0;
        int cellRight = getWidth();

        //draw borders
        blackPaint.setStrokeWidth(5);

        // bottom line
        canvas.drawLine(cellLeft, cellBottom, cellRight, cellBottom, blackPaint);
        // top line
        canvas.drawLine(cellLeft, cellTop, cellRight, cellTop, blackPaint);
        // left line
        canvas.drawLine(cellLeft, cellTop, cellLeft, cellBottom,blackPaint);
        // right line
        canvas.drawLine(cellRight, cellTop, cellRight, cellBottom, blackPaint);
    }

    public void setHighlighted(boolean isHighlighted){
        this.highlighted = isHighlighted;
        invalidate();
    }

    protected void drawHighlight(Canvas canvas){
        if (highlighted){
            highlightRect.top = 0;
            highlightRect.bottom = getHeight();
            highlightRect.left = 0;
            highlightRect.right = getWidth();
            canvas.drawRect(highlightRect,highlightPaint);
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
