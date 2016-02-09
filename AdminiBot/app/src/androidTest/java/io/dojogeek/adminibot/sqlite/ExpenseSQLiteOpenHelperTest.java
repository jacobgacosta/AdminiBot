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
import io.dojogeek.adminibot.utiltest.CreatorModels;
import io.dojogeek.adminibot.utiltest.sqlite.DataBaseConfigurationTest;
import io.dojogeek.adminibot.utiltest.sqlite.ExpenseDataUtilTest;
import io.dojogeek.adminibot.utils.DateUtils;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpenseSQLiteOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;
    private static final int INSERT_ERROR = -1;

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

        loadExpenseUtil();

    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_creationExpenseTable_isTrue() {

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = '" +
                ExpensesContract.Expense.TABLE_NAME + "'", null);

        assertNotNull(cursor);
        assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

    }

    @Test
    public void sqliteHelper_insertionData_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        assertTrue(insertExpenseModelData(expenseModel) > INSERT_ERROR);
    }

    @Test
    public void sqliteHelper_readingData_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long recordId = insertExpenseModelData(expenseModel);

        Cursor cursor = mExpenseDataUtilTest.queryRecordWhere(getIdField(recordId));

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, expenseModel);
            }
        }
    }

    @Test
    public void sqlite_updatingData_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long recordId = insertExpenseModelData(expenseModel);

        expenseModel = CreatorModels.createExpenseModel("Expense type test update", 100, DateUtils.getCurrentData(),
                DateUtils.getCurrentData(), 3, 50);

        ContentValues newContentValues = mExpenseDataUtilTest.createContentValues(expenseModel);

        long updatedRecord = mExpenseDataUtilTest.updateRecord(newContentValues, getIdField(recordId));

        Cursor cursor = mExpenseDataUtilTest.queryRecordWhere(getIdField(updatedRecord));

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, expenseModel);
            }
        }
    }

    @Test
    public void sqlite_deletionData_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long createdExpenseId = insertExpenseModelData(expenseModel);

        long deletedRecord = mExpenseDataUtilTest.deleteRecordWhere(getIdField(createdExpenseId));

        assertEquals(createdExpenseId, deletedRecord);

    }

    private long insertExpenseModelData(ExpenseModel expenseModel) {

        ContentValues contentValues = mExpenseDataUtilTest.createContentValues(expenseModel);

        long insertedRecord = mExpenseDataUtilTest.createRecord(contentValues);

        return insertedRecord;
    }

    private void compareResultQueryFields(Cursor currentPosition, ExpenseModel expenseModel) {

        String description = currentPosition.getString(currentPosition.getColumnIndex(ExpensesContract.Expense.COLUMN_DESCRIPTION));
        long amount = currentPosition.getLong(currentPosition.getColumnIndex(ExpensesContract.Expense.COLUMN_AMOUNT));
        String dateExpediture = currentPosition.getString(currentPosition.getColumnIndex(ExpensesContract.Expense.COLUMN_DATE_EXPEDITURE));
        String nextExit = currentPosition.getString(currentPosition.getColumnIndex(ExpensesContract.Expense.COLUMN_NEXT_EXIT));
        long expenseTypeId = currentPosition.getLong(currentPosition.getColumnIndex(ExpensesContract.Expense.COLUMN_EXPENSES_TYPE_ID));
        long userId = currentPosition.getLong(currentPosition.getColumnIndex(ExpensesContract.Expense.COLUMN_USER_ID));


        assertEquals(expenseModel.description, description);
        assertEquals(expenseModel.amount, amount);
        assertEquals(expenseModel.dateExpediture, dateExpediture);
        assertEquals(expenseModel.nextExit, nextExit);
        assertEquals(expenseModel.expenseTypeId, expenseTypeId);
        assertEquals(expenseModel.userId, userId);

    }

    private void loadExpenseUtil() {
        mExpenseDataUtilTest = new ExpenseDataUtilTest(mSQLiteDatabase);
    }

    private String getIdField(long id) {
        return UserContract.User._ID + " = " + id;
    }
}
