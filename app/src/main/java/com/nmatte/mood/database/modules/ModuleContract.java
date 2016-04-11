package com.nmatte.mood.database.modules;


public class ModuleContract {
    public static final String
        MODULE_TABLE_NAME = "module_table",
        MODULE_ID_COLUMN = "_ID",
        MODULE_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT",
        MODULE_VISIBLE_COLUMN = "module_is_visible",
        MODULE_VISIBLE_TYPE = "INTEGER",
        MODULE_NAME_COLUMN = "module_name",
        MODULE_NAME_TYPE = "TEXT UNIQUE";


    public static final String
        BOOL_MODULE_NAME = "BoolModule",
        NUM_MODULE_NAME = "NumModule",
        NOTE_MODULE_NAME = "NoteModule",
        MOOD_MODULE_NAME = "MoodModule";
}
