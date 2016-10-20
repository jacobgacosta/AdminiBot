package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.models.CashModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public interface CashPresenter {

    void createCash(OtherPaymentMethodModel otherPaymentMethodModel);

    void unnusedView();

}
