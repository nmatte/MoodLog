package com.nmatte.mood.views.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nmatte.mood.controllers.chart.ChartEvents;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.columns.ChartColumn;

import de.greenrobot.event.EventBus;

public class NoteView extends RelativeLayout{
    Context context;


    ChartColumn.Mode mode;
    ImageButton button;
    TextView noteText;
    ChartEntry entry;

    public NoteView(Context context) {
        super(context);
        this.context = context;
        init();
    }


    public NoteView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context = context;
        init();
    }

    private void init(){
        inflate(context, R.layout.note_view, this);
        button = (ImageButton) findViewById(R.id.doneButton);
        noteText = (TextView) findViewById(R.id.noteText);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO

//                entry.setNote(noteText.getText().toString());
                EventBus.getDefault().post(new ChartEvents.CloseNoteEvent(entry));
            }
        });
    }

    public void setEntry(ChartEntry entry) {
        this.entry = entry;
        // TODO
        noteText.setText("placeholder");
//        noteText.setText(entry.getNote());
    }

    public void setMode(ChartColumn.Mode mode){
        if (mode == ChartColumn.Mode.ENTRY_EDIT){
            this.mode = mode;
            noteText.setEnabled(true);
        } else if (mode == ChartColumn.Mode.ENTRY_READ){
            this.mode = mode;
            noteText.setEnabled(false);
        }
    }

    public void animateDown(){
        this.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(this.getHeight())
                .start();
    }

    public void animateUp(){
        this.setTranslationY(this.getHeight());


        this.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(0)
                .start();
    }

}
