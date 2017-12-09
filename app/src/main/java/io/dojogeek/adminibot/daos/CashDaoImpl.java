package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.PaymentMethodModel;

public class CashDaoImpl extends SQLiteGlobalDao implements CashDao {

    @Inject
    public CashDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createCash(PaymentMethodModel paymentMethod) {

        openConnection();

        ContentValues contentValues = createContentValues(paymentMethod);

        long result = 0;

        return result;
    }

    private ContentValues createContentValues(PaymentMethodModel paymentMethod) {

        ContentValues contentValues = new ContentValues();
//        contentValues.put(PaymentMethodsContract.PaymentMethodDaoImpl.COLUMN_NAME,
//                paymentMethod.getName());
//        contentValues.put(PaymentMethodsContract.PaymentMethodDaoImpl.COLUMN_REFERENCE,
//                paymentMethod.getReferenceNumber());
//        contentValues.put(PaymentMethodsContract.PaymentMethodDaoImpl.COLUMN_TYPE_PAYMENT_METHOD,
//                paymentMethod.getTypePaymentMethod().name());
//        contentValues.put(PaymentMethodsContract.PaymentMethodDaoImpl.COLUMN_AVAILABLE_CREDIT,
//                paymentMethod.getAvailableCredit().doubleValue());
//        contentValues.put(PaymentMethodsContract.PaymentMethodDaoImpl.COLUMN_USER_ID, 0);

        return contentValues;
    }
}
