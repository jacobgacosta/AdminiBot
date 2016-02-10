package io.dojogeek.adminibot.utiltest.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.IncomesModel;
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

    public ContentValues createContentValues(IncomesModel incomesModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesContract.Incomes.COLUMN_DESCRIPTION, incomesModel.description);
        contentValues.put(IncomesContract.Incomes.COLUMN_AMOUNT, incomesModel.amount);
        contentValues.put(IncomesContract.Incomes.COLUMN_DATE, incomesModel.date);
        contentValues.put(IncomesContract.Incomes.COLUMN_NEXT_ENTRY, incomesModel.nextDate);
        contentValues.put(IncomesContract.Incomes.COLUMN_USER_ID, incomesModel.userId);

        return contentValues;

    }

}
