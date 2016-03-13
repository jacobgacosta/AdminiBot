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

        ContentValues contentValues = createContentValues(otherPaymentMethodModel);

        long resutl = mDatabase.insert(PaymentMethodsContract.PaymentMethods.TABLE_NAME,
                PaymentMethodsContract.PaymentMethods.COLUMN_NAME_NULLABLE, contentValues);

        return resutl;
    }

    @Override
    public OtherPaymentMethodModel getOtherPaymentMethodById(long otherPaymentMethodId) throws DataException {

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
    public long updateOtherPaymentMethod(OtherPaymentMethodModel otherPaymentMethodModel, long id) {

        ContentValues contentValues = createContentValues(otherPaymentMethodModel);

        String where = PaymentMethodsContract.PaymentMethods._ID + "= " + id;

        long updatedRows = mDatabase.update(PaymentMethodsContract.PaymentMethods.TABLE_NAME, contentValues, where, null);

        return updatedRows;
    }

    @Override
    public long deleteOtherPaymentMethod(long otherPaymentMethodId) {

        String [] arg = {String.valueOf(otherPaymentMethodId)};

        int deletedRows = mDatabase.delete(PaymentMethodsContract.PaymentMethods.TABLE_NAME,
                PaymentMethodsContract.PaymentMethods._ID + "= ?", arg);

        return deletedRows;
    }

    @Override
    public List<OtherPaymentMethodModel> getOtherPaymentMethodByType(TypePaymentMethodEnum typePaymentMethodEnum) {

        String [] args = {typePaymentMethodEnum.name()};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + PaymentMethodsContract.PaymentMethods.TABLE_NAME +
                " WHERE type_payment_method = ? ", args);


        List<OtherPaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                OtherPaymentMethodModel otherPaymentMethodModel = getOtherPaymentMethodModelFromCursor(cursor);
                otherPaymentMethodModelList.add(otherPaymentMethodModel);
                cursor.moveToNext();
            }
        }

        return otherPaymentMethodModelList;
    }

    private ContentValues createContentValues(OtherPaymentMethodModel otherPaymentMethodModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_NAME, otherPaymentMethodModel.getName());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_REFERENCE,
                otherPaymentMethodModel.getReferenceNumber());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_TYPE_PAYMENT_METHOD,
                otherPaymentMethodModel.getTypePaymentMethod().name());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_AVAILABLE_CREDIT,
                otherPaymentMethodModel.getAvailableCredit());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_USER_ID,
                otherPaymentMethodModel.getUserId());

        return contentValues;
    }

    private OtherPaymentMethodModel getOtherPaymentMethodModelFromCursor(Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_NAME));
        String reference = cursor.getString(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_REFERENCE));
        String paymentMethod = cursor.getString(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_TYPE_PAYMENT_METHOD));
        double availableCredit = cursor.getDouble(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_AVAILABLE_CREDIT));
        long userId = cursor.getLong(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethods.COLUMN_USER_ID));

        OtherPaymentMethodModel otherPaymentMethodModel = new OtherPaymentMethodModel();
        otherPaymentMethodModel.setName(name);
        otherPaymentMethodModel.setReferenceNumber(reference);
        otherPaymentMethodModel.setTypePaymentMethod(TypePaymentMethodEnum.valueOf(paymentMethod));
        otherPaymentMethodModel.setAvailableCredit(availableCredit);
        otherPaymentMethodModel.setUserId(userId);

        return otherPaymentMethodModel;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }
}
