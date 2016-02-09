package io.dojogeek.adminibot.sqlite;

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

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpensesPaymentMethodsOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;

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

}
