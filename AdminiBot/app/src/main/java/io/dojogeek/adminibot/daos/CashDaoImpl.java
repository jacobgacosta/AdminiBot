package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

import javax.inject.Inject;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.CashModel;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.sqlite.TypesPaymentMethodsContract;

public class CashDaoImpl extends SQLiteGlobalDao implements CashDao {

    @Inject
    public CashDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createCash(CashModel cashModel) {

        openConnection();

        ContentValues contentValues = createContentValues(cashModel);

        long result = mDatabase.insertOrThrow(PaymentMethodsContract.PaymentMethods.TABLE_NAME,
                PaymentMethodsContract.PaymentMethods.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    private ContentValues createContentValues(CashModel cashModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_NAME, cashModel.getAlias());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_REFERENCE, 0);
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_TYPE_PAYMENT_METHOD,
                TypePaymentMethodEnum.CASH.name());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_AVAILABLE_CREDIT,
                cashModel.getAmount().doubleValue());
        contentValues.put(PaymentMethodsContract.PaymentMethods.COLUMN_USER_ID, 0);

        return contentValues;
    }
}
