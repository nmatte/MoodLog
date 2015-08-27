package com.nmatte.mood.logbookitems;


/*
LogbookItems are the basic components of a user's chart. The default setup was originally 3 hardcoded
numerical items (Anxiety 0-3, Irritability 0-3, Sleep 0-24) and user-defined medications (I decided
to call them "bool items" after this).

On the chart, they are represented as number pickers and checkboxes.

"Log" refers to the "logbook", which stores daily entries made by the user.
"Item" refers to the items defined by the user to appear on their chart.
 */
public class LogbookItemContract {

    public class Bool {
        // Table for information regarding num items (name, id).
        public static final String
                ITEM_TABLE = "BoolItems",

                ITEM_ID_COLUMN = "_ID",
                ITEM_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT",
                ITEM_NAME_COLUMN = "BoolItemName",
                ITEM_NAME_TYPE = "TEXT UNIQUE",
                ITEM_VISIBLE_COLUMN = "BoolItemVisible",
                ITEM_VISIBLE_TYPE = "INTEGER";


        // Table for storing entries to correspond with ChartEntries.
        public static final String
                LOG_TABLE = "BoolItemEntryTable",
                LOG_DATE_COLUMN = "BoolChartEntryDate",
                LOG_DATE_TYPE = "INTEGER PRIMARY KEY",
        // SQLite doesn't support boolean, so I'll use 0 and 1.
                LOG_VALUE_TYPE = "INTEGER";
    }

    public class Num{
        // Table for information regarding num items (name, id, max val, etc).
        public static final String
                ITEM_TABLE = "NumItems",
                ITEM_ID_COLUMN = "_ID",
                ITEM_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT",
                ITEM_NAME_COLUMN = "numItemName",
                ITEM_NAME_TYPE = "TEXT UNIQUE",
                ITEM_VISIBLE_COLUMN = "NumItemVisible",
                ITEM_VISIBLE_TYPE = "INTEGER",
                ITEM_MAX_COLUMN = "numMaxValue",
                ITEM_MAX_TYPE = "INTEGER",
                ITEM_DEFAULT_COLUMN = "numItemDefault",
                ITEM_DEFAULT_TYPE = "INTEGER";


        // Table for storing entries to correspond with ChartEntries.
        public static final String
                LOG_TABLE = "NumItemEntryTable",
                LOG_DATE_COLUMN = "NumChartEntryDate",
                LOG_DATE_TYPE = "INTEGER PRIMARY KEY",
                LOG_VALUE_TYPE = "INTEGER";

    }



}
