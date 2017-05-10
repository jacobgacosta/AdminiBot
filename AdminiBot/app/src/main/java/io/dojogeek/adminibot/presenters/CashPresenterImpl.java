package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import io.dojogeek.adminibot.daos.PaymentMethodDao;
import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.views.Cash;

public class CashPresenterImpl implements CashPresenter {

    private Cash mCash;
    private PaymentMethodDao mPaymentMethodDao;


    public CashPresenterImpl(Cash cash, PaymentMethodDao paymentMethodDao) {
        this.mCash = cash;
        this.mPaymentMethodDao = paymentMethodDao;
    }

    @Override
    public void createCash(PaymentMethodModel paymentMethod) {

        try {

            mPaymentMethodDao.create(paymentMethod);
            mCash.notifySuccessfulInsertion();
            mCash.returnToMyPaymentsMethods();

        } catch (SQLException exception) {

            mCash.notifyErrorInsertion();
        }

    }

    @Override
    public void unnusedView() {
        ((PaymentMethodDaoImpl) mPaymentMethodDao).closeConnection();
    }
}
