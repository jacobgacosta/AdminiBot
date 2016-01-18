package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import sqlite.AdminiBotSQLiteOpenHelper;
import sqlite.ExpenseTypeContract;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AdminiBotSQLiteOpenHelperTest {

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
    public void sqliteHelper_numberOfInsertedExpensesTypes_isEquals() {

        final int insertedValues = 5;

        SQLiteDatabase database = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ExpenseTypeContract.ExpenseType.TABLE_NAME, null, null, null, null, null, null);

        assertEquals(cursor.getCount(), insertedValues);

    }

    @Test
    public void sqliteHelper_expensesTypesInserted_isEquals() {

        SQLiteDatabase database = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ExpenseTypeContract.ExpenseType.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                compareResultQueryFields(cursor);

                cursor.moveToNext();
            }
        }

    }

    private void compareResultQueryFields(Cursor currentPosition) {

        int [] expectedExpensesTypes = ExpenseTypeContract.EXPENSES_TYPES;
        int [] expextedExpensesTypesDescriptions = ExpenseTypeContract.EXPENSES_TYPES_DESCRIPTIONS;

        long actualId = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType._ID));
        int actualName = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType.COLUMN_NAME));
        int actualDescription = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType.COLUMN_DESCRIPTION));

        String expectedName = getStringFromResourceId(expectedExpensesTypes[castLongToInt(actualId)]);
        String expectedDescription = getStringFromResourceId(expextedExpensesTypesDescriptions[castLongToInt(actualId)]);

        assertEquals(expectedName, actualName);
        assertEquals(expectedDescription, actualDescription);

    }

    private String getStringFromResourceId(int resourceId) {
        return mContext.getResources().getString(resourceId);
    }

    private int castLongToInt(long value) {
        return (int) value;
    }

}
