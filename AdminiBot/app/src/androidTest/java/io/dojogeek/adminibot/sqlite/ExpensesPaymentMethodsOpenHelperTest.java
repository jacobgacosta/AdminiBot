package io.dojogeek.adminibot.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.utiltest.sqlite.DataBaseConfigurationTest;
import io.dojogeek.adminibot.utiltest.sqlite.ExpenseDataUtilTest;
import io.dojogeek.adminibot.utiltest.sqlite.ExpensesPaymentMethodsUtilTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpensesPaymentMethodsOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;
    private static final int INSERT_ERROR = -1;

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private ExpensesPaymentMethodsUtilTest mExpensesPaymentMethodsUtilTest;

    @Before
    public void setup() {

        mContext = getTargetContext();

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(mContext);
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        loadExpensePaymentMethodUtil();

    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_correctCreatedTable_isTrue() {

        Cursor cursor = mSQLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                ExpensesPaymentMethodsContract.ExpensesPaymentMethods.TABLE_NAME + "'", null);

        assertNotNull(cursor);
        assertTrue(cursor.getCount() > NONE_TABLE_CREATED);
    }

    @Test
    public void sqliteHelper_insertionData_isTrue() {

        double amount = 16956;
        long paymentMethodId = 1;
        long expenseTypeId = 1;


        long insertedRecordId = insertExpensePaymentMethod(amount, paymentMethodId, expenseTypeId);

        assertNotEquals(INSERT_ERROR, insertedRecordId);

    }

    @Test
    public void sqliteHelper_readingData_isTrue() {

        double amount = 6721.90;
        long paymentMethodId = 3;
        long expenseTypeId = 2;

        long insertedRecordId = insertExpensePaymentMethod(amount, paymentMethodId, expenseTypeId);

        String where = ExpensesPaymentMethodsContract.ExpensesPaymentMethods._ID + " = " + insertedRecordId;

        mExpensesPaymentMethodsUtilTest.queryRecordWhere(where);

    }

    private long insertExpensePaymentMethod(double amount, long paymentMethodId, long expenseTypeId) {

        ContentValues contentValues = mExpensesPaymentMethodsUtilTest.createContentValues(amount, paymentMethodId, expenseTypeId);

        long insertedRecordId = mExpensesPaymentMethodsUtilTest.createRecord(contentValues);

        return insertedRecordId;
    }

    private void loadExpensePaymentMethodUtil() {
        mExpensesPaymentMethodsUtilTest = new ExpensesPaymentMethodsUtilTest(mSQLiteDatabase);
    }

}
