package io.dojogeek.adminibot.utiltest.sqlite;

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

    public Cursor queryRecordWhere(String where) {

        Cursor cursor = mDatabaseOperationsUtilTest.queryRecordWhere(mSQLiteDatabase,
                PaymentMethodsContract.PaymentMethods.TABLE_NAME, where);

        return cursor;
    }

    public long insertPaymentMethod(PaymentMethodModel paymentMethodModel) {

        ContentValues contentValues = createContentValuesFromPaymentMethod(paymentMethodModel);

        long insertedRecordId = mDatabaseOperationsUtilTest.createRecord(mSQLiteDatabase,
                PaymentMethodsContract.PaymentMethods.TABLE_NAME, contentValues);

        return insertedRecordId;
    }

    public long deleteRecordWhere(String where) {
        long deletedRecordId = mDatabaseOperationsUtilTest.deleteRecordWhere(mSQLiteDatabase,
                PaymentMethodsContract.PaymentMethods.TABLE_NAME, where);

        return deletedRecordId;
    }

    public long updateRecordWhere(ContentValues contentValues, String where) {
        long updatedRecordId = mDatabaseOperationsUtilTest.updateRecord(mSQLiteDatabase, PaymentMethodsContract.PaymentMethods.TABLE_NAME,
                contentValues, where);

        return updatedRecordId;
    }

    public static String getValuePaymentMethodFromId(Context context, long id) {

        int [] paymentMethods = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS;

        return  context.getString(paymentMethods[getId(id)]);
    }

    public static String getValuePaymentMethodDescriptionFromId(Context context, long id) {

        int [] paymentMethodsDescriptions = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS_DESCRIPTIONS;

        return context.getString(paymentMethodsDescriptions[getId(id)]);
    }

    public ContentValues createContentValuesFromPaymentMethod(PaymentMethodModel paymentMethodModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_NAME, paymentMethodModel.name);
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_REFERENCE, paymentMethodModel.reference);
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_TYPE_PAYMENT_METHOD_ID, paymentMethodModel.typePaymentMethod);
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_AVAILABLE_CREDIT, paymentMethodModel.availabreCredit);
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_USER_ID, paymentMethodModel.userId);


        return contentValues;

    }

    private static int getId(long value) {
        return (int) value - ADITIONAL_INDEX;
    }

}
