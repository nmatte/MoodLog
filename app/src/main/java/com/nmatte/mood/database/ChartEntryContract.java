package com.nmatte.mood.database;

public class ChartEntryContract {
    public static final String
            ENTRY_TABLE_NAME = "FlexibleLogbookEntryTable",
            ENTRY_DATE_COLUMN = "LogbookEntryDate",
            ENTRY_DATE_TYPE = "INTEGER PRIMARY KEY",
            ENTRY_MOOD_COLUMN = "LogbookEntryMood",
            ENTRY_MOOD_TYPE = "TEXT",
            ENTRY_NOTE_COLUMN = "LogbookEntryNote",
            ENTRY_NOTE_TYPE = "TEXT";
}
