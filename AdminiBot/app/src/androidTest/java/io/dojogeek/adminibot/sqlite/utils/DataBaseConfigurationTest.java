package io.dojogeek.adminibot.sqlite.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;

public class DataBaseConfigurationTest {

    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;

    public void loadDataBaseConfiguration(Context context) {
        mContext = context;
        deleteExistentDB();
        loadSQLiteHelper();
        openDataBaseConnection();
    }

    public void deleteExistentDB() {
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    public void loadSQLiteHelper() {
        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);
    }

    public void openDataBaseConnection() {
        mSQLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
    }

    public void closeDataBaseConnection() {
        mAdminiBotSQLiteOpenHelper.close();
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return mSQLiteDatabase;
    }

    public AdminiBotSQLiteOpenHelper getAdminiBotSQLiteOpenHelper() {
        return mAdminiBotSQLiteOpenHelper;
    }


}
