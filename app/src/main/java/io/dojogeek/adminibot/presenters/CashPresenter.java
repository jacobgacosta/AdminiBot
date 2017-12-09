package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.models.PaymentMethodModel;

public interface CashPresenter {

    void createCash(PaymentMethodModel paymentMethod);

    void unnusedView();

}
