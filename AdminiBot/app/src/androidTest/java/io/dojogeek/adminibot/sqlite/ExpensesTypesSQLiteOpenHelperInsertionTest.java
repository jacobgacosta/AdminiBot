package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.enums.ExpenseTypeEnum;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;

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

        assertNotNull(cursor);
        assertThat(cursor.getCount(), greaterThan(0));

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }
    }

    private void compareResultQueryFields(Cursor currentPosition) {


        long id = currentPosition.getInt(currentPosition.getColumnIndex(ExpensesTypesContract.ExpenseType._ID));
        String name = currentPosition.getString(currentPosition.getColumnIndex(ExpensesTypesContract.ExpenseType.COLUMN_NAME));
        String description = currentPosition.getString(currentPosition.getColumnIndex(ExpensesTypesContract.ExpenseType.COLUMN_DESCRIPTION));

        ExpenseTypeEnum expectedName = ExpensesTypesContract.EXPENSES_TYPES[((int) id - 1)];
        String expectedDescription = ExpensesTypesContract.EXPENSES_TYPES[((int) id - 1)].getDescription();

        assertEquals(expectedName, ExpenseTypeEnum.valueOf(name));
        assertEquals(expectedDescription, description);

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
