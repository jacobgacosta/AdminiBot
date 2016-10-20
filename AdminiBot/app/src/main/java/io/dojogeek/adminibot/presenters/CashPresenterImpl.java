package io.dojogeek.adminibot.presenters;

import android.content.Context;
import android.database.SQLException;

import io.dojogeek.adminibot.daos.CashDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.models.CashModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.Cash;

public class CashPresenterImpl implements CashPresenter {

    private Cash mCash;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;


    public CashPresenterImpl(Cash cash, OtherPaymentMethodDao otherPaymentMethodDao) {
        this.mCash = cash;
        this.mOtherPaymentMethodDao = otherPaymentMethodDao;
    }

    @Override
    public void createCash(OtherPaymentMethodModel otherPaymentMethodModel) {

        try {

            mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);
            mCash.notifySuccessfulInsertion();
            mCash.returnToMyPaymentsMethods();

        } catch (SQLException exception) {

            mCash.notifyErrorInsertion();
        }

    }

    @Override
    public void unnusedView() {
        ((OtherPaymentMethodDaoImpl) mOtherPaymentMethodDao).closeConnection();
    }
}
