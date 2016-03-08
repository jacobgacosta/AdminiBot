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
        if (mDatabase == null) {
            mDatabase = mHelper.getWritableDatabase();
        }
    }

    public void closeConnection() {
        mHelper.close();
    }
}
