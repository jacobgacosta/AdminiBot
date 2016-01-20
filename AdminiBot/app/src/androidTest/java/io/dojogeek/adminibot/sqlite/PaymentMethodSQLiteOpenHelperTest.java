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

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PaymentMethodSQLiteOpenHelperTest {

    private static final int ADITIONAL_INDEX = 1;
    private static final int NONE_TABLE_CREATED = 0;
    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;

    @Before
    public void setUp() {

        mDataBaseConfigurationTest = new DataBaseConfigurationTest();
        mDataBaseConfigurationTest.prepareDataBase(getContext());

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        mAdminiBotSQLiteOpenHelper = mDataBaseConfigurationTest.getAdminiBotSQLiteOpenHelper();
    }

    @After
    public void tearDown() throws Exception {
        mAdminiBotSQLiteOpenHelper.close();
    }

    @Test
    public void sqliteHelper_correctTableCreation_isTrue() {
        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = '" +
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

        SQLiteDatabase sqLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(PaymentMethodsContract.PaymentMethod.TABLE_NAME, null, null, null, null, null, null);

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

        String expectedName = getValuePaymentMethodFromId(id);
        String expectedDescription = getValuePaymentMethodDescriptionFromId(id);

        assertEquals(expectedName, mContext.getString(name));
        assertEquals(expectedDescription, mContext.getString(description));


    }

    private String getValuePaymentMethodFromId(long id) {

        int [] paymentMethods = PaymentMethodsContract.PAYMENT_METHODS;

        return  mContext.getString(paymentMethods[getId(id)]);
    }

    private String getValuePaymentMethodDescriptionFromId(long id) {

        int [] paymentMethodsDescriptions = PaymentMethodsContract.PAYMENT_METHODS_DESCRIPTIONS;

        return mContext.getString(paymentMethodsDescriptions[getId(id)]);
    }

    private int getId(long value) {
        return (int) value - ADITIONAL_INDEX;
    }
}
