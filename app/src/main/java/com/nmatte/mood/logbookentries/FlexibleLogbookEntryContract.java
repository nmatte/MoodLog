package com.nmatte.mood.logbookentries;

public class FlexibleLogbookEntryContract {
    public static final String
            ENTRY_TABLE_NAME = "FlexibleLogbookEntryTable",
            ENTRY_DATE_COLUMN = "LogbookEntryDate",
            ENTRY_DATE_TYPE = "INTEGER PRIMARY KEY",
            ENTRY_MOOD_COLUMN = "LogbookEntryMood",
            ENTRY_MOOD_TYPE = "TEXT",
            ENTRY_NUMITEM_COLUMN = "LogbookEntryNumItems",
            ENTRY_NUMITEM_TYPE = "TEXT",
            ENTRY_BOOLITEM_COLUMN = "LogbookEntryBoolItems",
            ENTRY_BOOLITEM_TYPE = "TEXT";
}
