package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sqlite.AdminiBotSQLiteOpenHelper;
import sqlite.ExpenseContract;
import sqlite.PaymentMethodsContract;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpenseSQLiteOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;

    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;

    @Before
    public void setup() {

        mContext = getTargetContext();

        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);

        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);

    }

    @Test
    public void sqliteHelper_correctTableCreation_isTrue() {

        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = '" +
                ExpenseContract.Expense.TABLE_NAME + "'", null);

        if (cursor != null) {

            assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

        }

    }

}
