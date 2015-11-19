package com.nmatte.mood.logbookentries.editentry;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nmatte.mood.chart.column.CloseNoteEvent;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.moodlog.R;

import de.greenrobot.event.EventBus;

public class NoteView extends RelativeLayout{
    Context context;


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
                entry.setNote(noteText.getText().toString());
                EventBus.getDefault().post(new CloseNoteEvent(entry));
            }
        });
    }

    public void setEntry(ChartEntry entry) {
        this.entry = entry;
        noteText.setText(entry.getNote());

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
