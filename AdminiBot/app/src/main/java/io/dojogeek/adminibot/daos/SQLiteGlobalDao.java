package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sqlite.AdminiBotSQLiteOpenHelper;

public class SQLiteGlobalDao {

    private static final int INVALID_RESPONSE = 0;

    protected AdminiBotSQLiteOpenHelper mHelper;
    protected SQLiteDatabase mDatabase;

    public SQLiteGlobalDao(Context context) {
        mHelper = AdminiBotSQLiteOpenHelper.getInstance(context);
    }

    public void openConection() {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void closeConection() {
        mHelper.close();
    }

    protected boolean isValidResponse(long response) {
        if (response > INVALID_RESPONSE) {
            return true;
        }

        return false;
    }

}
