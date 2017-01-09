package com.progammingtalents.android.databaseexample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.progammingtalents.android.databaseexample.utils.Constants;


public class TablesClass extends SQLiteOpenHelper {

    /**
     * Write all create table statements here in this class on oncreate method
     * If any changes in table structure go for onUpgrade method
     */

    Context context;

    public TablesClass(Context context, String DatabaseName, String nullColumnHack, int databaseVersion) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE IF NOT EXISTS " + Constants.BOOK_RECORD + " ("
                + Constants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.BOOK_ID + " TEXT, "
                + Constants.BOOK_NAME + " TEXT, "
                + Constants.BOOK_AUTHOR + " TEXT) ";

        db.execSQL(table1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        context.deleteDatabase(Constants.DATABASE_NAME);
        onCreate(db);
    }
}