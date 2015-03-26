package com.nmatte.mood.logbookentries;


public class LogBookContract {
    public static final String LOGBOOKENTRY_TABLE = "entries";

    public static final String LOGBOOKENTRY_DATE_COLUMN = "date";
    public static final String LOGBOOKENTRY_DATE_TYPE = "INTEGER PRIMARY KEY";

    public static final String LOGBOOKENTRY_MOOD_COLUMN = "moodLevels";
    public static final String LOGBOOKENTRY_MOOD_TYPE = "TEXT";

    public static final String LOGBOOKENTRY_ANXIETY_COLUMN = "anxiety";
    public static final String LOGBOOKENTRY_ANXIETY_TYPE = "INTEGER";

    public static final String LOGBOOKENTRY_IRRITABILITY_COLUMN = "irritability";
    public static final String LOGBOOKENTRY_IRRITABILITY_TYPE = "INTEGER";

    public static final String LOGBOOKENTRY_HOURS_SLEPT_COLUMN = "hours";
    public static final String LOGBOOKENTRY_HOURS_SLEPT_TYPE = "INTEGER";

    public static final String LOGBOOKENTRY_MEDICATION_COLUMN = "medications";
    public static final String LOGBOOKENTRY_MEDICATION_TYPE = "TEXT";
}
