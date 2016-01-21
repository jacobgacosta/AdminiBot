package io.dojogeek.adminibot.sqlite.utils;

import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.utils.DateUtils;

public class ExpenseDataUtilTest {

    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;

    public ExpenseDataUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public ExpenseModel createExpenseModel() {

        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.totalAmount = "4566.90";
        expenseModel.userId = 1;
        expenseModel.expenseTypeId = 1;
        expenseModel.dataExpediture = DateUtils.getCurrentData();
        expenseModel.name = "expense test";

        return expenseModel;
    }

}
