package com.nmatte.mood.database.components;


import com.nmatte.mood.database.modules.ModuleContract;

public class LogbookItemContract {
    public static final String
            ID_COLUMN = "_ID",
            ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT",
            NAME_COLUMN = "component_name",
            NAME_TYPE = "TEXT UNIQUE",
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
        public static final String
                ITEM_TABLE = "bool_components";
        public static final String
                LOG_VALUE_TYPE = "INTEGER";
    }
    public class Num{
        public static final String
                ITEM_TABLE = "num_components",
                ITEM_MAX_COLUMN = "num_max",
                ITEM_MAX_TYPE = "INTEGER",
                ITEM_DEFAULT_COLUMN = "num_default",
                ITEM_DEFAULT_TYPE = "INTEGER";

        public static final String
                LOG_VALUE_TYPE = "INTEGER";
    }
}
