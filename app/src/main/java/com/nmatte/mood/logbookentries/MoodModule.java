package com.nmatte.mood.logbookentries;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.chart.cell.ImageCellView;
import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.chart.cell.TextCellViewBuilder;
import com.nmatte.mood.chart.cell.VerticalText;
import com.nmatte.mood.chart.column.ChartColumn;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class MoodModule {

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

    public static ArrayList<ImageCellView>
    getCheckboxViews(Context context, ArrayList<Boolean> cellValues, ChartColumn.Mode mode, Size size){
        ArrayList<ImageCellView> result = new ArrayList<>();
        Resources res = context.getResources();
        int colorID = (size == Size.MINI) ? R.array.mood_colors_mini : R.array.mood_colors;
        int [] colors = res.getIntArray(colorID);
        for (int i = 0; i < colors.length; i++){
            ImageCellView newRow = new ImageCellView(context,colors[i],mode);
            newRow.setBackground(CellView.Background.NONE);
            if (i < cellValues.size())
                newRow.setChecked(cellValues.get(i));
            else
                newRow.setChecked(false);
            result.add(newRow);
        }
        return result;
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
}
