package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

import javax.inject.Inject;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.CashModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.sqlite.TypesPaymentMethodsContract;

public class CashDaoImpl extends SQLiteGlobalDao implements CashDao {

    @Inject
    public CashDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createCash(OtherPaymentMethodModel otherPaymentMethodModel) {

        openConnection();

        ContentValues contentValues = createContentValues(otherPaymentMethodModel);

        long result = mDatabase.insertOrThrow(PaymentMethodsContract.PaymentMethods.TABLE_NAME,
                PaymentMethodsContract.PaymentMethods.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    private ContentValues createContentValues(OtherPaymentMethodModel otherPaymentMethodModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_NAME,
                otherPaymentMethodModel.getName());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_REFERENCE,
                otherPaymentMethodModel.getReferenceNumber());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_TYPE_PAYMENT_METHOD,
                otherPaymentMethodModel.getTypePaymentMethod().name());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_AVAILABLE_CREDIT,
                otherPaymentMethodModel.getAvailableCredit().doubleValue());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_USER_ID, 0);

        return contentValues;
    }
}
