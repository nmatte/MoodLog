package com.nmatte.mood.models;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.CellView;
import com.nmatte.mood.views.chart.ChartColumn;
import com.nmatte.mood.views.chart.ImageCellView;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;
import com.nmatte.mood.views.chart.VerticalText;

import java.util.ArrayList;

public class MoodModule {

    ArrayList<Boolean> fullValues;


    public MoodModule(ArrayList<Boolean> fullValues){
        this.fullValues = fullValues;
    }

    public MoodModule(String values){
        this.fullValues = new ArrayList<>();
        for (String value : values.split(" ")){
            fullValues.add(value.equals("T"));
        }
    }

    public static View getLabelView(Context context, Size size){
        RelativeLayout mainView = (RelativeLayout) View.inflate(context, R.layout.view_moodmodule_large_label, null);

        LinearLayout labelLayout = (LinearLayout) mainView.findViewById(R.id.moodLabelLayout);

        if (size == Size.MINI){
            VerticalText depressed = (VerticalText) mainView.findViewById(R.id.depressedText);
            depressed.setTextAlignment(VerticalText.TextAlignment.BOTTOM);
            depressed.setText("DEP");
            VerticalText normal = (VerticalText) mainView.findViewById(R.id.normalText);
            normal.setText("NORM");
            VerticalText elevated = (VerticalText) mainView.findViewById(R.id.elevatedText);
            elevated.setTextAlignment(VerticalText.TextAlignment.TOP);
            elevated.setText("ELEV");
        }



        for (TextCellView cellView : getLabelViews(context, size)){
            labelLayout.addView(cellView);
        }


        return mainView;
    }

    public static ArrayList<TextCellView> getLabelViews(Context context, Size size){
        ArrayList<TextCellView> result = new ArrayList<>();
        int labelID = (size == Size.MINI) ? R.array.mood_labels_mini : R.array.mood_labels;
        String [] moodLabels = context.getResources().getStringArray(labelID);
        int colorID = (size == Size.MINI) ? R.array.mood_colors_mini : R.array.mood_colors;
        int[] moodColors = context.getResources().getIntArray(colorID);

        int i = 0;
        for (String label : moodLabels){
            TextCellViewBuilder b = new TextCellViewBuilder(context);
            if (i < moodColors.length){
                result.add(b
                        .setText(label)
                        .setBackgroundColor(moodColors[i])
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                        .build());
                i++;
            } else {
                result.add(b.setText(label).build());
            }
        }
        return result;
    }

    public ArrayList<Boolean> getFullValues() {
        return fullValues;
    }

    public void set(int index, boolean value){
        fullValues.set(index,value);
    }

    public ArrayList<Boolean> getMiniValues(){
        ArrayList<Boolean> result = new ArrayList<>();
        int i = 0;
        for (Boolean value :
                fullValues) {
            if (i % 2 == 0){
                result.add(value);
            }
        }
        return result;
    }

    public ArrayList<ImageCellView>
    getCheckboxes(Context context, ChartColumn.Mode mode, Size size){
        ArrayList<ImageCellView> result = new ArrayList<>();
        Resources res = context.getResources();
        int colorID = (size == Size.MINI) ? R.array.mood_colors_mini : R.array.mood_colors;
        int [] colors = res.getIntArray(colorID);
        int factor = (size == Size.MINI) ? 2 : 1;
        int i = 0;
        for (int color :
                colors) {
            ImageCellView newRow = new ImageCellView(context,color,mode);
            newRow.setBackground(CellView.Background.NONE);
            newRow.setChecked(fullValues.get(i));
            result.add(newRow);
            i+= factor;
        }
        return result;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Boolean value : fullValues) {
            builder
                    .append(value ? "T" : "F")
                    .append(" ");
        }
        return builder.toString();
    }

    public enum Size {
        MINI(0),
        FULL(1);
        final int sizeCode;
        Size (int sizeCode){
            this.sizeCode = sizeCode;
        }

        public static Size fromSizeCode(int sizeCode){
            if (sizeCode == MINI.getSizeCode()){
                return MINI;
            } else if (sizeCode == FULL.getSizeCode()){
                return FULL;
            }
            return FULL;
        }
        public int getSizeCode() {
            return sizeCode;
        }
    }
}
