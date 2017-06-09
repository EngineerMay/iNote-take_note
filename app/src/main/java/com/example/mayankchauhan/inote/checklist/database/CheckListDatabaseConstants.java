package com.example.mayankchauhan.inote.checklist.database;

/**
 * Created by mayankchauhan on 06/06/17.
 */

public class CheckListDatabaseConstants {

    public static final String CHECKLIST_DATABASE = "namesOfCheckList";
    public static final String CHECKLIST_TABLE_NAME = "tableOfNames";
    public static final int CHECKLIST_DATABASE_VERSION = 1;

    public static final String CHECKLIST_ID ="nameId";
    public static final String CHECKLIST_NAME = "name";
    public static final String CHECKLIST_TASK = "toDoTask";
    public static final String CHECKLIST_DATE = "date";
    public static final String CHECKLIST_TIME = "time";

    public static final String CHECKLIST_CREATE_TABLE ="CREATE TABLE "+CHECKLIST_TABLE_NAME+
            "("+CHECKLIST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+CHECKLIST_NAME+
            " TEXT NOT NULL,"+CHECKLIST_TASK+" TEXT NOT NULL,"+CHECKLIST_DATE+" TEXT,"+CHECKLIST_TIME+" TEXT);";

    public static final String CHECKLIST_DELETE_TABLE ="DROP TABLE IF EXISTS "+CHECKLIST_TABLE_NAME;

    public static final String CHECKLIST_SELECT_QUERY = "SELECT * FROM "+ CHECKLIST_TABLE_NAME;

    public static final String CHECKLIST_REMINDER_ID = "checklist_reminder_id";
}
