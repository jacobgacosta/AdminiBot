package io.dojogeek.adminibot.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.sqlite.utils.DataBaseConfigurationTest;
import io.dojogeek.adminibot.sqlite.utils.PaymentMethodDataUtilTest;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PaymentMethodsSQLiteOpenHelperTest {

    private static final int INSERT_ERROR = -1;
    private static final int NONE_TABLE_CREATED = 0;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private PaymentMethodDataUtilTest mPaymentMethodDataUtilTest;

    @Before
    public void setup() {

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(getTargetContext());
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        loadPaymentMethodUtil();

    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_creationTablePaymentMethods_isTrue() {

        Cursor cursor = mSQLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                PaymentMethodsContract.PaymentMethods.TABLE_NAME + "'", null);

        assertNotNull(cursor);
        assertTrue(cursor.getCount() > NONE_TABLE_CREATED);
    }

    @Test
    public void sqliteHelper_correctInsertionData_isTrue() {

        long insertedRecordId = insertPaymentMethod();

        assertNotEquals(INSERT_ERROR, insertedRecordId);
    }

    @Test
    public void sqliteHelper_correctDeleteData_isTrue() {

        long insertedRecordId = insertPaymentMethod();

        long deletedRecordId = mPaymentMethodDataUtilTest.deleteRecordWhere(getIdField(insertedRecordId));

        assertEquals(insertedRecordId, deletedRecordId);

    }

    private long insertPaymentMethod() {
        PaymentMethodModel paymentMethod = CreatorModels.createPaymentMethodModel();

        long insertedRecordId = mPaymentMethodDataUtilTest.insertPaymentMethod(paymentMethod);

        return insertedRecordId;
    }

    private void loadPaymentMethodUtil() {
        mPaymentMethodDataUtilTest = new PaymentMethodDataUtilTest(mSQLiteDatabase);
    }

    private String getIdField(long id) {
        return UserContract.User._ID + " = " + id;
    }

}