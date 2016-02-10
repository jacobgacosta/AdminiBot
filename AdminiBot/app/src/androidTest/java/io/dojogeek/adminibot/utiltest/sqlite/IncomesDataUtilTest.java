package io.dojogeek.adminibot.utiltest.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.sqlite.IncomesContract;

public class IncomesDataUtilTest {

    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;

    public IncomesDataUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public long createRecord(ContentValues contentValues) {
        long insertedValueId = mDatabaseOperationsUtilTest.createRecord(mSQLiteDatabase, IncomesContract.Incomes.TABLE_NAME,
                contentValues);

        return insertedValueId;
    }

    public Cursor queryRecordWhere(String where) {

        return mDatabaseOperationsUtilTest.queryRecordWhere(mSQLiteDatabase, IncomesContract.Incomes.TABLE_NAME, where);

    }

    public long updateRecord(ContentValues contentValues, String where) {
        return mDatabaseOperationsUtilTest.updateRecord(mSQLiteDatabase, IncomesContract.Incomes.TABLE_NAME, contentValues, where);
    }

    public long deleteRecordWhere(String where) {
        return mDatabaseOperationsUtilTest.deleteRecordWhere(mSQLiteDatabase, IncomesContract.Incomes.TABLE_NAME, where);
    }

    public ContentValues createContentValues(IncomeModel incomeModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesContract.Incomes.COLUMN_DESCRIPTION, incomeModel.description);
        contentValues.put(IncomesContract.Incomes.COLUMN_AMOUNT, incomeModel.amount);
        contentValues.put(IncomesContract.Incomes.COLUMN_DATE, incomeModel.date);
        contentValues.put(IncomesContract.Incomes.COLUMN_NEXT_ENTRY, incomeModel.nextDate);
        contentValues.put(IncomesContract.Incomes.COLUMN_USER_ID, incomeModel.userId);

        return contentValues;

    }

}
