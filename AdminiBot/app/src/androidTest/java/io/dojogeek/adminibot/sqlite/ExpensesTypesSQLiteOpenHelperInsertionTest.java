package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExpensesTypesSQLiteOpenHelperInsertionTest {

    private Context mContext;
    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;

    @Before
    public void setup() {

        mContext = getTargetContext();

        loadSQLiteHelper();

        mSQLiteDatabase = openDataBaseConnection();
    }

    @After
    public void tearDown() {
        closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_numberOfInsertedExpensesTypes_isEquals() {

        final int insertedInitialValues = 5;

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + ExpensesTypesContract.ExpenseType.TABLE_NAME,
                null);

        assertEquals(insertedInitialValues, cursor.getCount());

    }

    @Test
    public void sqliteHelper_recordMatching_isEquals() {

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + ExpensesTypesContract.ExpenseType.TABLE_NAME,
                null);

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }
    }

    private void compareResultQueryFields(Cursor currentPosition) {


        long id = currentPosition.getInt(currentPosition.getColumnIndex(ExpensesTypesContract.ExpenseType._ID));
        int name = currentPosition.getInt(currentPosition.getColumnIndex(ExpensesTypesContract.ExpenseType.COLUMN_NAME));
        int description = currentPosition.getInt(currentPosition.getColumnIndex(ExpensesTypesContract.ExpenseType.COLUMN_DESCRIPTION));

        String expectedName = mContext.getString(ExpensesTypesContract.EXPENSES_TYPES[((int) id)]);
        String expectedDescription = mContext.getString(ExpensesTypesContract.EXPENSES_TYPES_DESCRIPTIONS[((int) id)]);

        assertEquals(expectedName, mContext.getString(name));
        assertEquals(expectedDescription, mContext.getString(description));

    }


    private void loadSQLiteHelper() {
        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);
    }

    public void closeDataBaseConnection() {
        mAdminiBotSQLiteOpenHelper.close();
    }

    public SQLiteDatabase openDataBaseConnection() {
        return mAdminiBotSQLiteOpenHelper.getReadableDatabase();
    }
}
