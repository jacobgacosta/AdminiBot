package io.dojogeek.adminibot.presenters;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.views.PaymentMethods;

public class PaymentMethodsPresenterImpl implements PaymentMethodsPresenter {

    private PaymentMethods mPaymentMethods;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;
    private BankCardDao mBankCardDao;
    private List<OtherPaymentMethodModel> mOtherPaymentMethodModels;
    private List<BankCardModel> mBankCardModels;

    public PaymentMethodsPresenterImpl(PaymentMethods paymentMethods, OtherPaymentMethodDao otherPaymentMethodDao,
                                       BankCardDao bankCardDao) {
        mPaymentMethods = paymentMethods;
        mOtherPaymentMethodDao = otherPaymentMethodDao;
        mBankCardDao = bankCardDao;
    }

    @Override
    public void loadPaymentMethods() {

        List<TypePaymentMethodEnum> typePaymentMethodEnumList = getTypesPaymentMethods();

        mPaymentMethods.showTypesPaymentMethods(typePaymentMethodEnumList);

    }

    @Override
    public void unnusedView() {
        ((OtherPaymentMethodDaoImpl) mOtherPaymentMethodDao).closeConnection();
        ((BankCardDaoImpl) mBankCardDao).closeConnection();
    }

    private List<TypePaymentMethodEnum> getTypesPaymentMethods() {

        List<TypePaymentMethodEnum> paymentMethods = new ArrayList<>();

        mOtherPaymentMethodModels = mOtherPaymentMethodDao.getOtherPaymentMethods();

        mBankCardModels = mBankCardDao.getBankCards();

        if (!mBankCardModels.isEmpty()) {
            paymentMethods.add(TypePaymentMethodEnum.CARD);
        }

        for (OtherPaymentMethodModel otherPaymentMethodModel : mOtherPaymentMethodModels) {
            addTypePaymentMethodIfNonExistToList(paymentMethods, otherPaymentMethodModel);
        }

        return paymentMethods;
    }

    private void addTypePaymentMethodIfNonExistToList(List<TypePaymentMethodEnum> paymentMethods,
                                                      OtherPaymentMethodModel otherPaymentMethodModel) {

        TypePaymentMethodEnum typePaymentMethod = otherPaymentMethodModel.getTypePaymentMethod();

        if (!paymentMethods.contains(typePaymentMethod)) {
            paymentMethods.add(typePaymentMethod);
        }

    }

}
