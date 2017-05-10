package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import io.dojogeek.adminibot.daos.PaymentMethodDao;
import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.views.Check;

public class CheckPresenterImpl implements CheckPresenter {

    private Check mcheck;
    private PaymentMethodDao mPaymentMethodDao;

    public CheckPresenterImpl(Check check, PaymentMethodDao paymentMethodDao) {
        this.mcheck = check;
        mPaymentMethodDao = paymentMethodDao;
    }

    @Override
    public void createCheck(PaymentMethodModel paymentMethod) {

        try {

            mPaymentMethodDao.create(paymentMethod);
            mcheck.notifySuccessfulInsertion();
            mcheck.returnToMyPaymentsMethods();

        } catch (SQLException exception) {

            mcheck.notifyErrorInsertion();
        }

    }

    @Override
    public void unnusedView() {
        ((PaymentMethodDaoImpl) mPaymentMethodDao).closeConnection();
    }

}
