package com.michaelwijaya.xyzdictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Favorite.db";

    public FavoriteDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                    FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY," +
                    FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE + " TEXT," +
                    FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_DEFS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
