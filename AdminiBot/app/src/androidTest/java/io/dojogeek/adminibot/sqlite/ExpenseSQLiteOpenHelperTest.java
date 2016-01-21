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
import io.dojogeek.adminibot.sqlite.utils.DataBaseConfigurationTest;
import io.dojogeek.adminibot.sqlite.utils.ExpenseDataUtilTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpenseSQLiteOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;
    private static final int INSERT_ERROR = -1;

    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private ExpenseDataUtilTest mExpenseDataUtilTest;

    @Before
    public void setup() {

        mContext = getTargetContext();

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(mContext);
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        mAdminiBotSQLiteOpenHelper = mDataBaseConfigurationTest.getAdminiBotSQLiteOpenHelper();

        loadExpenseUtil();

    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
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
    public void sqliteHelper_correctInsertionData_isTrue() {

        ExpenseModel expenseModel = mExpenseDataUtilTest.createExpenseModel();

        assertTrue(insertData(expenseModel) > INSERT_ERROR);
    }

    @Test
    public void sqliteHelper_correctQueryData_isTrue() {

        ExpenseModel expenseModel = mExpenseDataUtilTest.createExpenseModel();

        long recordId = insertData(expenseModel);

        String where =  ExpenseContract.Expense._ID + " = " + recordId;

        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(ExpenseContract.Expense.TABLE_NAME, null, where, null, null, null, null);

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, expenseModel);
            }
        }
    }

    @Test
    public void sqlite_correctUpdateData_isTrue() {

        ExpenseModel expenseModel = createExpenseModel();

        long recordId = insertData(expenseModel);

        expenseModel = changeExpenseModelValues(expenseModel);

        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();

        ContentValues contentValues = createContentValues(expenseModel);

        String where =  ExpenseContract.Expense._ID + " = " + recordId;

        sqLiteDatabase.update(ExpenseContract.Expense.TABLE_NAME, contentValues, where, null);

        Cursor cursor = sqLiteDatabase.query(ExpenseContract.Expense.TABLE_NAME, null, where, null, null, null, null);

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, expenseModel);
            }
        }
    }

    @Test
    public void sqlite_correctDeleteData_isTrue() {

        ExpenseModel expenseModel = createExpenseModel();

        long createdExpenseId = insertData(expenseModel);

        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getWritableDatabase();

        String where =  ExpenseContract.Expense._ID + " = " + createdExpenseId;

        long deletedRecord = sqLiteDatabase.delete(ExpenseContract.Expense.TABLE_NAME, where, null);

        assertEquals(createdExpenseId, deletedRecord);

    }

    private long insertData(ExpenseModel expenseModel) {

        ContentValues contentValues = mExpenseDataUtilTest.createContentValues(expenseModel);

        long insertedRecord = mExpenseDataUtilTest.createRecord(contentValues);

        return insertedRecord;
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

    private ExpenseModel changeExpenseModelValues(ExpenseModel expenseModel) {

        expenseModel.name = "Changed text";
        expenseModel.totalAmount = "456.9";

        return expenseModel;
    }

    private void loadExpenseUtil() {
        mExpenseDataUtilTest = new ExpenseDataUtilTest(mSQLiteDatabase);
    }
}
