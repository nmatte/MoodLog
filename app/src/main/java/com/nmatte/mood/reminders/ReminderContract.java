package com.nmatte.mood.reminders;


public class ReminderContract {
    public static final String REMINDER_TABLE = "reminders",
                            REMINDER_TIME_OF_DAY_COLUMN = "timeID",
                            REMINDER_TIME_TYPE = "INTEGER PRIMARY KEY",
                            REMINDER_MILLIS_COLUMN = "intentID",
                            REMINDER_MILLIS_TYPE = "INTEGER",
                            REMINDER_MESSAGE_COLUMN = "message",
                            REMINDER_MESSAGE_TYPE = "text";
}
