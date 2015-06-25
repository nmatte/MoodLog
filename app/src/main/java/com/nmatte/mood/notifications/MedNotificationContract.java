package com.nmatte.mood.notifications;

/**
 * Created by Nathan on 6/19/2015.
 */
public class MedNotificationContract {
    public static final String MED_NOTIFICATION_TABLE = "medreminders",
                            MED_REMINDER_TIME_COLUMN = "timeID",
                            MED_REMINDER_TIME_TYPE = "INTEGER PRIMARY KEY",
                            MED_REMINDER_INTENT_COLUMN = "intentID",
                            MED_REMINDER_INTENT_TYPE = "INTEGER",
                            MED_REMINDER_MEDICATIONS_COLUMN = "medications",
                            MED_REMINDER_MEDICATIONS_TYPE = "text";
}
