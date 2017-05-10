package io.dojogeek.adminibot.presenters;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.IncomeTypePaymentMethodDao;
import io.dojogeek.adminibot.daos.IncomeTypeTypePaymentMethodDaoImpl;
import io.dojogeek.adminibot.daos.PaymentMethodDao;
import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.views.PaymentMethods;

public class PaymentMethodsPresenterImpl implements PaymentMethodsPresenter {

    private BankCardDao mBankCardDao;
    private PaymentMethods mPaymentMethods;
    private PaymentMethodDao mPaymentMethodDao;
    private IncomeTypePaymentMethodDao mIncomeTypePaymentMethodDao;

    public PaymentMethodsPresenterImpl(PaymentMethods paymentMethods,
                                       IncomeTypePaymentMethodDao incomeTypePaymentMethodDao,
                                       BankCardDao bankCardDao,
                                       PaymentMethodDao paymentMethodDao) {
        mBankCardDao = bankCardDao;
        mPaymentMethods = paymentMethods;
        mPaymentMethodDao = paymentMethodDao;
        mIncomeTypePaymentMethodDao = incomeTypePaymentMethodDao;
    }

    @Override
    public void loadAvailablePaymentMethods() {

        List<TypePaymentMethodEnum> availablePaymentMethods = new ArrayList<>();
        availablePaymentMethods.addAll(mPaymentMethodDao.getRegisteredTypes());
        availablePaymentMethods.addAll(mBankCardDao.getRegisteredTypes());

        mPaymentMethods.showRegistered(availablePaymentMethods);
    }

    @Override
    public void closeConnections() {
        ((BankCardDaoImpl) mBankCardDao).closeConnection();
        ((PaymentMethodDaoImpl) mPaymentMethodDao).closeConnection();
        ((IncomeTypeTypePaymentMethodDaoImpl) mIncomeTypePaymentMethodDao).closeConnection();
    }

}
