package io.dojogeek.adminibot.presenters;

public interface PaymentMethodsPresenter {

    void closeConnections();

    void loadAvailablePaymentMethods();
}
