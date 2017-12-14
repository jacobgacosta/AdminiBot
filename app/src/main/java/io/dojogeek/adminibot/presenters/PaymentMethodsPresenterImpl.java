package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.daos.IncomeDao;
import io.dojogeek.adminibot.dtos.IncomeDto;
import io.dojogeek.adminibot.views.PaymentMethods;

public class PaymentMethodsPresenterImpl implements PaymentMethodsPresenter {

    private PaymentMethods mPaymentMethods;
    private IncomeDao mIncomeDao;

    public PaymentMethodsPresenterImpl(PaymentMethods mPaymentMethods, IncomeDao incomeDao) {

        this.mPaymentMethods = mPaymentMethods;
        this.mIncomeDao = incomeDao;

    }

    @Override
    public void registerIncome(IncomeDto income) {

//        mPaymentMethods.stashIncomeId(mIncomeDao.createIncome(income));

    }

}
