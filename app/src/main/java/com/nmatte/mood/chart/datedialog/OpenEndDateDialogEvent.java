package com.nmatte.mood.chart.datedialog;

import org.joda.time.DateTime;

public class OpenEndDateDialogEvent {
    public DateTime getDate() {
        return date;
    }

    DateTime date;

    public OpenEndDateDialogEvent(DateTime date) {
        this.date = date;
    }
}
