package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;

public class OtherPaymentMethodDaoImpl extends SQLiteGlobalDao implements OtherPaymentMethodDao {

    @Inject
    public OtherPaymentMethodDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createOtherPaymentMethod(OtherPaymentMethodModel otherPaymentMethodModel) {

        openConnection();

        ContentValues contentValues = createContentValues(otherPaymentMethodModel);

        long resutl = mDatabase.insert(PaymentMethodsContract.PaymentMethods.TABLE_NAME,
                PaymentMethodsContract.PaymentMethods.COLUMN_NAME_NULLABLE, contentValues);

        return resutl;
    }

    @Override
    public OtherPaymentMethodModel getOtherPaymentMethodById(long otherPaymentMethodId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(otherPaymentMethodId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + PaymentMethodsContract.PaymentMethods.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        OtherPaymentMethodModel otherPaymentMethodModel = new OtherPaymentMethodModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                otherPaymentMethodModel = getOtherPaymentMethodModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return otherPaymentMethodModel;

    }

    @Override
    public List<OtherPaymentMethodModel> getOtherPaymentMethods() {

        openConnection();

        List<OtherPaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(PaymentMethodsContract.PaymentMethods.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                OtherPaymentMethodModel otherPaymentMethodModel = getOtherPaymentMethodModelFromCursor(cursor);

                otherPaymentMethodModelList.add(otherPaymentMethodModel);

                cursor.moveToNext();
            }
        }

        return otherPaymentMethodModelList;
    }

    @Override
    public long updateOtherPaymentMethod(OtherPaymentMethodModel otherPaymentMethodModel, String where) {

        openConnection();

        ContentValues contentValues = createContentValues(otherPaymentMethodModel);

        long updatedRows = mDatabase.update(PaymentMethodsContract.PaymentMethods.TABLE_NAME, contentValues, where, null);

        return updatedRows;
    }

    @Override
    public long deleteOtherPaymentMethod(long otherPaymentMethodId) {

        openConnection();

        String [] arg = {String.valueOf(otherPaymentMethodId)};

        int deletedRows = mDatabase.delete(PaymentMethodsContract.PaymentMethods.TABLE_NAME,
                PaymentMethodsContract.PaymentMethods._ID + "= ?", arg);

        return deletedRows;
    }

    private ContentValues createContentValues(OtherPaymentMethodModel otherPaymentMethodModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_NAME, otherPaymentMethodModel.name);
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_REFERENCE,
                otherPaymentMethodModel.referenceNumber);
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_TYPE_PAYMENT_METHOD,
                otherPaymentMethodModel.typePaymentMethod.name());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_AVAILABLE_CREDIT,
                otherPaymentMethodModel.availableCredit);
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_USER_ID,
                otherPaymentMethodModel.userId);

        return contentValues;
    }

    private OtherPaymentMethodModel getOtherPaymentMethodModelFromCursor(Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_NAME));
        String reference = cursor.getString(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_REFERENCE));
        String paymentMethod = cursor.getString(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_TYPE_PAYMENT_METHOD));
        double availableCredit = cursor.getDouble(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_AVAILABLE_CREDIT));
        long userId = cursor.getLong(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_USER_ID));

        OtherPaymentMethodModel otherPaymentMethodModel = new OtherPaymentMethodModel();
        otherPaymentMethodModel.name = name;
        otherPaymentMethodModel.referenceNumber = reference;
        otherPaymentMethodModel.typePaymentMethod = TypePaymentMethodEnum.valueOf(paymentMethod);
        otherPaymentMethodModel.availableCredit = availableCredit;
        otherPaymentMethodModel.userId = userId;

        return otherPaymentMethodModel;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }
}
