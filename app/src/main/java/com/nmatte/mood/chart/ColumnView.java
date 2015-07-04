package com.nmatte.mood.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.nmatte.mood.logbookentries.LogbookEntry;

import java.util.ArrayList;

public class ColumnView extends View {


    Paint paint;
    Paint blackPaint;
    DrawMode currentMode = DrawMode.NONE;

    Rect blackRect;

    // for Entry mode
    int startDate = 0;
    final int NUM_INDEX = 0, ANX_INDEX = 14, IRR_INDEX = 15, HOURS_INDEX = 16;
    LogbookEntry entry;

    // for Label mode
    String [] labels;

    final int WHITE = 0xFFFFFFFF;
    final int [] moodColors = {0xFFFFE0D4,0xFFFFBBA7,0xFFFFE1C8,0xFFFFE0AD,0xFFFFF9B3,0xFFDDEDCF,
            0xFFC8E4B2, 0xFFAEDCB6,0xFFC5E7DB,0xFFB5E3E6,0xFFA3E0F7,0xFFABC2E3,0xFFE0DBEC};
    ArrayList<Integer> allColors = new ArrayList<>();

    int cellHeight;



    private enum DrawMode {
        LABEL, NONE, ENTRY
    }

    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackRect = new Rect();
        initPaints();
    }

    public ColumnView(Context context, LogbookEntry entry, int startDate) {
        super(context);
        if (entry != null) {
            currentMode = DrawMode.ENTRY;
            this.entry = entry;
            this.startDate = startDate;
        }
        fillColorArray();
        blackRect = new Rect();
        initPaints();
    }

    public ColumnView(Context context, String [] labels){
        super(context);
        currentMode = DrawMode.LABEL;
        this.labels = labels;

        fillColorArray();
        blackRect = new Rect();
        initPaints();
    }

    private void initPaints(){
        paint = new Paint();
        blackPaint = new Paint();
    }

    private void fillColorArray(){
        allColors.add(WHITE);
        for (int color : moodColors) allColors.add(color);
        for (int i = 0; i < 3; i++) allColors.add(WHITE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        cellHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,48,getResources().getDisplayMetrics());

        int desiredWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,36,getResources().getDisplayMetrics());
        int desiredHeight = allColors.size() * cellHeight;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,48,getResources().getDisplayMetrics());


        int top = 0;
        int left = 0;
        int right = getWidth();
        int bottom = height;

        blackPaint.setColor(0xFF000000);

        blackRect.left = left + getWidth()/10;
        blackRect.right = right - getWidth()/10;
        int heightDiff = height * 2/10;

        blackPaint.setTextSize(height);

        for (int i = 0; i < allColors.size(); i++) {
            paint.setColor(allColors.get(i));
            Rect r = new Rect();
            r.top = top;
            r.left = left;
            r.right = right;
            r.bottom = bottom;
            canvas.drawRect(r, paint);
            blackPaint.setStrokeWidth(5);
            canvas.drawLine(left, bottom, right, bottom, blackPaint);


            switch (currentMode) {
                case ENTRY:
                    blackPaint = adjustTextSize("00", blackPaint);
                    if (i >= ANX_INDEX || i == 0) {
                        String text = "";
                        switch (i) {
                            case NUM_INDEX:
                                text = Integer.toString((int)entry.getDate() - (int)startDate);
                                break;
                            case ANX_INDEX:
                                text = Integer.toString(entry.getAnxValue());
                                break;
                            case IRR_INDEX:
                                text = Integer.toString(entry.getIrrValue());
                                break;
                            case HOURS_INDEX:
                                text = Integer.toString(entry.getHoursSleptValue());
                                break;


                        }
                        float textX = getWidth() / 2 - blackPaint.measureText(text) / 2;
                        float textY = bottom - height / 6;
                        canvas.drawText(text, textX, textY, blackPaint);
                    } else if (!entry.getMoods().isEmpty()) {
                        if(entry.getMoods().get(i - 1)){
                        blackRect.top = top + heightDiff;
                        blackRect.bottom = bottom - heightDiff;
                        canvas.drawRect(blackRect, blackPaint);
                        }
                    }
                    break;
                case LABEL:
                    String longest = "";
                    for (String s : labels){
                        longest = (s.length() > longest.length())? s : longest;
                    }
                    blackPaint = adjustTextSize(longest,blackPaint);
                    String text = labels[i];
                    float textX = 0;
                    float textY = bottom - height / 6;
                    canvas.drawText(text,textX,textY,blackPaint);
                    break;
                case NONE:
                    break;

            }

            top += height;
            bottom += height;


        }

        //TODO: change to line?

        Rect rightLine = new Rect();
        rightLine.left = right - 1;
        rightLine.right = right;
        rightLine.top = 0;
        rightLine.bottom = bottom;

        canvas.drawRect(rightLine,blackPaint);

    }

    private Paint adjustTextSize (String text, Paint paint){
        while (paint.measureText(text) > getWidth() * 0.9){
            float currentSize = paint.getTextSize();
            double newSize = currentSize * 0.7;
            float newSize2 = (float) newSize;
            paint.setTextSize(newSize2);
        }
        return paint;
    }
}

