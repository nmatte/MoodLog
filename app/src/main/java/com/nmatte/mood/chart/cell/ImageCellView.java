package com.nmatte.mood.chart.cell;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.chart.column.ChartColumn;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.moodlog.R;


public class ImageCellView extends CellView {

    Paint blackPaint;
    Rect blackRect;

    BoolItem boolItem;

    boolean isChecked;
    OnChangeListener onChangeListener = null;
    ChartColumn.Mode mode;
    int imageResource = R.drawable.black_square;



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

        if(isChecked) {
            Drawable d = getContext().getResources().getDrawable(imageResource);
            int length = (int) getContext().getResources().getDimension(R.dimen.small_button_length);
            int xOffset = (getWidth() - length )/ 2;
            int yOffset = (getHeight() - length) / 2;
            d.setBounds(xOffset,yOffset,length + xOffset,yOffset + length);
            d.draw(canvas);

        }

    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean newValue){
        isChecked = newValue;
        invalidate();
    }

    public void setImageResource(int id){
        this.imageResource = id;
    }

    public BoolItem getBoolItem() {
        return boolItem;
    }

    public void setBoolItem(BoolItem boolItem) {
        this.boolItem = boolItem;
    }

}
