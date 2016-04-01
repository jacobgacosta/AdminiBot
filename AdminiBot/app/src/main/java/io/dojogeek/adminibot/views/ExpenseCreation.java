package io.dojogeek.adminibot.views;

import java.util.List;

import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public interface ExpenseCreation {

    void showTypesPaymentMethods(List<TypePaymentMethodEnum> paymentMethods);

    void showOtherPaymentMethods(List<OtherPaymentMethodModel> otherPaymentMethodModelList);

    void successfulExpenseCreation();

    void errorExpenseCreation();

    void showBankCards(List<BankCardModel> bankCardModels);
}
