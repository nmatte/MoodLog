package com.nmatte.mood.medications;


public class MedicationContract {
    public static final String MEDICATION_TABLE = "medications",
            MEDICATION_NAME_COLUMN = "medicationName",
            MEDICATION_NAME_TYPE = "TEXT UNIQUE",
            MEDICATION_ID_COLUMN = "_ID",
            MEDICATION_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
}
