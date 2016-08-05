package io.dojogeek.adminibot.presenters;

import android.content.Context;
import android.database.SQLException;

import io.dojogeek.adminibot.daos.CashDao;
import io.dojogeek.adminibot.models.CashModel;
import io.dojogeek.adminibot.views.Cash;

public class CashPresenterImpl implements CashPresenter {

    private Cash mCash;
    private CashDao mCashDao;


    public CashPresenterImpl(Cash cash, CashDao cashDao) {
        this.mCash = cash;
        this.mCashDao = cashDao;
    }

    @Override
    public void createCash(CashModel cashModel) {

        try {

            mCashDao.createCash(cashModel);
            mCash.notifySuccessfulInsertion();
            mCash.returnToMyPaymentsMethods();

        } catch (SQLException exception) {

            mCash.notifyErrorInsertion();
        }

    }
}
