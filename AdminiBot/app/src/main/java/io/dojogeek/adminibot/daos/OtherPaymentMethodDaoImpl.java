package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

import javax.inject.Inject;

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
}
