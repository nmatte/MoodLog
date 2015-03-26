package com.nmatte.mood.medications;


public class MedicationContract {
    public static final String MEDICATION_TABLE = "medications";

    public static final String MEDICATION_NAME_COLUMN = "medicationName";
    public static final String MEDICATION_NAME_TYPE = "TEXT UNIQUE";

    public static final String MEDICATION_ID_COLUMN = "_ID";
    public static final String MEDICATION_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
}
