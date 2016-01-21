package io.dojogeek.adminibot.sqlite.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.ExpenseTypeContract;

public class ExpenseTypeDataUtilTest {

    private static final int ADITIONAL_INDEX = 1;

    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;

    public ExpenseTypeDataUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public Cursor queryAllRecords() {

        Cursor cursor = mDatabaseOperationsUtilTest.queryAllRecords(mSQLiteDatabase, ExpenseTypeContract.ExpenseType.TABLE_NAME);

        return cursor;
    }

    public static String getExpenseTypeFromId(Context context, long id) {

        int [] expectedExpensesTypes = ExpenseTypeContract.EXPENSES_TYPES;

        return context.getString(expectedExpensesTypes[getId(id)]);
    }

    public static String getExpenseTypeDescriptionFromId(Context context, long id) {

        int [] expextedExpensesTypesDescriptions = ExpenseTypeContract.EXPENSES_TYPES_DESCRIPTIONS;

        return context.getString(expextedExpensesTypesDescriptions[getId(id)]);
    }

    private static int getId(long value) {
        return (int) value - ADITIONAL_INDEX;
    }

}
