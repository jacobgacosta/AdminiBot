package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public interface CheckPresenter {

    void createCheck(OtherPaymentMethodModel otherPaymentMethodModel);

    void unnusedView();

}
