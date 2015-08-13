package com.nmatte.mood.chart.cell;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;

public class ReadonlyCheckbox extends CellView {
    Paint blackPaint;
    Rect blackRect;

    BoolItem boolItem;

    boolean isChecked;

    public ReadonlyCheckbox(Context context) {
        super(context);
        init();
    }

    public ReadonlyCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ReadonlyCheckbox(Context context, int backgroundColor) {
        super(context, backgroundColor);
        init();
    }


    private void init(){
        this.blackPaint = new Paint();
        this.blackPaint.setColor(BLACK);
        this.blackRect = new Rect();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cellTop = 0;
        int cellBottom = getHeight();
        int cellLeft = 0;
        int cellRight = getWidth();


        if(isChecked){
            // draw indicator that cell is checked
            int heightDiff = getHeight() * 2/10;
            blackRect.left = cellLeft + getWidth()/10;
            blackRect.right = cellRight - getWidth()/10;
            blackRect.top = cellTop+ heightDiff;
            blackRect.bottom = cellBottom - heightDiff;
            canvas.drawRect(blackRect,blackPaint);
        }
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean newValue){
        isChecked = newValue;
        invalidate();
    }





    public BoolItem getBoolItem() {
        return boolItem;
    }

    public void setBoolItem(BoolItem boolItem) {
        this.boolItem = boolItem;
    }

}
