package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.Check;

public class CheckPresenterImpl implements CheckPresenter {

    private Check mcheck;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    public CheckPresenterImpl(Check check, OtherPaymentMethodDao otherPaymentMethodDao) {
        this.mcheck = check;
        mOtherPaymentMethodDao = otherPaymentMethodDao;
    }

    @Override
    public void createCheck(OtherPaymentMethodModel otherPaymentMethodModel) {

        try {

            mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);
            mcheck.notifySuccessfulInsertion();
            mcheck.returnToMyPaymentsMethods();

        } catch (SQLException exception) {

            mcheck.notifyErrorInsertion();
        }

    }

}
