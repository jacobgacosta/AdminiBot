package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.sqlite.UserContract;

public class PaymentMethodsDaoImpl extends SQLiteGlobalDao implements PaymentMethodsDao {

    @Inject
    public PaymentMethodsDaoImpl(Context context) {
        super(context);
    }

    @Override
    public List<PaymentMethodModel> getPaymentMethods() {

        List<PaymentMethodModel> paymentMethodModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(PaymentMethodsContract.PaymentMethod.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                PaymentMethodModel paymentMethodModel = getPaymentMethodModelFromCursor(cursor);

                paymentMethodModelList.add(paymentMethodModel);
                cursor.moveToNext();
            }
        }

        return paymentMethodModelList;
        
    }

    @Override
    public long createPaymentMethod(PaymentMethodModel paymentMethod) {

        ContentValues contentValues = createContentValues(paymentMethod);

        long insertedRecordId = mDatabase.insert(PaymentMethodsContract.PaymentMethod.TABLE_NAME, PaymentMethodsContract.PaymentMethod.COLUMN_NAME_NULLABLE, contentValues);

        return insertedRecordId;
    }

    private PaymentMethodModel getPaymentMethodModelFromCursor(Cursor cursor) {

        PaymentMethodModel paymentMethodModel = new PaymentMethodModel();

        long id = cursor.getInt(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethod._ID));
        int name = cursor.getInt(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_NAME));
        int description = cursor.getInt(cursor.getColumnIndex(PaymentMethodsContract.PaymentMethod.COLUMN_DESCRIPTION));

        paymentMethodModel.id = id;
        paymentMethodModel.name = name;
        paymentMethodModel.description = description;

        return paymentMethodModel;

    }

    private ContentValues createContentValues(PaymentMethodModel paymentMethod) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PaymentMethodsContract.PaymentMethod.COLUMN_NAME, paymentMethod.name);
        contentValues.put(PaymentMethodsContract.PaymentMethod.COLUMN_DESCRIPTION, paymentMethod.description);

        return contentValues;
    }

}
