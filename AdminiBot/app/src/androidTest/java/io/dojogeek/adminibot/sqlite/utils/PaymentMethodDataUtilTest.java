package io.dojogeek.adminibot.sqlite.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.sqlite.UserContract;

public class PaymentMethodDataUtilTest {

    private static final int ADITIONAL_INDEX = 1;
    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;
    private static PaymentMethodDataUtilTest mPaymentMethodDataUtilTest;

    public PaymentMethodDataUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public Cursor queryAllRecord() {

        Cursor cursor = mDatabaseOperationsUtilTest.queryAllRecords(mSQLiteDatabase, PaymentMethodsContract.PaymentMethod.TABLE_NAME);

        return cursor;
    }

    public static String getValuePaymentMethodFromId(Context context, long id) {

        int [] paymentMethods = PaymentMethodsContract.PAYMENT_METHODS;

        return  context.getString(paymentMethods[getId(id)]);
    }

    public static String getValuePaymentMethodDescriptionFromId(Context context, long id) {

        int [] paymentMethodsDescriptions = PaymentMethodsContract.PAYMENT_METHODS_DESCRIPTIONS;

        return context.getString(paymentMethodsDescriptions[getId(id)]);
    }

    private static int getId(long value) {
        return (int) value - ADITIONAL_INDEX;
    }

}
