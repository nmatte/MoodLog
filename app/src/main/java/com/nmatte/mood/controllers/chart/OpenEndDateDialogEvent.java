package com.nmatte.mood.controllers.chart;

import org.joda.time.DateTime;

public class OpenEndDateDialogEvent {
    DateTime date;

    public OpenEndDateDialogEvent(DateTime date) {
        this.date = date;
    }

    public DateTime getDate() {
        return date;
    }
}
