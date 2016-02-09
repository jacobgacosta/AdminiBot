package io.dojogeek.adminibot.utiltest.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.ExpensesPaymentMethodsContract;

public class ExpensesPaymentMethodsUtilTest {

    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;

    public ExpensesPaymentMethodsUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public long createRecord(ContentValues contentValues) {
        long insertedValueId = mDatabaseOperationsUtilTest.createRecord(mSQLiteDatabase, ExpensesPaymentMethodsContract.ExpensesPaymentMethods.TABLE_NAME,
                contentValues);

        return insertedValueId;
    }

    public Cursor queryRecordWhere(String where) {

        return mDatabaseOperationsUtilTest.queryRecordWhere(mSQLiteDatabase, ExpensesPaymentMethodsContract.ExpensesPaymentMethods.TABLE_NAME, where);

    }

    public long updateRecord(ContentValues contentValues, String where) {
        return mDatabaseOperationsUtilTest.updateRecord(mSQLiteDatabase, ExpensesPaymentMethodsContract.ExpensesPaymentMethods.TABLE_NAME, contentValues, where);
    }

    public long deleteRecordWhere(String where) {
        return mDatabaseOperationsUtilTest.deleteRecordWhere(mSQLiteDatabase, ExpensesPaymentMethodsContract.ExpensesPaymentMethods.TABLE_NAME, where);
    }

    public ContentValues createContentValues(double amount, long fkPaymentMethod, long fkExpense) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensesPaymentMethodsContract.ExpensesPaymentMethods.COLUMN_EXPENSE_ID, fkExpense);
        contentValues.put(ExpensesPaymentMethodsContract.ExpensesPaymentMethods.COLUMN_PAYMENTH_METHOD_ID, fkPaymentMethod);
        contentValues.put(ExpensesPaymentMethodsContract.ExpensesPaymentMethods.COLUMN_AMOUNT, amount);

        return contentValues;

    }

}
