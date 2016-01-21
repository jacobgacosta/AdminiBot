package io.dojogeek.adminibot.sqlite.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.sqlite.UserContract;

public class PaymentMethodDataUtilTest {

    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;
    private static PaymentMethodDataUtilTest mPaymentMethodDataUtilTest;

    public static PaymentMethodDataUtilTest getInstance(SQLiteDatabase sqliteDatabase) {

        if (mPaymentMethodDataUtilTest == null) {
            mPaymentMethodDataUtilTest = new PaymentMethodDataUtilTest(sqliteDatabase);
            return mPaymentMethodDataUtilTest;
        } else {
            return mPaymentMethodDataUtilTest;
        }
        
    }

    private PaymentMethodDataUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public Cursor queryAllRecord() {

        Cursor cursor = mDatabaseOperationsUtilTest.queryAllRecords(mSQLiteDatabase, PaymentMethodsContract.PaymentMethod.TABLE_NAME);

        return cursor;
    }

}
