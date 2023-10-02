package com.himasha.fitfatz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Todolist.db";
    public static final String TABLE_NAME = "Todolist_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "NAME2";
    public static final String COL_4 = "NAME3";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, NAME2 TEXT, NAME3 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean createTask(String name, String name2, String name3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, name2);
        contentValues.put(COL_4, name3);

        // Insert the task into the database table
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor readTask() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    public boolean updateTasks(String id, String name, String name2, String name3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, name2);
        contentValues.put(COL_4, name3);

        // Update the task in the database table
        long count = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    public int deleteTasks(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the task from the database table
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
