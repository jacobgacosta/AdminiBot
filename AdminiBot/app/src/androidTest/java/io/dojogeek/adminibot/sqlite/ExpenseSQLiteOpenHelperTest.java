package io.dojogeek.adminibot.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.utils.DateUtils;
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

    @After
    public void tearDown() throws Exception {
        mAdminiBotSQLiteOpenHelper.close();
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

    @Test
    public void sqliteHelper_correctInsertData_isTrue() {

        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = createContentValues(createExpenseModel());

        long responseId = sqLiteDatabase.insert(ExpenseContract.Expense.TABLE_NAME,
                ExpenseContract.Expense.COLUMN_NAME_NULLABLE, contentValues);

        assertTrue(responseId > -1);
    }

    private ExpenseModel createExpenseModel() {

        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.totalAmount = "4566.90";
        expenseModel.userId = 1;
        expenseModel.expenseTypeId = 1;
        expenseModel.dataExpediture = DateUtils.getCurrentData();
        expenseModel.name = "expense test";

        return expenseModel;
    }

    private ContentValues createContentValues(ExpenseModel expenseModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseContract.Expense.COLUMN_NAME, expenseModel.name);
        contentValues.put(ExpenseContract.Expense.COLUMN_USER_ID, expenseModel.userId);
        contentValues.put(ExpenseContract.Expense.COLUMN_EXPENSES_TYPE_ID, expenseModel.expenseTypeId);
        contentValues.put(ExpenseContract.Expense.COLUMN_AMOUNT, expenseModel.totalAmount);
        contentValues.put(ExpenseContract.Expense.COLUMN_DATE_EXPEDITURE, expenseModel.dataExpediture);

        return contentValues;

    }

    private void compareResultQueryFields(Cursor currentPosition, ExpenseModel expenseModel) {

        String name = currentPosition.getString(currentPosition.getColumnIndex(ExpenseContract.Expense.COLUMN_NAME));
        long userId = currentPosition.getLong(currentPosition.getColumnIndex(ExpenseContract.Expense.COLUMN_USER_ID));
        long expenseTypeId = currentPosition.getLong(currentPosition.getColumnIndex(ExpenseContract.Expense.COLUMN_EXPENSES_TYPE_ID));
        String amount = currentPosition.getString(currentPosition.getColumnIndex(ExpenseContract.Expense.COLUMN_AMOUNT));
        String date = currentPosition.getString(currentPosition.getColumnIndex(ExpenseContract.Expense.COLUMN_DATE_EXPEDITURE));


        assertEquals(expenseModel.name, name);
        assertEquals(expenseModel.dataExpediture, date);
        assertEquals(expenseModel.totalAmount, amount);
        assertEquals(expenseModel.userId, userId);
        assertEquals(expenseModel.expenseTypeId, expenseTypeId);

    }
}
