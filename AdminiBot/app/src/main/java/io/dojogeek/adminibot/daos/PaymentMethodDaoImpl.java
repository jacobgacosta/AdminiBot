package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;

public class PaymentMethodDaoImpl extends SQLiteGlobalDao implements PaymentMethodDao {

    @Inject
    public PaymentMethodDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long delete(long id) {

        openConnection();

        String [] arg = {String.valueOf(id)};

        return mDatabase.delete(PaymentMethodsContract.PaymentMethod.TABLE_NAME,
                PaymentMethodsContract.PaymentMethod._ID + "= ?", arg);
    }

    @Override
    public List<PaymentMethodModel> getAll() {

        openConnection();

        List<PaymentMethodModel> paymentMethods = new ArrayList<>();

        Cursor cursor = mDatabase.query(PaymentMethodsContract.PaymentMethod.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                PaymentMethodModel paymentMethod = getPaymentMethodFromCursor(cursor);

                paymentMethods.add(paymentMethod);

                cursor.moveToNext();
            }
        }

        return paymentMethods;
    }

    @Override
    public long create(PaymentMethodModel paymentMethod) throws SQLException {

        openConnection();

        ContentValues contentValues = createContentValues(paymentMethod);
        contentValues.put(PaymentMethodsContract.PaymentMethod.COLUMN_CREATED_AT,
                new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

        return mDatabase.insertOrThrow(PaymentMethodsContract.PaymentMethod.TABLE_NAME,
                PaymentMethodsContract.PaymentMethod.COLUMN_NAME_NULLABLE, contentValues);
    }

    @Override
    public List<TypePaymentMethodEnum> getRegisteredTypes() {

        openConnection();

        List<TypePaymentMethodEnum> paymentMethods = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery("SELECT type FROM payment_methods GROUP BY type", null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                String type = cursor.getString(cursor.
                        getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_TYPE));

                paymentMethods.add(TypePaymentMethodEnum.valueOf(type));

                cursor.moveToNext();
            }
        }

        cursor.close();

        return paymentMethods;
    }

    @Override
    public long update(PaymentMethodModel paymentMethod, long id) {

        openConnection();

        ContentValues contentValues = createContentValues(paymentMethod);
        contentValues.put(PaymentMethodsContract.PaymentMethod.COLUMN_UPDATED_AT,
                new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

        String where = PaymentMethodsContract.PaymentMethod._ID + "= " + id;

        return (long) mDatabase.update(PaymentMethodsContract.PaymentMethod.TABLE_NAME,
                contentValues, where, null);
    }

    @Override
    public PaymentMethodModel getById(long id) throws DataException {

        openConnection();

        String [] args = {String.valueOf(id)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + PaymentMethodsContract.PaymentMethod.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (cursor.getCount() == NO_DATA) {
            throw new DataException("no data!");
        }

        PaymentMethodModel paymentMethod = new PaymentMethodModel();

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                paymentMethod = getPaymentMethodFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return paymentMethod;
    }

    private PaymentMethodModel getPaymentMethodFromCursor(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethod._ID));
        String name = cursor.getString(cursor.
                getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_NAME));
        String type = cursor.getString(cursor.
                getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_TYPE));
        String createdAt = cursor.getString(cursor.
                getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_CREATED_AT));
        String updatedAt = cursor.getString(cursor.
                getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_UPDATED_AT));

        DateTime updated =
                updatedAt == null? null :
                        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(updatedAt);

        PaymentMethodModel paymentMethod = new PaymentMethodModel();
        paymentMethod.setId(id);
        paymentMethod.setName(name);
        paymentMethod.setType(TypePaymentMethodEnum.valueOf(type));
        paymentMethod.setCreatedAt(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").
                parseDateTime(createdAt));
        paymentMethod.setUpdatedAt(updated);

        return paymentMethod;
    }

    private ContentValues createContentValues(PaymentMethodModel paymentMethod) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PaymentMethodsContract.PaymentMethod.COLUMN_NAME,
                paymentMethod.getName());
        contentValues.put(PaymentMethodsContract.PaymentMethod.COLUMN_TYPE,
                paymentMethod.getType().name());

        return contentValues;
    }
}
