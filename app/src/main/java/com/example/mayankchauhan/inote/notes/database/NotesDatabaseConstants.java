package com.example.mayankchauhan.inote.notes.database;

/**
 * Created by mayankchauhan on 31/05/17.
 */

public class NotesDatabaseConstants {

    public static final String DATABASE_NAME = "notesDatabase";
    public static final String TABLE_NAME = "notesTable";
    public static final int DATABASE_VERSION = 1;

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String NOTE = "note";
    public static final String IMGPATH = "imgpath";
    public static final String NOTES_DATE = "date";
    public static final String NOTES_TIME = "time";


    public static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +TITLE+" TEXT NOT NULL,"+NOTE+" TEXT NOT NULL,"+IMGPATH+" TEXT,"+NOTES_DATE+" TEXT,"+NOTES_TIME+" TEXT"+");";


    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public static final String REMINDER_ID = "reminder_id";

    public static final String BUNDLE_ID = "bundle";

    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

}
