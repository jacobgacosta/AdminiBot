package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

public class SQLiteGlobalDao {

    protected final int NO_DATA = 0;
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
}
