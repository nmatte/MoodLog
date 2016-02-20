package com.nmatte.mood.database.components;


import com.nmatte.mood.database.modules.ModuleContract;

/*
LogbookItems are the basic components of a user's chart. The default setup was originally 3 hardcoded
numerical items (Anxiety 0-3, Irritability 0-3, Sleep 0-24) and user-defined medications (I decided
to call them "bool items" after this).

On the chart, they are represented as number pickers and checkboxes.

"Log" refers to the "logbook", which stores daily entries made by the user.
"Item" refers to the items defined by the user to appear on their chart.
 */
public class LogbookItemContract {

    public static final String
            ID_COLUMN = "_ID",
            ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT",
            NAME_COLUMN = "component_name",
            NAME_TYPE = "TEXT UNIQUE",
            VISIBLE_COLUMN = "is_visible",
            VISIBLE_TYPE = "INTEGER",
            COLOR_COLUMN = "color",
            COLOR_TYPE = "INTEGER",
            PARENT_MODULE_COLUMN = "parent_id",
            PARENT_MODULE_TYPE = "INTEGER",
            FOREIGN_KEY_CONSTRAINT = String.format(
                    "FOREIGN KEY(%s) REFERENCES %s(%s)",
                    PARENT_MODULE_COLUMN,
                    ModuleContract.MODULE_TABLE_NAME,
                    ModuleContract.MODULE_ID_COLUMN
            );
    public class Bool {
        // Table for information regarding num items (name, id).
        public static final String
                ITEM_TABLE = "BoolItems";
        // Table for storing entries to correspond with ChartEntries.
        public static final String
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

        public static final String
                LOG_VALUE_TYPE = "INTEGER";
    }



}
