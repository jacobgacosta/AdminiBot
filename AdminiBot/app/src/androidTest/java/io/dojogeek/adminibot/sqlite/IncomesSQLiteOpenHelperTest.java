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

import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.utiltest.CreatorModels;
import io.dojogeek.adminibot.utiltest.sqlite.DataBaseConfigurationTest;
import io.dojogeek.adminibot.utiltest.sqlite.IncomesDataUtilTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class IncomesSQLiteOpenHelperTest {

    private static final int  AFFECTED_ROWS = 1;
    private static final int NONE_TABLE_CREATED = 0;
    private static final int INSERT_ERROR = -1;

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private IncomesDataUtilTest mIncomesDataUtilTest;

    @Before
    public void setup() {

        mContext = getTargetContext();

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(mContext);
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        loadIncomesUtil();

    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_creationIncomesTable_isTrue() {

        Cursor cursor = mSQLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                IncomesContract.Incomes.TABLE_NAME + "'", null);

        assertNotNull(cursor);
        assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

    }

    @Test
    public void sqliteHelper_insertionData_isTrue() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        assertTrue(insertIncome(incomeModel) > INSERT_ERROR);
    }

    @Test
    public void sqliteHelper_readingData_isTrue() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long recordId = insertIncome(incomeModel);

        String where = UsersContract.User._ID + " = " + recordId;

        Cursor cursor = mIncomesDataUtilTest.queryRecordWhere(where);

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, incomeModel);
            }
        }
    }

    @Test
    public void sqliteHelper_updatingData_isTrue() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long recordId = insertIncome(incomeModel);

        String where = UsersContract.User._ID + " = " + recordId;

        incomeModel = CreatorModels.createIncomeModel();
        incomeModel.description = "Test update description";

        ContentValues newValues = mIncomesDataUtilTest.createContentValues(incomeModel);

        long updatedRowsAffected = mIncomesDataUtilTest.updateRecord(newValues, where);

        assertEquals(AFFECTED_ROWS, updatedRowsAffected);

        Cursor cursor = mIncomesDataUtilTest.queryRecordWhere(where);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, incomeModel);
            }
        }

    }

    @Test
    public void sqliteHelper_deletingData_isTrue() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long recordId = insertIncome(incomeModel);

        String where = UsersContract.User._ID + " = " + recordId;

        long deletedRows = mIncomesDataUtilTest.deleteRecordWhere(where);

        assertEquals(AFFECTED_ROWS, deletedRows);

    }

    private long insertIncome(IncomeModel incomeModel) {

        ContentValues contentValues = mIncomesDataUtilTest.createContentValues(incomeModel);

        long insertedRecordId = mIncomesDataUtilTest.createRecord(contentValues);

        return insertedRecordId;
    }

    private void loadIncomesUtil() {
        mIncomesDataUtilTest = new IncomesDataUtilTest(mSQLiteDatabase);
    }

    private void compareResultQueryFields(Cursor currentPosition, IncomeModel incomeModel) {

        String description = currentPosition.getString(currentPosition.getColumnIndex(IncomesContract.Incomes.COLUMN_DESCRIPTION));
        double amount = currentPosition.getDouble(currentPosition.getColumnIndex(IncomesContract.Incomes.COLUMN_AMOUNT));
        String date = currentPosition.getString(currentPosition.getColumnIndex(IncomesContract.Incomes.COLUMN_DATE));
        String nextEntry = currentPosition.getString(currentPosition.getColumnIndex(IncomesContract.Incomes.COLUMN_NEXT_ENTRY));
        long userId = currentPosition.getLong(currentPosition.getColumnIndex(IncomesContract.Incomes.COLUMN_USER_ID));


        assertEquals(incomeModel.description, description);
        assertEquals(incomeModel.amount, amount, 0);
        assertEquals(incomeModel.date, date);
        assertEquals(incomeModel.nextDate, nextEntry);
        assertEquals(incomeModel.userId, userId);

    }

}
