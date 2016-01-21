package io.dojogeek.adminibot.sqlite.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.sqlite.ExpenseContract;
import io.dojogeek.adminibot.sqlite.UserContract;
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

    public ExpenseModel createExpenseModel() {

        return createExpenseModel("4566.90", 1, 1, DateUtils.getCurrentData(), "expense test");
    }

    public ExpenseModel createExpenseModel(String totalAmount, int userId, int expenseType,
                                           String currentDate, String name) {
        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.totalAmount = totalAmount;
        expenseModel.userId = userId;
        expenseModel.expenseTypeId = expenseType;
        expenseModel.dataExpediture = currentDate;
        expenseModel.name = name;

        return expenseModel;
    }

}
