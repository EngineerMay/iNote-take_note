package com.example.mayankchauhan.inote.notes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mayankchauhan.inote.notes.model.NotesBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankchauhan on 31/05/17.
 */

public class NotesDatabaseHandler extends SQLiteOpenHelper{


    private Context context;
    public NotesDatabaseHandler(Context context) {
        super(context, NotesDatabaseConstants.DATABASE_NAME,null, NotesDatabaseConstants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesDatabaseConstants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NotesDatabaseConstants.DELETE_TABLE);
        onCreate(db);
    }
    public int addNote(NotesBeans note)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(NotesDatabaseConstants.TITLE,note.getTitle());
        contentValues.put(NotesDatabaseConstants.NOTE,note.getNote());
        contentValues.put(NotesDatabaseConstants.IMGPATH,note.getImgPath());
        contentValues.put(NotesDatabaseConstants.NOTES_DATE,note.getmDate());
        contentValues.put(NotesDatabaseConstants.NOTES_TIME,note.getmTime());

        long Id = db.insert(NotesDatabaseConstants.TABLE_NAME,null,contentValues);

        db.close();
        return (int) Id;

    }
    public List<NotesBeans> getAllNotes()
    {
        List<NotesBeans> list = new ArrayList<>();

        String SelectQuery ="SELECT * FROM "+NotesDatabaseConstants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =db.rawQuery(SelectQuery,null);

        if (cursor.moveToFirst())
        {
            do {
                NotesBeans beans = new NotesBeans();

                beans.setiD(cursor.getInt(0));
                beans.setTitle(cursor.getString(1));
                beans.setNote(cursor.getString(2));
                beans.setImgPath(cursor.getString(3));
                beans.setmDate(cursor.getString(4));
                beans.setmTime(cursor.getString(5));

                list.add(beans);
            }while (cursor.moveToNext());
        }
        db.close();
        return  list;
    }
    public NotesBeans getSingleNote(int id) {
        NotesBeans beans = new NotesBeans();

        String SelectSingle = "SELECT * FROM "+NotesDatabaseConstants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(SelectSingle,null);

        if (cursor.moveToFirst())
        {
            do {
                if (cursor.getInt(0) == id)
                {
                    beans.setiD(cursor.getInt(0));
                    beans.setTitle(cursor.getString(1));
                    beans.setNote(cursor.getString(2));
                    beans.setImgPath(cursor.getString(3));
                    beans.setmDate(cursor.getString(4));
                    beans.setmTime(cursor.getString(5));

                    break;
                }
            }while (cursor.moveToNext());
        }
        db.close();
        return beans;
    }
    public void deleteNote(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(NotesDatabaseConstants.TABLE_NAME,
                NotesDatabaseConstants.ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();

    }

}
