package io.dojogeek.adminibot.views;

import java.util.List;

import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public interface ExpenseCreation {

    void showNotification(int stringResource);

    void selectCard(List<BankCardModel> bankCardModelList);

    void registerBankCard(CardTypeEnum cardTypeEnum);

    void selectOtherPaymentMethod(List<OtherPaymentMethodModel> otherPaymentMethodModelList);

    void registerOtherPaymentMethod(TypePaymentMethodEnum typePaymentMethodEnum);

}
