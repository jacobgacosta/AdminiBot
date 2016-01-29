package io.dojogeek.adminibot.sqlite.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.TypesPaymentMethodsContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

public class TypesPaymentMethodsDataUtilTest {

    private static final int ADITIONAL_INDEX = 1;
    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;

    public TypesPaymentMethodsDataUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public Cursor queryAllRecord() {

        Cursor cursor = mDatabaseOperationsUtilTest.queryAllRecords(mSQLiteDatabase, TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME);

        return cursor;
    }

    public int insertTypePaymentMethod() {


        //int insertedRecordId = mDatabaseOperationsUtilTest.createRecord(mSQLiteDatabase, );
        return 0;
    }

    public String getValuePaymentMethodFromId(Context context, long id) {

        int [] paymentMethods = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS;

        return  context.getString(paymentMethods[getId(id)]);
    }

    public String getValuePaymentMethodDescriptionFromId(Context context, long id) {

        int [] paymentMethodsDescriptions = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS_DESCRIPTIONS;

        return context.getString(paymentMethodsDescriptions[getId(id)]);
    }

    private static int getId(long value) {
        return (int) value - ADITIONAL_INDEX;
    }

}
