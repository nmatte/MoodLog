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
import com.nmatte.mood.chart.column.ChartColumn;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class MoodModule {

    public static ArrayList<ImageCellView>
    getCheckboxViews(Context context, ArrayList<Boolean> cellValues, ChartColumn.Mode mode){
        ArrayList<ImageCellView> result = new ArrayList<>();
        Resources res = context.getResources();
        int [] colors = res.getIntArray(R.array.mood_colors);
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

    public static View getLabelView(Context context){
        RelativeLayout mainView = (RelativeLayout) View.inflate(context,R.layout.view_moodmodule_large_label,null);

        LinearLayout labelLayout = (LinearLayout) mainView.findViewById(R.id.moodLabelLayout);

        for (TextCellView cellView : getLabelViews(context)){
            labelLayout.addView(cellView);
        }


        return mainView;
    }

    public static ArrayList<TextCellView> getLabelViews(Context context){
        ArrayList<TextCellView> result = new ArrayList<>();
        String [] moodLabels = context.getResources().getStringArray(R.array.mood_labels);
        int[] moodColors = context.getResources().getIntArray(R.array.mood_colors);

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
