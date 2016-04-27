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
        NOTE_MODULE_NAME = "NoteModule";

    public class Bool {
        public static final String NAME = "BoolModule";
        public static final long ID = 1;
    }

    public class Num {
        public static final String NAME = "NumModule";
        public static final long ID = 2;
    }

    public class Mood {
        public static final String NAME = "MoodModule";
        public static final long ID = 3;
    }

}
