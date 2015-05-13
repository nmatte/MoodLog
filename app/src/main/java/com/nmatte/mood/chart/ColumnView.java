package com.nmatte.mood.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
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



    private enum DrawMode {
        LABEL, NONE, ENTRY, BUTTON
    }

    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackRect = new Rect();
        initPaints();
    }

    public ColumnView(Context context, LogbookEntry entry, int startDate, String [] labels) {
        super(context);
        if (entry != null) {
            currentMode = DrawMode.ENTRY;
            this.entry = entry;
            this.startDate = startDate;
        }
        if (labels != null) {
            currentMode = DrawMode.LABEL;
            this.labels = labels;
        }
        fillColorArray();
        blackRect = new Rect();
        initPaints();
    }

    private void initPaints(){
        paint = new Paint();
        blackPaint = new Paint();
    }

    public void setButtonMode(){
        this.currentMode = DrawMode.BUTTON;
        allColors.clear();
        for (int color : moodColors) allColors.add(color);
    }

    public void updateEntry(LogbookEntry e){
        this.entry = e;
        invalidate();
    }

    private void fillColorArray(){
        allColors.add(WHITE);
        for (int color : moodColors) allColors.add(color);
        for (int i = 0; i < 3; i++) allColors.add(WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight()/(allColors.size());

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
                                text = Integer.toString(entry.getDate() - startDate);
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
                case BUTTON:
                    if(entry.getMoods().size() == 0) break;
                    if(entry.getMoods().get(i)) {
                        blackRect.top = top + heightDiff;
                        blackRect.bottom = bottom - heightDiff;
                        canvas.drawRect(blackRect,blackPaint);
                    }
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
            double newSize = currentSize * 0.85;
            float newSize2 = (float) newSize;
            paint.setTextSize(newSize2);
        }
        return paint;
    }
}

