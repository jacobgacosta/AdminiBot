package io.dojogeek.adminibot.sqlite.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.sqlite.TypesPaymentMethodsContract;

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

        Cursor cursor = mDatabaseOperationsUtilTest.queryAllRecords(mSQLiteDatabase, PaymentMethodsContract.PaymentMethods.TABLE_NAME);

        return cursor;
    }

    public long insertPaymentMethod(PaymentMethodModel paymentMethodModel) {

        ContentValues contentValues = createContentValuesFromPaymentMethod(paymentMethodModel);

        long insertedRecordId = mDatabaseOperationsUtilTest.createRecord(mSQLiteDatabase,
                PaymentMethodsContract.PaymentMethods.TABLE_NAME, contentValues);

        return insertedRecordId;
    }

    public static String getValuePaymentMethodFromId(Context context, long id) {

        int [] paymentMethods = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS;

        return  context.getString(paymentMethods[getId(id)]);
    }

    public static String getValuePaymentMethodDescriptionFromId(Context context, long id) {

        int [] paymentMethodsDescriptions = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS_DESCRIPTIONS;

        return context.getString(paymentMethodsDescriptions[getId(id)]);
    }

    private static int getId(long value) {
        return (int) value - ADITIONAL_INDEX;
    }

}
