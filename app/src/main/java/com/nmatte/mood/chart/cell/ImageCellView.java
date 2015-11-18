package com.nmatte.mood.chart.cell;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.chart.monthview.ChartColumn;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.moodlog.R;


public class ImageCellView extends CellView {

    Paint blackPaint;
    Rect blackRect;

    BoolItem boolItem;

    boolean isChecked;
    OnChangeListener onChangeListener = null;
    ChartColumn.Mode mode;
    Image image = Image.RECT;

    public enum Image {
        RECT,
        NOTE
    }


    public interface OnChangeListener{
        /**
         * Called when the checkbox changes its value.
         * @param value The value of the checkbox after its most recent click.
         */
        void onChange(boolean value);
    }


    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public ImageCellView(Context context, ChartColumn.Mode mode) {
        super(context);
        this.mode = mode;
        init();
    }

    public ImageCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ImageCellView(Context context, int backgroundColor, ChartColumn.Mode mode) {
        super(context, backgroundColor);
        this.mode = mode;
        init();
    }


    private void init(){
        this.blackPaint = new Paint();
        this.blackPaint.setColor(BLACK);
        this.blackRect = new Rect();
        if (mode == ChartColumn.Mode.ENTRY_EDIT){
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChecked(!isChecked);
                    if (onChangeListener != null)
                        onChangeListener.onChange(isChecked());

                }
            });
        }

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cellTop = 0;
        int cellBottom = getHeight();
        int cellLeft = 0;
        int cellRight = getWidth();


        if(isChecked) {
            // draw indicator that cell is checked
            int heightDiff = getHeight() * 2 / 10;
            //blackRect.left = cellLeft + getWidth()/10;
            //blackRect.right = cellRight - getWidth()/10;
            //blackRect.top = cellTop+ heightDiff;
            //blackRect.bottom = cellBottom - heightDiff;

            if (image == Image.RECT){

                blackRect.left = super.leftTransparentBound;
                blackRect.right = super.rightTransparentBound;
                blackRect.top = super.topTransparentBound;
                blackRect.bottom = super.bottomTransparentBound;
                canvas.drawRect(blackRect, blackPaint);
            }


            if (image == Image.NOTE) {
                Drawable d = getContext().getResources().getDrawable(R.drawable.ic_assignment_black_24dp);
                int length = (int) getContext().getResources().getDimension(R.dimen.small_button_length);
                int xOffset = (getWidth() - length )/ 2;
                int yOffset = (getHeight() - length) / 2;
                d.setBounds(xOffset,yOffset,length + xOffset,yOffset + length);
                d.draw(canvas);
            }
        }

    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean newValue){
        isChecked = newValue;
        invalidate();
    }



    public void setImage (Image image){
        this.image = image;
    }



    public BoolItem getBoolItem() {
        return boolItem;
    }

    public void setBoolItem(BoolItem boolItem) {
        this.boolItem = boolItem;
    }

}