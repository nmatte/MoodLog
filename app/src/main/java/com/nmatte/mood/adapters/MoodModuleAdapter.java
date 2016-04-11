package com.nmatte.mood.adapters;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nmatte.mood.models.modules.MoodModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;

import java.util.ArrayList;

public class MoodModuleAdapter extends BoolModuleAdapter {
    MoodModule module;

    public MoodModuleAdapter(MoodModule module) {
        super(module);

        this.module = module;
    }

//    private ArrayList<ImageCellView> getCheckboxes(){
//        ArrayList<ImageCellView> result = new ArrayList<>();
//        Resources res = context.getResources();
//        int colorID = (module.isMini()) ? R.array.mood_colors_mini : R.array.mood_colors;
//        int [] colors = res.getIntArray(colorID);
//        int factor = (module.isMini()) ? 2 : 1;
//        int i = 0;
//        for (int color :
//                colors) {
//            ImageCellView newRow = new ImageCellView(context, color);
//            newRow.setBackground(CellView.Background.NONE);
//            newRow.setChecked(module.get(i));
//            result.add(newRow);
//            i+= factor;
//        }
//        return result;
//    }

    @Override
    public ArrayList<View> getLabelViews(Context context) {
        RelativeLayout mainView = (RelativeLayout) View.inflate(context, R.layout.view_moodmodule_large_label, null);

        LinearLayout labelLayout = (LinearLayout) mainView.findViewById(R.id.moodLabelLayout);

//        if (this.module.isMini()){
//            VerticalText depressed = (VerticalText) mainView.findViewById(R.id.depressedText);
//            depressed.setTextAlignment(VerticalText.TextAlignment.BOTTOM);
//            depressed.setText("DEP");
//            VerticalText normal = (VerticalText) mainView.findViewById(R.id.normalText);
//            normal.setText("NORM");
//            VerticalText elevated = (VerticalText) mainView.findViewById(R.id.elevatedText);
//            elevated.setTextAlignment(VerticalText.TextAlignment.TOP);
//            elevated.setText("ELEV");
//        }

        ArrayList<TextCellView> result = new ArrayList<>();
//        int labelID = (module.isMini() ) ? R.array.mood_labels_mini : R.array.mood_labels;
        String [] moodLabels = context.getResources().getStringArray(R.array.mood_labels);
//        int colorID = (module.isMini() ) ? R.array.mood_colors_mini : R.array.mood_colors;
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

        for (TextCellView cellView : result){
            labelLayout.addView(cellView);
        }

        ArrayList<View> mainResult = new ArrayList<>();
        mainResult.add(mainView);
        return mainResult;
    }
}
