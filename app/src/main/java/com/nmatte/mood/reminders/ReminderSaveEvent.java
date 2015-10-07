package com.nmatte.mood.reminders;

public class ReminderSaveEvent {
    Reminder reminder;

    public ReminderSaveEvent(Reminder reminder){
        this.reminder = reminder;
    }

    public Reminder getReminder(){
        return reminder;
    }
}
