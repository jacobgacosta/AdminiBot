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

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import sqlite.AdminiBotSQLiteOpenHelper;
import sqlite.ExpenseTypeContract;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpenseTypeSQLiteOpenHelperTest {

    private static final int ADITIONAL_INDEX = 1;
    private static final int NONE_TABLE_CREATED = 0;
    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;

    @Before
    public void setUp() {

        mContext = getTargetContext();

        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);

        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);
    }

    @After
    public void tearDown() throws Exception {
        mAdminiBotSQLiteOpenHelper.close();
    }

    @Test
    public void sqliteHelper_correctCreatedTable() {
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

        SQLiteDatabase database = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ExpenseTypeContract.ExpenseType.TABLE_NAME, null, null, null, null, null, null);

        assertEquals(cursor.getCount(), insertedValues);

    }

    @Test
    public void sqliteHelper_insertedExpensesTypes_isEquals() {

        SQLiteDatabase database = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ExpenseTypeContract.ExpenseType.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }
    }

    private void compareResultQueryFields(Cursor currentPosition) {

        int [] expectedExpensesTypes = ExpenseTypeContract.EXPENSES_TYPES;
        int [] expextedExpensesTypesDescriptions = ExpenseTypeContract.EXPENSES_TYPES_DESCRIPTIONS;

        long id = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType._ID));
        int name = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType.COLUMN_NAME));
        int description = currentPosition.getInt(currentPosition.getColumnIndex(ExpenseTypeContract.ExpenseType.COLUMN_DESCRIPTION));

        String expectedName = getStringFromResourceId(expectedExpensesTypes[getId(id)]);
        String expectedDescription = getStringFromResourceId(expextedExpensesTypesDescriptions[getId(id)]);

        String actualName = getStringFromResourceId(name);
        String actualDescription = getStringFromResourceId(description);

        assertEquals(expectedName, actualName);
        assertEquals(expectedDescription, actualDescription);

    }

    private String getStringFromResourceId(int resourceId) {
        return mContext.getResources().getString(resourceId);
    }

    private int getId(long value) {
        return (int) value - ADITIONAL_INDEX;
    }

    private ContentValues createContentValues() {

        ExpenseTypeModel expenseTypeModel = createExpenseTypeModel();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseTypeContract.ExpenseType.COLUMN_NAME, expenseTypeModel.name);
        contentValues.put(ExpenseTypeContract.ExpenseType.COLUMN_DESCRIPTION, expenseTypeModel.description);

        return contentValues;
    }

    private ExpenseTypeModel createExpenseTypeModel() {

        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        expenseTypeModel.name = R.string.expenses_types_foods;
        expenseTypeModel.description = 0;

        return expenseTypeModel;

    }

}
