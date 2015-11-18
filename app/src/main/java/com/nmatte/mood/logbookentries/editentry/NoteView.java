package com.nmatte.mood.logbookentries.editentry;

import android.content.Context;
import android.widget.RelativeLayout;

import com.nmatte.mood.moodlog.R;

public class NoteView extends RelativeLayout{
    public NoteView(Context context) {
        super(context);
        inflate(context, R.layout.note_view,this);
    }

}
