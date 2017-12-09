package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

public class SQLiteGlobalDao {

    protected final int NO_DATA = 0;
    protected SQLiteDatabase mDatabase;
    private AdminiBotSQLiteOpenHelper mHelper;

    public SQLiteGlobalDao(Context context) {
        mHelper = AdminiBotSQLiteOpenHelper.getInstance(context);
    }

    public void openConnection() {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void beginTransaction() {
        mDatabase.beginTransaction();
    }

    public void setTransactionSuccessful() {
        mDatabase.setTransactionSuccessful();
    }

    public void endTransaction() {
        mDatabase.endTransaction();
    }

    public void closeConnection() {
        mHelper.close();
    }
}
