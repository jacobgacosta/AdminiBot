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
import io.dojogeek.adminibot.sqlite.utils.PaymentMethodDataUtilTest;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PaymentMethodSQLiteOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private PaymentMethodDataUtilTest mPaymentMethodDataUtilTest;

    @Before
    public void setUp() {

        mContext = getTargetContext();

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(mContext);
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        loadPaymentMethodUtil();
    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_successfulTableCreation_isTrue() {

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = '" +
                PaymentMethodsContract.PaymentMethod.TABLE_NAME + "'", null);

        if (cursor != null) {

            assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

        }

    }

    @Test
    public void sqliteHelper_numberOfInsertedPaymentMethods_isEquals() {

        final int insertedValues = 4;

        Cursor cursor = mPaymentMethodDataUtilTest.queryAllRecord();

        assertEquals(cursor.getCount(), insertedValues);

    }

    @Test
    public void sqliteHelper_insertedPaymentMethods_isEquals() {

        Cursor cursor = mPaymentMethodDataUtilTest.queryAllRecord();

        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }

    }

    private void compareResultQueryFields(Cursor currentPosition) {

        long id = currentPosition.getInt(currentPosition.getColumnIndex(PaymentMethodsContract.PaymentMethod._ID));
        int name = currentPosition.getInt(currentPosition.getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_NAME));
        int description = currentPosition.getInt(currentPosition.getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_DESCRIPTION));

        String expectedName = PaymentMethodDataUtilTest.getValuePaymentMethodFromId(mContext, id);
        String expectedDescription = PaymentMethodDataUtilTest.getValuePaymentMethodDescriptionFromId(mContext, id);

        assertEquals(expectedName, mContext.getString(name));
        assertEquals(expectedDescription, mContext.getString(description));

    }

    private void loadPaymentMethodUtil() {
        mPaymentMethodDataUtilTest = new PaymentMethodDataUtilTest(mSQLiteDatabase);
    }
}
