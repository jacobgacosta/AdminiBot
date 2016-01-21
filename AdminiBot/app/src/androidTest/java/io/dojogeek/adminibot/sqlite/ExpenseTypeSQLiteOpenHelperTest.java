package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.sqlite.utils.DataBaseConfigurationTest;
import io.dojogeek.adminibot.sqlite.utils.ExpenseTypeDataUtilTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpenseTypeSQLiteOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;
    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private ExpenseTypeDataUtilTest mExpenseTypeDataUtilTest;

    @Before
    public void setUp() {

        mContext = getTargetContext();

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(mContext);
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        mAdminiBotSQLiteOpenHelper = mDataBaseConfigurationTest.getAdminiBotSQLiteOpenHelper();

        loadExpenseTypeUtil();
    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_correctCreatedTable_isTrue() {
        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = '" +
                        ExpenseTypeContract.ExpenseType.TABLE_NAME + "'", null);

        if (cursor != null) {

            assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

        }

    }

    @Test
    public void sqliteHelper_numberOfInsertedExpensesTypes_isEquals() {

        final int insertedValues = 5;

        Cursor cursor = mExpenseTypeDataUtilTest.queryAllRecords();

        assertEquals(cursor.getCount(), insertedValues);

    }

    @Test
    public void sqliteHelper_insertedExpensesTypes_isEquals() {

        Cursor cursor = mExpenseTypeDataUtilTest.queryAllRecords();

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }
    }

    private void compareResultQueryFields(Cursor currentPosition) {


        long id = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType._ID));
        int name = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType.COLUMN_NAME));
        int description = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType.COLUMN_DESCRIPTION));

        String expectedName = ExpenseTypeDataUtilTest.getExpenseTypeFromId(mContext, id);
        String expectedDescription = ExpenseTypeDataUtilTest.getExpenseTypeDescriptionFromId(mContext, id);

        assertEquals(expectedName, getStringFromResourceId(name));
        assertEquals(expectedDescription, getStringFromResourceId(description));

    }

    private String getStringFromResourceId(int resourceId) {
        return mContext.getResources().getString(resourceId);
    }


    private void loadExpenseTypeUtil() {
        mExpenseTypeDataUtilTest = new ExpenseTypeDataUtilTest(mSQLiteDatabase);
    }

}
