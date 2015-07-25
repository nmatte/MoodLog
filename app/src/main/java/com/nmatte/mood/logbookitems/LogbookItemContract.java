package com.nmatte.mood.logbookitems;


public class LogbookItemContract {
    public static final String BOOL_ITEM_TABLE = "medications",
            BOOL_ITEM_NAME_COLUMN = "medicationName",
            BOOL_ITEM_TYPE = "TEXT UNIQUE",
            BOOL_ID_COLUMN = "_ID",
            BOOL_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String NUM_ITEM_TABLE = "numItems",
            NUM_ITEM_NAME_COLUMN = "numItemName",
            NUM_ITEM_TYPE = "TEXT UNIQUE",
            NUM_ITEM_ID_COLUMN = "_ID",
            NUM_ITEM_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT",
            NUM_ITEM_MAX_NAME = "numMaxValue",
            NUM_ITEM_MAX_TYPE = "INTEGER";
}
