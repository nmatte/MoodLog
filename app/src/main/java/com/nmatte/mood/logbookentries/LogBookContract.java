package com.nmatte.mood.logbookentries;


public class LogBookContract {
    public static final String LOGBOOKENTRY_TABLE = "entries",
            LOGBOOKENTRY_DATE_COLUMN = "date",
            LOGBOOKENTRY_DATE_TYPE = "INTEGER PRIMARY KEY",
            LOGBOOKENTRY_MOOD_COLUMN = "moodLevels",
            LOGBOOKENTRY_MOOD_TYPE = "TEXT",
            LOGBOOKENTRY_ANXIETY_COLUMN = "anxiety",
            LOGBOOKENTRY_ANXIETY_TYPE = "INTEGER",
            LOGBOOKENTRY_IRRITABILITY_COLUMN = "irritability",
            LOGBOOKENTRY_IRRITABILITY_TYPE = "INTEGER",
            LOGBOOKENTRY_HOURS_SLEPT_COLUMN = "hours",
            LOGBOOKENTRY_HOURS_SLEPT_TYPE = "INTEGER",
            LOGBOOKENTRY_MEDICATION_COLUMN = "medications",
            LOGBOOKENTRY_MEDICATION_TYPE = "TEXT";
}
