package com.alibi.triviaapp.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbManager extends SQLiteOpenHelper {

    private static final String dbName = "trivia.db";

    public DbManager(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table game_details (game_number integer primary key autoincrement,date text,name text,cricketer text,flag_colors text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS game_details");
        onCreate(db);
    }

    public String addGameDetails(String date, String name, String cricketer, String colors) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("name", name);
        contentValues.put("cricketer", cricketer);
        contentValues.put("flag_colors", colors);

        long result = db.insert("game_details", null, contentValues);

        if (result == -1) {
            return "failed";
        } else {
            return "Successfully Inserted";
        }
    }


    public Cursor fetchHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from game_details order by game_number asc";
        return db.rawQuery(query, null);
    }
}
