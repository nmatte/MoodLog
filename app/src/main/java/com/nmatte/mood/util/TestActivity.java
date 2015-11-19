package com.nmatte.mood.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.nmatte.mood.logbookentries.editentry.NoteView;
import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{

    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

         main =  (RelativeLayout) findViewById(R.id.mainLayout);

    }

    public void upAnimation(View view) {
        NoteView noteView = (NoteView) findViewById(R.id.testNoteView);
        noteView.setVisibility(View.VISIBLE);

        // start note at bottom of layout
        noteView.setY(main.getHeight());

        noteView.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(0)
                .start();


    }

    public void downAnimation(View view) {
        NoteView noteView = (NoteView) findViewById(R.id.testNoteView);
        noteView.animate()
                .translationY(noteView.getHeight())
                .start();
    }
}
