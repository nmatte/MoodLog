package com.nmatte.mood.views.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.models.BoolItem;
import com.nmatte.mood.moodlog.R;


public class ImageCellView extends CellView {

    Paint blackPaint;
    Rect blackRect;

    BoolItem boolItem;

    boolean isChecked;
    OnChangeListener onChangeListener = null;

    boolean isEnabled = true;

    int imageResource = R.drawable.black_circle;

    public ImageCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageCellView(Context context, int backgroundColor, boolean isEnabled) {
        super(context, backgroundColor);
        this.isEnabled = isEnabled;
        init();
    }

    public ImageCellView(Context context, int backgroundColor) {
        super(context, backgroundColor);
        init();
    }

    public ImageCellView(Context context, boolean isEnabled) {
        super(context);
        this.isEnabled = isEnabled;
        init();
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;

        if (isEnabled) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChecked(!isChecked);
                    if (onChangeListener != null)
                        onChangeListener.onChange(isChecked());

                     }
            });
        } else {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void setOnChangeListener(final OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;


        if (isEnabled) {
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

    private void init(){
        this.blackPaint = new Paint();
        this.blackPaint.setColor(BLACK);
        this.blackRect = new Rect();
        if (isEnabled){
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

    public interface OnChangeListener{
        /**
         * Called when the checkbox changes its value.
         * @param value The value of the checkbox after its most recent click.
         */
        void onChange(boolean value);
    }

}
