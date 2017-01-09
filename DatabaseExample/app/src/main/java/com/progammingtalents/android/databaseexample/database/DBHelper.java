package com.progammingtalents.android.databaseexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import com.progammingtalents.android.databaseexample.model.BookData;
import com.progammingtalents.android.databaseexample.utils.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class DBHelper {

    private SQLiteDatabase db;
    private final Context context;
    private final TablesClass dbHelper;
    public static int no;
    public static DBHelper db_helper = null;

    public static DBHelper getInstance(Context context){
        try{
            if(db_helper == null){
                db_helper = new DBHelper(context);
                db_helper.open();
            }
        }catch(IllegalStateException e){
            //db_helper already open
        }
        return db_helper;
    }

    /*
	 * set context of the class and initialize AppData Object
	 */

    public DBHelper(Context c) {
        context = c;
        dbHelper = new TablesClass(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    /*
     * close databse.
     */
    public void close() {
        if (db.isOpen()) {
            db.close();
        }
    }

    public boolean dbOpenCheck() {
        try{
            return db.isOpen();
        }catch(Exception e){
            return false;
        }
    }

    /*
     * open database
     */
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            // Log.v("open database Exception", "error==" + e.getMessage());
            db = dbHelper.getReadableDatabase();
        }
    }

    public long insertContentVals(String tableName, ContentValues content){
        long id=0;
        try{
            id = db.insert(tableName, null, content);
        }catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public Cursor getTableRecords(String tablename, String[] columns, String where, String orderby){
        Cursor cursor =  db.query(false, tablename, columns,where, null, null, null, orderby, null);
        return cursor;
    }

    /*
	 * Get count of all tables in a table as per the condition
	 */

    public int getFullCount(String table, String where) {
        Cursor cursor = db.query(false, table, null, where, null, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                no = cursor.getCount();
                cursor.close();
            }
        } finally {
            cursor.close();
        }
        return no;
    }


    public void deleteRecords(String table, String whereClause, String[] whereArgs){
        db.delete(table, whereClause, whereArgs);
    }

    /*
	 * Get value of any table as per the condition.
	 */

    public String getValue(String table, String column, String where) {
        Cursor result = db.query(false, table, new String[] { column }, where,
                null, null, null, Constants.ID, null);
        String value = "";
        try {
            if (result.moveToFirst()) {
                value = result.getString(0);
            } else {
                return null;
            }
        } finally {
            result.close();
        }
        return value;
    }
	/*
	 * Get Multiple Values from column of any specified table.
	 */

    public String[] getValues(boolean b, String table, String column,
                              String where, String orderBy) {
        ArrayList<String> savedAns = new ArrayList<String>();
        Cursor result = null;
        String[] y;
        try {
            result = db.query(b, table, new String[] { column }, where, null,
                    null, null, orderBy, null);
            if (result.moveToFirst()) {
                do {
                    savedAns.add(result.getString(result.getColumnIndex(column)));
                } while (result.moveToNext());
            } else {
                return null;
            }
            y = savedAns.toArray(new String[result.getCount()]);
        } finally {
            result.close();
        }
        return y;
    }

    public int updateRecords(String table, ContentValues values,
                             String whereClause, String[] whereArgs) {
        int a = db.update(table, values, whereClause, whereArgs);
        return a;
    }

    public List<BookData> getAllBooks() {
        List<BookData> books = new LinkedList<BookData>();

        // select book query
        String query = "SELECT  * FROM " + Constants.BOOK_RECORD;

        // get reference of the BookDB database
        Cursor cursor = db.rawQuery(query, null);
        //Cursor c= db.rawQuery(query,null );

        // parse all results
        BookData book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new BookData();
                book.setId((cursor.getString(0)).toString());
                book.setBookId(cursor.getString(1));
                book.setBookName(cursor.getString(2));
                book.setAuthorName(cursor.getString(3));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }
        return books;
    }


}
