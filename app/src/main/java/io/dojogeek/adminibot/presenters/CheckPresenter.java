package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.models.PaymentMethodModel;

public interface CheckPresenter {

    void createCheck(PaymentMethodModel paymentMethod);

    void unnusedView();

}
