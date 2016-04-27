package com.nmatte.mood.views.chart.cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.nmatte.mood.moodlog.R;


public class ImageCellView extends CellView {
    Paint blackPaint;
    Rect blackRect;
    boolean value;
    OnChangeListener onChangeListener = null;
    int imageResource = R.drawable.black_circle;

    protected ImageCellView(Context context, int backgroundColor, boolean value, OnChangeListener listener, int imageResource) {
        super(context, backgroundColor);
        this.value = value;
        this.onChangeListener = listener;
        this.imageResource = imageResource;
        init();
    }

    private void init(){
        this.blackPaint = new Paint();
        this.blackPaint.setColor(context.getResources().getColor(R.color.black));
        this.blackRect = new Rect();

        this.setOnClickListener(v -> {
            if (onChangeListener != null) {
                value = !value;
                onChangeListener.onChange(value);
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(value) {
            Drawable d = getContext().getResources().getDrawable(imageResource);
            int length = (int) getContext().getResources().getDimension(R.dimen.small_button_length);
            int xOffset = (getWidth() - length )/ 2;
            int yOffset = (getHeight() - length) / 2;

            if (d != null) {
                d.setBounds(xOffset, yOffset, length + xOffset, yOffset + length);
                d.draw(canvas);
            }
        }

    }

    public interface OnChangeListener{
        /**
         * Called when the checkbox changes its value.
         * @param value The value of the checkbox after its most recent click.
         */
        void onChange(boolean value);
    }
}
