package com.progammingtalents.android.databaseexample.utils;

import android.content.Context;

import com.progammingtalents.android.databaseexample.database.DBHelper;



public class CommonUtilities {

    /**
     * Check if singleton object of DB is null and not open; in the case
     * reinitialize and open DB.
     *
     * @param mContext
     */
    public static DBHelper getDBObject(Context mContext) {
        DBHelper dbhelper = DBHelper.getInstance(mContext);
        return dbhelper;
    }
}
