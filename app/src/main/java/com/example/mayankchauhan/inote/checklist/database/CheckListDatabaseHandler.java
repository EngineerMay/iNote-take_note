package com.example.mayankchauhan.inote.checklist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mayankchauhan.inote.checklist.model.CheckListBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankchauhan on 06/06/17.
 */

public class CheckListDatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    public CheckListDatabaseHandler(Context context) {
        super(context, CheckListDatabaseConstants.CHECKLIST_DATABASE,null, CheckListDatabaseConstants.CHECKLIST_DATABASE_VERSION);;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CheckListDatabaseConstants.CHECKLIST_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CheckListDatabaseConstants.CHECKLIST_DELETE_TABLE);
        onCreate(db);
    }
    public int addCheckList(CheckListBeans beans)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(CheckListDatabaseConstants.CHECKLIST_NAME,beans.getCheckListName());
        cv.put(CheckListDatabaseConstants.CHECKLIST_TASK,beans.getCheckListTasks());
        cv.put(CheckListDatabaseConstants.CHECKLIST_DATE,beans.getCheckListDate());
        cv.put(CheckListDatabaseConstants.CHECKLIST_TIME,beans.getCheckListTime());

        long i = db.insert(CheckListDatabaseConstants.CHECKLIST_TABLE_NAME,null,cv);
        db.close();
        return (int) i;
    }
    public List<CheckListBeans> getAllCheckList()
    {
        List<CheckListBeans> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor  = db.rawQuery(CheckListDatabaseConstants.CHECKLIST_SELECT_QUERY,null);

        if (cursor.moveToFirst())
        {
            do
            {
                CheckListBeans beans = new CheckListBeans();

                beans.setCheckListId(cursor.getInt(0));
                beans.setCheckListName(cursor.getString(1));
                beans.setCheckListTasks(cursor.getString(2));
                beans.setCheckListDate(cursor.getString(3));
                beans.setCheckListTime(cursor.getString(4));

                list.add(beans);
            }while (cursor.moveToNext());
        }
        db.close();

        return list;
    }
    public CheckListBeans getSingleCheckList(int id)
    {
        CheckListBeans beans = new CheckListBeans();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor  = db.rawQuery(CheckListDatabaseConstants.CHECKLIST_SELECT_QUERY,null);

        if (cursor.moveToFirst())
        {
            do
            {   if (id == cursor.getInt(0))
                {
                beans.setCheckListId(cursor.getInt(0));
                beans.setCheckListName(cursor.getString(1));
                beans.setCheckListTasks(cursor.getString(2));
                beans.setCheckListDate(cursor.getString(3));
                beans.setCheckListTime(cursor.getString(4));
                break;
                }
            }while (cursor.moveToNext());
        }
        db.close();
        return beans;
    }
    public void deleteCheckList(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(CheckListDatabaseConstants.CHECKLIST_TABLE_NAME,
                CheckListDatabaseConstants.CHECKLIST_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();

    }
}
