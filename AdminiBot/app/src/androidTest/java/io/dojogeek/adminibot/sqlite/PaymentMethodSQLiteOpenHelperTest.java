package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sqlite.AdminiBotSQLiteOpenHelper;
import sqlite.ExpenseTypeContract;
import sqlite.PaymentMethodsContract;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PaymentMethodSQLiteOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;
    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;

    @Before
    public void setUp() {

        mContext = getTargetContext();

        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);

        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);
    }

    @After
    public void tearDown() throws Exception {
        mAdminiBotSQLiteOpenHelper.close();
    }

    @Test
    public void sqliteHelper_correctTableCreation_isTrue() {
        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = '" +
                PaymentMethodsContract.PaymentMethod.TABLE_NAME + "'", null);

        if (cursor != null) {

            assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

        }

    }

}
