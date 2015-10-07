package com.nmatte.mood.reminders;

import org.joda.time.DateTime;

import java.text.DateFormat;

public class Reminder {
    private final DateTime time;
    private String message;

    Reminder(DateTime time, String message){
        this.time = time;
        this.message = message;
    }

    public DateTime getTime() {
        return time;
    }

    public String getMessage(){
        return message;
    }


    public String calendarString (){
        return DateFormat
                .getTimeInstance(DateFormat.SHORT)
                .format(time.toDate());
    }

    public int timeOfDay(){
        return Integer.valueOf(time.toString("HHmm"));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Reminder){
            Reminder rhs = (Reminder) o;
            return this.timeOfDay() == rhs.timeOfDay();
        }
        return false;
    }
}
