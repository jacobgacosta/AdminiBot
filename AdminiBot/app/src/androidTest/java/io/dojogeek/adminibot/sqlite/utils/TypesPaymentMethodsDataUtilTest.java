package io.dojogeek.adminibot.sqlite.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.TypePaymentMethodModel;
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

    public long insertTypePaymentMethod(TypePaymentMethodModel typePaymentMethodModel) {

        ContentValues typePaymentMethodContentValues = createContentValuesFromTypePaymentMethod(typePaymentMethodModel);

        long insertedRecordId = mDatabaseOperationsUtilTest.createRecord(mSQLiteDatabase,
                TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME, typePaymentMethodContentValues);

        return insertedRecordId;
    }

    public Cursor queryRecordWhere(String where) {

        return mDatabaseOperationsUtilTest.queryRecordWhere(mSQLiteDatabase, TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME, where);

    }

    public long updateRecord(ContentValues contentValues, String where) {
        return mDatabaseOperationsUtilTest.updateRecord(mSQLiteDatabase, TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME, contentValues, where);
    }

    public String getValuePaymentMethodFromId(Context context, long id) {

        int [] paymentMethods = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS;

        return  context.getString(paymentMethods[getId(id)]);
    }

    public long deletePaymentMethod(String where) {
        return mDatabaseOperationsUtilTest.deleteRecordWhere(mSQLiteDatabase, TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME, where);
    }

    public String getValuePaymentMethodDescriptionFromId(Context context, long id) {

        int [] paymentMethodsDescriptions = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS_DESCRIPTIONS;

        return context.getString(paymentMethodsDescriptions[getId(id)]);
    }

    public ContentValues createContentValuesFromTypePaymentMethod(TypePaymentMethodModel typePaymentMethodModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_NAME, typePaymentMethodModel.name);
        contentValues.put(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_DESCRIPTION, typePaymentMethodModel.description);

        return contentValues;

    }

    private static int getId(long value) {
        return (int) value - ADITIONAL_INDEX;
    }

}
