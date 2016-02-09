package io.dojogeek.adminibot.utiltest.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.sqlite.ExpenseContract;
import io.dojogeek.adminibot.utils.DateUtils;

public class ExpenseDataUtilTest {

    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;

    public ExpenseDataUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public long createRecord(ContentValues contentValues) {
        long insertedValueId = mDatabaseOperationsUtilTest.createRecord(mSQLiteDatabase, ExpenseContract.Expense.TABLE_NAME,
                contentValues);

        return insertedValueId;
    }

    public Cursor queryRecordWhere(String where) {

        return mDatabaseOperationsUtilTest.queryRecordWhere(mSQLiteDatabase, ExpenseContract.Expense.TABLE_NAME, where);

    }

    public long updateRecord(ContentValues contentValues, String where) {
        return mDatabaseOperationsUtilTest.updateRecord(mSQLiteDatabase, ExpenseContract.Expense.TABLE_NAME, contentValues, where);
    }

    public long deleteRecordWhere(String where) {
        return mDatabaseOperationsUtilTest.deleteRecordWhere(mSQLiteDatabase, ExpenseContract.Expense.TABLE_NAME, where);
    }

    public ContentValues createContentValues(ExpenseModel expenseModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseContract.Expense.COLUMN_DESCRIPTION, expenseModel.description);
        contentValues.put(ExpenseContract.Expense.COLUMN_AMOUNT, expenseModel.amount);
        contentValues.put(ExpenseContract.Expense.COLUMN_DATE_EXPEDITURE, expenseModel.dateExpediture);
        contentValues.put(ExpenseContract.Expense.COLUMN_NEXT_EXIT, expenseModel.nextExit);
        contentValues.put(ExpenseContract.Expense.COLUMN_EXPENSES_TYPE_ID, expenseModel.expenseTypeId);
        contentValues.put(ExpenseContract.Expense.COLUMN_USER_ID, expenseModel.userId);

        return contentValues;

    }

}
